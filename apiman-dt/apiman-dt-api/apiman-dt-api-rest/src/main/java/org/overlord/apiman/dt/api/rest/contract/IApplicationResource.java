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

package org.overlord.apiman.dt.api.rest.contract;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.overlord.apiman.dt.api.beans.apps.ApplicationBean;
import org.overlord.apiman.dt.api.beans.apps.ApplicationVersionBean;
import org.overlord.apiman.dt.api.beans.contracts.ContractBean;
import org.overlord.apiman.dt.api.beans.contracts.NewContractBean;
import org.overlord.apiman.dt.api.beans.policies.PolicyBean;
import org.overlord.apiman.dt.api.beans.summary.ApplicationSummaryBean;
import org.overlord.apiman.dt.api.beans.summary.ContractSummaryBean;
import org.overlord.apiman.dt.api.rest.contract.exceptions.ApplicationAlreadyExistsException;
import org.overlord.apiman.dt.api.rest.contract.exceptions.ApplicationNotFoundException;
import org.overlord.apiman.dt.api.rest.contract.exceptions.ApplicationVersionNotFoundException;
import org.overlord.apiman.dt.api.rest.contract.exceptions.ContractAlreadyExistsException;
import org.overlord.apiman.dt.api.rest.contract.exceptions.ContractNotFoundException;
import org.overlord.apiman.dt.api.rest.contract.exceptions.NotAuthorizedException;
import org.overlord.apiman.dt.api.rest.contract.exceptions.OrganizationNotFoundException;
import org.overlord.apiman.dt.api.rest.contract.exceptions.PlanNotFoundException;
import org.overlord.apiman.dt.api.rest.contract.exceptions.PolicyNotFoundException;
import org.overlord.apiman.dt.api.rest.contract.exceptions.ServiceNotFoundException;

/**
 * The Application API.
 *
 * @author eric.wittmann@redhat.com
 */
@Path("organizations")
public interface IApplicationResource {

    @POST
    @Path("{organizationId}/applications")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApplicationBean create(@PathParam("organizationId") String organizationId, ApplicationBean bean)
            throws OrganizationNotFoundException, ApplicationAlreadyExistsException, NotAuthorizedException;

    @GET
    @Path("{organizationId}/applications/{applicationId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ApplicationBean get(@PathParam("organizationId") String organizationId,
            @PathParam("applicationId") String applicationId) throws ApplicationNotFoundException,
            NotAuthorizedException;

    @GET
    @Path("{organizationId}/applications")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ApplicationSummaryBean> list(@PathParam("organizationId") String organizationId)
            throws OrganizationNotFoundException, NotAuthorizedException;

    @PUT
    @Path("{organizationId}/applications/{applicationId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void update(@PathParam("organizationId") String organizationId,
            @PathParam("applicationId") String applicationId, ApplicationBean bean)
            throws ApplicationNotFoundException, NotAuthorizedException;

    @POST
    @Path("{organizationId}/applications/{applicationId}/versions")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApplicationVersionBean createVersion(@PathParam("organizationId") String organizationId,
            @PathParam("applicationId") String applicationId, ApplicationVersionBean bean)
            throws ApplicationNotFoundException, NotAuthorizedException;

    @GET
    @Path("{organizationId}/applications/{applicationId}/versions")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ApplicationVersionBean> listVersions(@PathParam("organizationId") String organizationId,
            @PathParam("applicationId") String applicationId) throws ApplicationNotFoundException, NotAuthorizedException;

    @GET
    @Path("{organizationId}/applications/{applicationId}/versions/{version}")
    @Produces(MediaType.APPLICATION_JSON)
    public ApplicationVersionBean getVersion(@PathParam("organizationId") String organizationId,
            @PathParam("applicationId") String applicationId, @PathParam("version") String version)
            throws ApplicationVersionNotFoundException, NotAuthorizedException;

    @PUT
    @Path("{organizationId}/applications/{applicationId}/versions/{version}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateVersion(@PathParam("organizationId") String organizationId,
            @PathParam("applicationId") String applicationId, @PathParam("version") String version,
            ApplicationVersionBean bean) throws ApplicationVersionNotFoundException, NotAuthorizedException;

    @POST
    @Path("{organizationId}/applications/{applicationId}/versions/{version}/contracts")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ContractBean createContract(@PathParam("organizationId") String organizationId,
            @PathParam("applicationId") String applicationId, @PathParam("version") String version,
            NewContractBean bean) throws OrganizationNotFoundException, ApplicationNotFoundException,
            ServiceNotFoundException, PlanNotFoundException, ContractAlreadyExistsException,
            NotAuthorizedException;

    @GET
    @Path("{organizationId}/applications/{applicationId}/versions/{version}/contracts/{contractId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ContractBean getContract(@PathParam("organizationId") String organizationId,
            @PathParam("applicationId") String applicationId, @PathParam("version") String version,
            @PathParam("contractId") Long contractId) throws ApplicationNotFoundException,
            ContractNotFoundException, NotAuthorizedException;

    @GET
    @Path("{organizationId}/applications/{applicationId}/versions/{version}/contracts")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ContractSummaryBean> listContracts(@PathParam("organizationId") String organizationId,
            @PathParam("applicationId") String applicationId, @PathParam("version") String version)
            throws ApplicationNotFoundException, NotAuthorizedException;

    @DELETE
    @Path("{organizationId}/applications/{applicationId}/versions/{version}/contracts/{contractId}")
    public void deleteContract(@PathParam("organizationId") String organizationId,
            @PathParam("applicationId") String applicationId, @PathParam("version") String version,
            @PathParam("contractId") Long contractId) throws ApplicationNotFoundException,
            ContractNotFoundException, NotAuthorizedException;

    @POST
    @Path("{organizationId}/applications/{applicationId}/versions/{version}/policies")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PolicyBean createPolicy(@PathParam("organizationId") String organizationId,
            @PathParam("applicationId") String applicationId, @PathParam("version") String version,
            PolicyBean bean) throws OrganizationNotFoundException, ApplicationVersionNotFoundException,
            NotAuthorizedException;

    @GET
    @Path("{organizationId}/applications/{applicationId}/versions/{version}/policies/{policyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public PolicyBean getPolicy(@PathParam("organizationId") String organizationId,
            @PathParam("applicationId") String applicationId, @PathParam("version") String version,
            @PathParam("policyId") long policyId) throws OrganizationNotFoundException, ApplicationVersionNotFoundException,
            PolicyNotFoundException, NotAuthorizedException;

    @PUT
    @Path("{organizationId}/applications/{applicationId}/versions/{version}/policies/{policyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public PolicyBean updatePolicy(@PathParam("organizationId") String organizationId,
            @PathParam("applicationId") String applicationId, @PathParam("version") String version,
            @PathParam("policyId") long policyId) throws OrganizationNotFoundException, ApplicationVersionNotFoundException,
            PolicyNotFoundException, NotAuthorizedException;

    @DELETE
    @Path("{organizationId}/applications/{applicationId}/versions/{version}/policies/{policyId}")
    public void deletePolicy(@PathParam("organizationId") String organizationId,
            @PathParam("applicationId") String applicationId, @PathParam("version") String version,
            @PathParam("policyId") long policyId) throws OrganizationNotFoundException, ApplicationVersionNotFoundException,
            PolicyNotFoundException, NotAuthorizedException;

    @GET
    @Path("{organizationId}/applications/{applicationId}/versions/{version}/policies")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PolicyBean> listPolicies(@PathParam("organizationId") String organizationId,
            @PathParam("applicationId") String applicationId, @PathParam("version") String version)
            throws OrganizationNotFoundException, ApplicationVersionNotFoundException,
            NotAuthorizedException;

}
