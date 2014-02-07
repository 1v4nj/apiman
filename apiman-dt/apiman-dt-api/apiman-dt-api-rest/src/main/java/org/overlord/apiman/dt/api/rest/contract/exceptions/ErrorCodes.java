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

package org.overlord.apiman.dt.api.rest.contract.exceptions;


/**
 * A set of error codes used by the application when returning errors via
 * the DT REST API.
 *
 * @author eric.wittmann@redhat.com
 */
public final class ErrorCodes {
    
    //
    // HTTP status codes
    //
    public static final int HTTP_STATUS_CODE_INVALID_INPUT  = 400;
    public static final int HTTP_STATUS_CODE_NOT_FOUND      = 404;
    public static final int HTTP_STATUS_CODE_ALREADY_EXISTS = 409;
    public static final int HTTP_STATUS_CODE_SYSTEM_ERROR   = 500;

    
    //
    // Search API related (cross-cutting across many APIs)
    //
    public static final int SEARCH_CRITERIA_INVALID         = 9001;

    public static final String SEARCH_CRITERIA_INVALID_INFO = null;

    
    //
    // User API related
    //
    public static final int USER_NOT_FOUND      = 1001;

    public static final String USER_NOT_FOUND_INFO = null;

    
    //
    // Organization API related
    //
    public static final int ORG_ALREADY_EXISTS             = 2001;
    public static final int ORG_NOT_FOUND                  = 2002;
    
    public static final String ORG_ALREADY_EXISTS_INFO     = null;
    public static final String ORG_NOT_FOUND_INFO          = null;

}
