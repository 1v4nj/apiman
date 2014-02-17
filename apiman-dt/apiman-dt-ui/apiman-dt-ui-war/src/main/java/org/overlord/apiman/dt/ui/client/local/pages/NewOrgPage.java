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
package org.overlord.apiman.dt.ui.client.local.pages;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShown;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.overlord.apiman.dt.api.beans.orgs.OrganizationBean;
import org.overlord.apiman.dt.ui.client.local.services.rest.IRestInvokerCallback;
import org.overlord.apiman.dt.ui.client.local.util.MultimapUtil;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;


/**
 * Page that lets the user create a new Organization.
 *
 * @author eric.wittmann@redhat.com
 */
@Templated("/org/overlord/apiman/dt/ui/client/local/site/new-org.html#page")
@Page(path="new-org")
@Dependent
public class NewOrgPage extends AbstractPage {
    
    @Inject
    TransitionTo<OrgAppsPage> toOrgApps;
    
    @Inject @DataField
    TextBox name;
    @Inject @DataField
    TextBox description;
    @Inject @DataField
    Button createButton;
    
    /**
     * Constructor.
     */
    public NewOrgPage() {
    }
    
    /**
     * @see org.overlord.apiman.dt.ui.client.local.pages.AbstractPage#loadPageData()
     */
    @Override
    protected int loadPageData() {
        int rval = super.loadPageData();
        return rval;
    }
    
    /**
     * Called once the page is shown.
     */
    @PageShown
    protected void onPageShown() {
        name.setFocus(true);
        createButton.setEnabled(true);
    }
    
    /**
     * Called when the user clicks the Create Organization button.
     * @param event
     */
    @EventHandler("createButton")
    public void onCreate(ClickEvent event) {
        createButton.setEnabled(false);
        OrganizationBean bean = new OrganizationBean();
        bean.setName(name.getValue());
        bean.setDescription(description.getValue());
        rest.createOrganization(bean, new IRestInvokerCallback<OrganizationBean>() {
            @Override
            public void onSuccess(OrganizationBean response) {
                String orgId = response.getId();
                // Short circuit page loading lifecycle - redirect to the Org page
                toOrgApps.go(MultimapUtil.singleItemMap("org", orgId)); //$NON-NLS-1$
            }
            @Override
            public void onError(Throwable error) {
                // TODO do something interesting here!
                Window.alert("Org creation failed: " + error.getMessage()); //$NON-NLS-1$
            }
        });
    }

}
