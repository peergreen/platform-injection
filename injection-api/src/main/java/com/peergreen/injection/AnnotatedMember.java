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

import java.util.List;

/**
 * Annotation entry
 * @author Florent Benoit
 */
public interface AnnotatedMember {

    /**
     * List of JNDI bindings for a member (Field, Method, etc).
     */
    List<Binding<?>> getBindings();

    /**
     * @return true if this member can be injected
     */
    boolean hasInjection();

    /**
     * Inject the values provided in the ENC values.
     */
    void inject(Object instance) throws InjectException;

    /**
     * Inject
     * @param instance the instance of the object to use for injection
     * @param values the different values to inject
     */
    void inject(Object instance, Object... value) throws InjectException;

}
