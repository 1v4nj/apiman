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

package org.overlord.apiman.dt.api.rest.impl;

import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.overlord.apiman.dt.api.beans.BeanUtils;
import org.overlord.apiman.dt.api.beans.idm.PermissionType;
import org.overlord.apiman.dt.api.beans.idm.RoleMembershipBean;
import org.overlord.apiman.dt.api.beans.orgs.OrganizationBean;
import org.overlord.apiman.dt.api.core.IIdmStorage;
import org.overlord.apiman.dt.api.core.IStorage;
import org.overlord.apiman.dt.api.core.exceptions.AlreadyExistsException;
import org.overlord.apiman.dt.api.core.exceptions.DoesNotExistException;
import org.overlord.apiman.dt.api.core.exceptions.StorageException;
import org.overlord.apiman.dt.api.rest.contract.IOrganizationResource;
import org.overlord.apiman.dt.api.rest.contract.IRoleResource;
import org.overlord.apiman.dt.api.rest.contract.IUserResource;
import org.overlord.apiman.dt.api.rest.contract.exceptions.NotAuthorizedException;
import org.overlord.apiman.dt.api.rest.contract.exceptions.OrganizationAlreadyExistsException;
import org.overlord.apiman.dt.api.rest.contract.exceptions.OrganizationNotFoundException;
import org.overlord.apiman.dt.api.rest.contract.exceptions.SystemErrorException;
import org.overlord.apiman.dt.api.rest.impl.util.ExceptionFactory;
import org.overlord.apiman.dt.api.security.ISecurityContext;

/**
 * Implementation of the Organization API.
 * 
 * @author eric.wittmann@redhat.com
 */
@ApplicationScoped
public class OrganizationResourceImpl implements IOrganizationResource {

    @Inject IStorage storage;
    @Inject IIdmStorage idmStorage;
    
    @Inject IUserResource users;
    @Inject IRoleResource roles;
    
    @Inject ISecurityContext securityContext;
    
    /**
     * Constructor.
     */
    public OrganizationResourceImpl() {
    }

    /**
     * @see org.overlord.apiman.dt.api.rest.contract.IOrganizationResource#create(org.overlord.apiman.dt.api.beans.orgs.OrganizationBean)
     */
    @Override
    public OrganizationBean create(OrganizationBean bean) throws OrganizationAlreadyExistsException {
        bean.setId(BeanUtils.idFromName(bean.getName()));
        bean.setCreatedOn(new Date());
        bean.setCreatedBy(securityContext.getCurrentUser());
        try {
            // Store/persist the new organization
            storage.create(bean);

            // Make the current user an owner of the new organization so that she can
            // manage it (grant/revoke, edit details, etc).
            // TODO warning - hard coded role name here
            String orgOwnerRoleId = "OrganizationOwner"; //$NON-NLS-1$
            String currentUser = securityContext.getCurrentUser();
            String orgId = bean.getId();
            RoleMembershipBean membership = RoleMembershipBean.create(currentUser, orgOwnerRoleId, orgId);
            membership.setCreatedOn(new Date());
            idmStorage.createMembership(membership);
            return bean;
        } catch (AlreadyExistsException e) {
            throw ExceptionFactory.organizationAlreadyExistsException(bean.getName());
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }
    
    /**
     * @see org.overlord.apiman.dt.api.rest.contract.IOrganizationResource#get(java.lang.String)
     */
    @Override
    public OrganizationBean get(String organizationId) throws OrganizationNotFoundException, NotAuthorizedException {
        if (!securityContext.hasPermission(PermissionType.orgView, organizationId))
            throw ExceptionFactory.notAuthorizedException();
        try {
            return storage.get(organizationId, OrganizationBean.class);
        } catch (DoesNotExistException e) {
            throw ExceptionFactory.organizationNotFoundException(organizationId);
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }
    
    /**
     * @see org.overlord.apiman.dt.api.rest.contract.IOrganizationResource#update(java.lang.String, org.overlord.apiman.dt.api.beans.orgs.OrganizationBean)
     */
    @Override
    public void update(String organizationId, OrganizationBean bean)
            throws OrganizationNotFoundException, NotAuthorizedException {
        if (!securityContext.hasPermission(PermissionType.orgEdit, organizationId))
            throw ExceptionFactory.notAuthorizedException();
        try {
            bean.setId(organizationId);
            storage.update(bean);
        } catch (DoesNotExistException e) {
            throw ExceptionFactory.organizationNotFoundException(organizationId);
        } catch (StorageException e) {
            throw new SystemErrorException(e);
        }
    }
    
}
