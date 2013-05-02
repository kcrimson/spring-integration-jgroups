package org.springframework.integration.jgroups;

import java.io.InputStream;

import org.jgroups.conf.ProtocolStackConfigurator;
import org.jgroups.conf.XmlConfigurator;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

public class XmlConfiguratorFactoryBean extends AbstractFactoryBean<ProtocolStackConfigurator> {

	private Resource resource;

	@Override
	public Class<?> getObjectType() {
		return ProtocolStackConfigurator.class;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@Override
	protected ProtocolStackConfigurator createInstance() throws Exception {
		
		Assert.notNull(resource, "no XML resource with JGroups configuration");

		InputStream stream = resource.getInputStream();

		XmlConfigurator configurator = XmlConfigurator.getInstance(stream);

		return configurator;
	}

}
