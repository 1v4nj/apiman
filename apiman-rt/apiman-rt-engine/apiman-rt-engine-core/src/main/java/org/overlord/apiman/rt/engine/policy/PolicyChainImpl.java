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
package org.overlord.apiman.rt.engine.policy;

import java.util.List;

import org.overlord.apiman.rt.engine.async.AsyncResultImpl;
import org.overlord.apiman.rt.engine.async.IAsyncHandler;
import org.overlord.apiman.rt.engine.async.IAsyncResult;
import org.overlord.apiman.rt.engine.beans.PolicyFailure;
import org.overlord.apiman.rt.engine.beans.ServiceRequest;
import org.overlord.apiman.rt.engine.beans.ServiceResponse;

/**
 * An implementation of the policy chain.  Created by the engine and then
 * passed to the policy impls when they are applied to the request/response.
 *
 * @author eric.wittmann@redhat.com
 */
public class PolicyChainImpl implements IPolicyChain {

    private IAsyncHandler<ServiceRequest> inboundHandler;
    private IAsyncHandler<ServiceResponse> outboundHandler;
    private IAsyncHandler<PolicyFailure> policyFailureHandler;
    private int inboundPolicyIndex;
    private int outboundPolicyIndex;
    private List<PolicyWithConfiguration> policies;
    private IPolicyContext context;

    /**
     * Constructor.
     * @param policies
     * @param context
     */
    public PolicyChainImpl(List<PolicyWithConfiguration> policies, IPolicyContext context) {
        this.policies = policies;
        this.context = context;
        this.inboundPolicyIndex = 0;
        this.outboundPolicyIndex = policies.size() - 1;
    }

    /**
     * @see org.overlord.apiman.rt.engine.policy.IPolicyChain#doApply(org.overlord.apiman.rt.engine.beans.ServiceRequest)
     */
    @Override
    public void doApply(ServiceRequest request) {
        if (inboundPolicyIndex < policies.size()) {
            try {
                PolicyWithConfiguration policyWithConfiguration = policies.get(inboundPolicyIndex++);
                Object config = policyWithConfiguration.getConfiguration();
                IPolicy policy = policyWithConfiguration.getPolicy();
                policy.apply(request, this.context, config, this);
            } catch (Throwable error) {
                inboundHandler.handle(AsyncResultImpl.<ServiceRequest>create(error));
            }
        } else {
            inboundHandler.handle(AsyncResultImpl.create(request));
        }
    }

    /**
     * @see org.overlord.apiman.rt.engine.policy.IPolicyChain#doApply(org.overlord.apiman.rt.engine.beans.ServiceResponse)
     */
    @Override
    public void doApply(ServiceResponse response) {
        if (outboundPolicyIndex >= 0) {
            try {
                PolicyWithConfiguration policyWithConfiguration = policies.get(outboundPolicyIndex--);
                Object config = policyWithConfiguration.getConfiguration();
                IPolicy policy = policyWithConfiguration.getPolicy();
                policy.apply(response, this.context, config, this);
            } catch (Throwable error) {
                outboundHandler.handle(AsyncResultImpl.<ServiceResponse>create(error));
            }
        } else {
            outboundHandler.handle(AsyncResultImpl.create(response));
        }
    }

    /**
     * @see org.overlord.apiman.rt.engine.policy.IPolicyChain#doFailure(org.overlord.apiman.rt.engine.beans.PolicyFailure)
     */
    @Override
    public void doFailure(PolicyFailure failure) {
        policyFailureHandler.handle(AsyncResultImpl.create(failure));
    }
    
    /**
     * @see org.overlord.apiman.rt.engine.policy.IPolicyChain#throwError(java.lang.Throwable)
     */
    @Override
    public void throwError(Throwable error) {
        policyFailureHandler.handle((IAsyncResult<PolicyFailure>) AsyncResultImpl.<PolicyFailure>create(error));
    }

    /**
     * @return the inboundCompleteHandler
     */
    public IAsyncHandler<ServiceRequest> getInboundHandler() {
        return inboundHandler;
    }

    /**
     * @param inboundCompleteHandler the inboundCompleteHandler to set
     */
    public void setInboundHandler(IAsyncHandler<ServiceRequest> inboundCompleteHandler) {
        this.inboundHandler = inboundCompleteHandler;
    }

    /**
     * @return the outboundCompleteHandler
     */
    public IAsyncHandler<ServiceResponse> getOutboundHandler() {
        return outboundHandler;
    }

    /**
     * @param outboundCompleteHandler the outboundCompleteHandler to set
     */
    public void setOutboundHandler(IAsyncHandler<ServiceResponse> outboundCompleteHandler) {
        this.outboundHandler = outboundCompleteHandler;
    }

    /**
     * @return the policyFailureHandler
     */
    public IAsyncHandler<PolicyFailure> getPolicyFailureHandler() {
        return policyFailureHandler;
    }

    /**
     * @param policyFailureHandler the policyFailureHandler to set
     */
    public void setPolicyFailureHandler(IAsyncHandler<PolicyFailure> policyFailureHandler) {
        this.policyFailureHandler = policyFailureHandler;
    }

}
