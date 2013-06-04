/**
 * Copyright 2013 Peergreen S.A.S.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.peergreen.injection.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Unbind;
import org.ow2.util.scan.api.metadata.IClassMetadata;
import org.ow2.util.scan.api.metadata.ICollectionClassMetadata;
import org.ow2.util.scan.api.metadata.IFieldMetadata;
import org.ow2.util.scan.api.metadata.IMetadata;
import org.ow2.util.scan.api.metadata.IMethodMetadata;
import org.ow2.util.scan.api.metadata.structures.IAnnotation;

import com.peergreen.deployment.Artifact;
import com.peergreen.injection.AnnotatedClass;
import com.peergreen.injection.AnnotatedMember;
import com.peergreen.injection.Binding;
import com.peergreen.injection.HandlerInjectionProcessor;
import com.peergreen.injection.InjectionService;
import com.peergreen.injection.internal.injector.FieldInjector;
import com.peergreen.injection.internal.injector.MethodInjector;


/**
 * Injection service that is tracking all {@link HandlerInjectionProcessor} processors available on the server
 * @author Florent Benoit
 */
@Component
@Instantiate
@Provides
public class BasicInjectionService implements InjectionService {

    /**
     * Injection processors list.
     */
    private final Map<String, List<HandlerInjectionProcessor>> injectionProcessors;

    public BasicInjectionService() {
        this.injectionProcessors = new ConcurrentHashMap<>();
    }



    @Bind(aggregate=true, optional=true)
    public void bindProcessor(HandlerInjectionProcessor processor) {
        String annotationName = processor.getAnnotation();
        if (annotationName == null) {
            // ignore this unknown processor
            return;
        }
        List<HandlerInjectionProcessor> processors = injectionProcessors.get(annotationName);
        if (processors == null) {
            processors = new CopyOnWriteArrayList<>();
            injectionProcessors.put(annotationName, processors);
        }
        processors.add(processor);



    }

    @Unbind(aggregate=true, optional=true)
    public void unbindProcessor(HandlerInjectionProcessor processor) {
        String annotationName = processor.getAnnotation();
        if (annotationName == null) {
            // ignore this unknown processor
            return;
        }

        // get the current list
        List<HandlerInjectionProcessor> processors = this.injectionProcessors.get(annotationName);

        //
        if (processors != null) {
            processors.remove(processor);
        }

    }

    /**
     * Perform injection on the given metadata
     * @param metadata
     */
    @Override
    public Map<String, AnnotatedClass> getInjection(Artifact artifact, ICollectionClassMetadata collectionMetadata) {
        Map<String, AnnotatedClass> entries = new HashMap<String, AnnotatedClass>();
        for (IClassMetadata classMetadata : collectionMetadata.getClassMetadataCollection()) {
            AnnotatedClass annotatedClass = getInjection(artifact, classMetadata);
            entries.put(annotatedClass.className(), annotatedClass);
        }
        return entries;
    }

    /**
     * Perform injection on the given metadata
     * @param metadata
     */
    @Override
    public AnnotatedClass getInjection(Artifact artifact, IClassMetadata metadata) {

        // gets the annotation on the class
        List<AnnotatedMember> annotatedMembers = new ArrayList<>();

        // annotations on the class itself are not injectable, so set a null injector
        BasicAnnotatedMember classAnnotatedMember = new BasicAnnotatedMember();
        annotatedMembers.add(classAnnotatedMember);
        addBindings(classAnnotatedMember, metadata, artifact);


        // Now add injection for all field metadata
        for (IFieldMetadata fieldMetadata : metadata.getFieldMetadataCollection()) {
            // annotations on the fields are injectable so inject a Field Injector
            BasicAnnotatedMember fieldAnnotatedMember = new BasicAnnotatedMember();
            addBindings(fieldAnnotatedMember, fieldMetadata, artifact);

            if (!fieldAnnotatedMember.getBindings().isEmpty()) {
                fieldAnnotatedMember.setInjector(new FieldInjector(fieldMetadata));
                annotatedMembers.add(fieldAnnotatedMember);
            }

        }

        // Now add injection for all method metadata
        for (IMethodMetadata methodMetadata : metadata.getMethodMetadataCollection()) {
            // annotations on the fields are injectable so inject a Field Injector
            BasicAnnotatedMember methodAnnotatedMember = new BasicAnnotatedMember();
            addBindings(methodAnnotatedMember, methodMetadata, artifact);

            if (!methodAnnotatedMember.getBindings().isEmpty()) {
                methodAnnotatedMember.setInjector(new MethodInjector(methodMetadata));
                annotatedMembers.add(methodAnnotatedMember);
            }

        }

        return new BasicAnnotatedClass(metadata.getClassName(), annotatedMembers);

    }


    protected void addBindings(AnnotatedMember annotatedMember, IMetadata metadata, Artifact artifact) {
        // Get all annotations
        List<IAnnotation> annotations = metadata.getAnnotations();

        // Now, search all matching processors
        for (IAnnotation annotation : annotations) {
            List<HandlerInjectionProcessor> matchingProcessors = getMatchingProcessors(annotation);

            // no matching processor, stop iteration
            if (matchingProcessors.isEmpty()) {
                continue;
            }

            // Build injectionPoint
            BasicInjectionPoint injectionPoint = new BasicInjectionPoint();
            injectionPoint.setAnnotation(annotation);
            injectionPoint.setArtifact(artifact);
            injectionPoint.setMember(metadata.getMember());
            injectionPoint.setMetadata(metadata);

            // Now, call the matching processors
            for (HandlerInjectionProcessor matchingProcessor : matchingProcessors) {
                Binding<?> binding = matchingProcessor.handle(injectionPoint);

                // The processor is not interested by the given annotation so don't do anything
                if (binding == null) {
                    continue;
                }

                // If available, add the binding
                annotatedMember.getBindings().add(binding);
            }
        }
    }


    protected List<HandlerInjectionProcessor> getMatchingProcessors(IAnnotation annotation) {
        List<HandlerInjectionProcessor> matchingProcessors = new ArrayList<>();
        List<HandlerInjectionProcessor> perAnnotationProcessors = injectionProcessors.get(annotation.getClassName());
        if (perAnnotationProcessors != null) {
            for (HandlerInjectionProcessor handlerInjectionProcessor : perAnnotationProcessors) {
                matchingProcessors.add(handlerInjectionProcessor);
            }
        }
        return matchingProcessors;
    }
}
