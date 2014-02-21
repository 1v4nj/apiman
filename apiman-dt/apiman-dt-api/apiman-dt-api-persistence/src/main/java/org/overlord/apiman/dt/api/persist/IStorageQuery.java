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
package org.overlord.apiman.dt.api.persist;

import java.util.List;
import java.util.Set;

import org.overlord.apiman.dt.api.beans.services.ServiceVersionBean;
import org.overlord.apiman.dt.api.beans.summary.ApplicationSummaryBean;
import org.overlord.apiman.dt.api.beans.summary.OrganizationSummaryBean;
import org.overlord.apiman.dt.api.beans.summary.ServiceSummaryBean;


/**
 * Specific querying of the storage layer.
 *
 * @author eric.wittmann@redhat.com
 */
public interface IStorageQuery {
    
    /**
     * Returns summary info for all organizations in the given set.
     * @param orgIds
     * @throws StorageException
     */
    public List<OrganizationSummaryBean> getOrgs(Set<String> orgIds) throws StorageException;

    /**
     * Returns summary info for all applications in all organizations in the given set.
     * @param orgIds
     * @throws StorageException
     */
    public List<ApplicationSummaryBean> getApplicationsInOrgs(Set<String> orgIds) throws StorageException;

    /**
     * Returns summary info for all applications in the given organization.
     * @param orgId
     * @throws StorageException
     */
    public List<ApplicationSummaryBean> getApplicationsInOrg(String orgId) throws StorageException;

    /**
     * Returns summary info for all services in all organizations in the given set.
     * @param orgIds
     * @throws StorageException
     */
    public List<ServiceSummaryBean> getServicesInOrgs(Set<String> orgIds) throws StorageException;

    /**
     * Returns summary info for all services in the given organization.
     * @param orgId
     * @throws StorageException
     */
    public List<ServiceSummaryBean> getServicesInOrg(String orgId) throws StorageException;
    
    /**
     * Returns the service version bean with the given info (orgid, serviceid, version).
     * @param orgId
     * @param serviceId
     * @param version
     * @throws StorageException
     */
    public ServiceVersionBean getServiceVersion(String orgId, String serviceId, String version) throws StorageException;
    
    /**
     * Returns all service versions for a given service.
     * @param orgId
     * @param serviceId
     * @throws StorageException
     */
    public List<ServiceVersionBean> getServiceVersions(String orgId, String serviceId) throws StorageException;
    

}
