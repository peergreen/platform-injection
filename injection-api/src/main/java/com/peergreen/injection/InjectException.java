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
 * Exception if injection cannot be done
 * @author Florent Benoit
 */
public class InjectException extends Exception {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -2869484335499180969L;

    public InjectException(String message) {
        super(message);
    }

    public InjectException(String message, Throwable e) {
        super(message, e);
    }
}
