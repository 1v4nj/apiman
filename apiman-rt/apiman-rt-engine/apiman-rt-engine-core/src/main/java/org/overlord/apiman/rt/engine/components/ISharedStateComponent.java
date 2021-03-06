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
package org.overlord.apiman.rt.engine.components;

import org.overlord.apiman.rt.engine.IComponent;
import org.overlord.apiman.rt.engine.async.IAsyncHandler;

/**
 * A component that allows policies to share information across invokations
 * and potentially across nodes in a cluster.  The expectation is that policies
 * will use this component to track aggregation information such as metrics,
 * billing, or for throttling.
 * 
 * It is up to the implementation of this component to determine how transactional
 * it might be (there are time vs. accuracy tradeoffs to be made here).  Users
 * of API Management will need to ensure they use an appropriate implementation
 * of this component based on the data integrity/accuracy guarantees they require.
 * 
 * All operations in this component are assumed to be asyncrhonous, so a handler
 * must be provided if the policy implementation needs the operation to finish
 * prior to moving on.
 *
 * @author eric.wittmann@redhat.com
 */
public interface ISharedStateComponent extends IComponent {
    
    /**
     * Gets the value of a single property stored in the shared state 
     * environment.  Null is returned if the property is not set.
     * @param namespace
     * @param propertyName
     * @param defaultValue
     * @param handler
     */
    public <T> void getProperty(String namespace, String propertyName, T defaultValue, IAsyncHandler<T> handler);
    
    /**
     * Sets a single property in the shared state environment, returning
     * the previous value of the property or null if it was not previously set.
     * @param namespace
     * @param propertyName
     * @param value
     * @param handler
     */
    public <T> void setProperty(String namespace, String propertyName, T value, IAsyncHandler<T> handler);
    
    /**
     * Clears a property from the shared state environment, returning the previous 
     * value of the property or null if it was not previously set.
     * @param namespace
     * @param propertyName
     * @param handler
     */
    public <T> void clearProperty(String namespace, String propertyName, IAsyncHandler<T> handler);

    /**
     * Increments a value in the shared state environment.  The new value of the 
     * property is returned.  There is no 'decrement' method - you can use 
     * 'increment' with a negative value instead.
     * @param namespace
     * @param propertyName
     * @param amount
     * @param handler
     */
    public <T extends Number> void increment(String namespace, String propertyName, T amount, IAsyncHandler<T> handler);
    
}
