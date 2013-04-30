package org.springframework.integration.jgroups;

import java.io.InputStream;

import org.jgroups.conf.ProtocolStackConfigurator;
import org.jgroups.conf.XmlConfigurator;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;

public class XmlConfiguratorFactoryBean implements FactoryBean<ProtocolStackConfigurator> {

	private Resource resource;

	@Override
	public ProtocolStackConfigurator getObject() throws Exception {
		
		InputStream stream = resource.getInputStream();
		
		XmlConfigurator configurator = XmlConfigurator.getInstance(stream);
		
		return configurator;
	}

	@Override
	public Class<?> getObjectType() {
		return ProtocolStackConfigurator.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public void setResource(Resource resource) {
		this.resource = resource;		
	}

}
