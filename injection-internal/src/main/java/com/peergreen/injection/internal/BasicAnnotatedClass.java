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

import java.util.List;

import com.peergreen.injection.AnnotatedMember;
import com.peergreen.injection.AnnotatedClass;

public class BasicAnnotatedClass implements AnnotatedClass {

    private final String name;
    private final List<AnnotatedMember> entries;

    public BasicAnnotatedClass(String name, List<AnnotatedMember> entries) {
        this.name = name;
        this.entries = entries;
    }


    @Override
    public String className() {
        return name;
    }

    @Override
    public List<AnnotatedMember> entries() {
        return entries;
    }

}
