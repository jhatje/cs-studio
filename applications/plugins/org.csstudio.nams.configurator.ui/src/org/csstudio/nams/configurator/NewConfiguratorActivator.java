package org.csstudio.nams.configurator;

import org.csstudio.nams.common.activatorUtils.AbstractBundleActivator;
import org.csstudio.nams.common.activatorUtils.OSGiBundleActivationMethod;
import org.csstudio.nams.common.activatorUtils.OSGiService;
import org.csstudio.nams.common.activatorUtils.Required;
import org.csstudio.nams.configurator.views.AlarmbearbeiterView;
import org.csstudio.nams.configurator.views.AlarmbearbeitergruppenView;
import org.csstudio.nams.configurator.views.AlarmtopicView;
import org.csstudio.nams.configurator.views.FilterView;
import org.csstudio.nams.configurator.views.FilterbedingungView;
import org.csstudio.nams.service.configurationaccess.localstore.declaration.LocalStoreConfigurationService;
import org.osgi.framework.BundleActivator;

public class NewConfiguratorActivator extends AbstractBundleActivator implements BundleActivator {

	/** The plug-in ID */
	public static final String PLUGIN_ID = "org.csstudio.nams.newconfigurator";
	private LocalStoreConfigurationService localStoreConfigurationService;
	
	@OSGiBundleActivationMethod
	public void startBundle(@OSGiService @Required LocalStoreConfigurationService localStoreConfigurationService) {
		this.localStoreConfigurationService = localStoreConfigurationService;
		ModelFactory modelFactory = new ModelFactory(localStoreConfigurationService);
		AlarmbearbeitergruppenView.staticInject(modelFactory);
		AlarmbearbeiterView.staticInject(modelFactory);
		// TODO implement following mehtods
//		AlarmtopicView.staticInject(modelFactory);
//		FilterbedingungView.staticInject(modelFactory);
//		FilterView.staticInject(modelFactory);
	}
}