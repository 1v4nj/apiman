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
package org.overlord.apiman.tools.devsvr;

import java.io.File;
import java.io.FileWriter;
import java.util.Properties;

import org.overlord.commons.dev.server.DevServerEnvironment;

/**
 * Holds information about the apiman development runtime environment.
 * @author eric.wittmann@redhat.com
 */
public class ApiManDevServerEnvironment extends DevServerEnvironment {

    /**
     * Constructor.
     * @param args
     */
    public ApiManDevServerEnvironment(String[] args) {
        super(args);
    }
    
    /**
     * @see org.overlord.commons.dev.server.DevServerEnvironment#createAppConfigs()
     */
    @Override
    public void createAppConfigs() throws Exception {
        super.createAppConfigs();
        
        File dir = new File(getTargetDir(), "overlord-apps");
        dir.mkdirs();

        File configFile1 = new File(dir, "apiman-dt-ui-overlordapp.properties");
        Properties props = new Properties();
        props.setProperty("overlordapp.app-id", "apiman-dt-ui");
        props.setProperty("overlordapp.href", "/apiman/index.html");
        props.setProperty("overlordapp.label", "API Management");
        props.setProperty("overlordapp.primary-brand", "JBoss Overlord");
        props.setProperty("overlordapp.secondary-brand", "API Management");
        props.store(new FileWriter(configFile1), "APIMan UI application");
    }

}
