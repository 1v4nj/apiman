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
package org.overlord.apiman.dt.api.jpa;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Produces an instance of {@link EntityManagerFactory}.
 *
 * @author eric.wittmann@redhat.com
 */
@ApplicationScoped
public class EntityManagerFactoryAccessor implements IEntityManagerFactoryAccessor {
    
    private EntityManagerFactory emf;

    /**
     * Constructor.
     */
    public EntityManagerFactoryAccessor() {
    }
    
    @PostConstruct
    public void postConstruct() {
        String autoValue = System.getProperty("hibernate.hbm2ddl.auto", "validate"); //$NON-NLS-1$ //$NON-NLS-2$
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("hibernate.hbm2ddl.auto", autoValue); //$NON-NLS-1$
        emf = Persistence.createEntityManagerFactory("apiman-dt-api-jpa", properties); //$NON-NLS-1$
    }
    
    /**
     * @see org.overlord.apiman.dt.api.jpa.IEntityManagerFactoryAccessor#getEntityManagerFactory()
     */
    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
    
}
