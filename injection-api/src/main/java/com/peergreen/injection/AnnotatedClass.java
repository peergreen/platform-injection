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
 * Defined an annotated class with annotated members (that can be either on class, field, methods...)
 * @author Florent Benoit
 */
public interface AnnotatedClass {

    /**
     * @return the name of the annotated class
     */
    String className();

    /**
     * @return all entries annotated on a class
     */
    List<AnnotatedMember> entries();
}
