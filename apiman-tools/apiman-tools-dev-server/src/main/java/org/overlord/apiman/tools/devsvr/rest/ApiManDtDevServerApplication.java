/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.overlord.apiman.tools.devsvr.rest;

import java.util.HashSet;
import java.util.Set;

import org.overlord.apiman.dt.api.rest.ApiManDtApplication;
import org.overlord.apiman.dt.api.rest.impl.SystemResourceImpl;
import org.overlord.apiman.dt.api.rest.impl.UserResourceImpl;
import org.overlord.apiman.dt.api.rest.impl.mappers.RestExceptionMapper;

/**
 * Version of the application used for testing.
 *
 * @author eric.wittmann@redhat.com
 */
public class ApiManDtDevServerApplication extends ApiManDtApplication {

    private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> classes = new HashSet<Class<?>>();

    /**
     * Constructor.
     */
    public ApiManDtDevServerApplication() {
        singletons.add(new SystemResourceImpl());
        singletons.add(new UserResourceImpl());
        
        classes.add(RestExceptionMapper.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
