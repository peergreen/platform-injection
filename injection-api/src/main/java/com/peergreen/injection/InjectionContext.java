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

import org.ow2.util.scan.api.metadata.IMetadata;
import org.ow2.util.scan.api.metadata.structures.IAnnotation;
import org.ow2.util.scan.api.metadata.structures.IMember;

import com.peergreen.deployment.Artifact;

/**
 * Defines the injection point
 * @author Florent Benoit
 */
public interface InjectionContext {

    /**
     * @return the artifact for which this metadata is part of.
     */
    Artifact getArtifact();

    /**
     * @return the member (class/method/field/...)
     */
    IMember getMember();

    /**
     * @return the metadata containing the parsed annotations
     */
    IMetadata getMetadata();

    /**
     * @return the annotation
     */
    IAnnotation getAnnotation();

    /**
     * Allows to create a new binding.
     * @param name the name of the binding
     * @param value the value of the binding
     * @return the binding
     */
    <T> Binding<T> createBinding(String name, T value);

}
