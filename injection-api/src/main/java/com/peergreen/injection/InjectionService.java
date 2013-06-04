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
package com.peergreen.injection;

import java.util.Map;

import org.ow2.util.scan.api.metadata.IClassMetadata;
import org.ow2.util.scan.api.metadata.ICollectionClassMetadata;

import com.peergreen.deployment.Artifact;

/**
 * Injection service that handle the injection for a given class or for a given set of classes
 * @author Florent Benoit
 */
public interface InjectionService {

    /**
     * Perform injection on the given metadata
     * @param metadata
     */
    Map<String, AnnotatedClass> getInjection(Artifact artifact, ICollectionClassMetadata collectionMetadata);

    /**
     * Perform injection on the given metadata
     * @param metadata
     */
    AnnotatedClass getInjection(Artifact artifact, IClassMetadata metadata);
}
