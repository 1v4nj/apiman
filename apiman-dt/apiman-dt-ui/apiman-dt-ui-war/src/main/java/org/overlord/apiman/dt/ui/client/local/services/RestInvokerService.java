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
package org.overlord.apiman.dt.ui.client.local.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.errai.common.client.api.Caller;
import org.overlord.apiman.dt.api.rest.contract.IOrganizationResource;
import org.overlord.apiman.dt.api.rest.contract.ISystemResource;
import org.overlord.apiman.dt.api.rest.contract.IUserResource;
import org.overlord.apiman.dt.ui.client.local.services.rest.CallbackAdapter;
import org.overlord.apiman.dt.ui.client.local.services.rest.IRestInvokerCallback;


/**
 * Used to invoke the APIMan DT REST interface.
 *
 * @author eric.wittmann@redhat.com
 */
@ApplicationScoped
public class RestInvokerService {
    
    @Inject
    private Caller<ISystemResource> system;
    @Inject
    private Caller<IUserResource> users;
    @Inject
    private Caller<IOrganizationResource> orgs;
    
    /**
     * Gets the current system status.
     * @param callback
     */
    public void getSystemStatus(IRestInvokerCallback<String> callback) {
        CallbackAdapter<String> adapter = new CallbackAdapter<String>(callback);
        system.call(adapter, adapter).getStatus();
    }

}
