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

/**
 * Allows to manage injection for a specific type (like Field, Method)
 * @author Florent Benoit
 */
public interface Injector {

    /**
     * Inject the given values on the given instance.
     * @param instance the instance on which to get field/method or constructor or ...
     * @param values the given values
     * @throws InjectException if value cannot be injected
     */
    void inject(Object instance, Object... values) throws InjectException;
}
