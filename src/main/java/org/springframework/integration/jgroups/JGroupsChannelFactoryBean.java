package org.springframework.integration.jgroups;

import org.jgroups.JChannel;
import org.jgroups.conf.ProtocolStackConfigurator;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.util.Assert;

public class JGroupsChannelFactoryBean extends AbstractFactoryBean<JChannel> {

	private ProtocolStackConfigurator protocolStackConfigurator;
	private String clusterName;

	@Override
	public JChannel createInstance() throws Exception {

		Assert.notNull(protocolStackConfigurator, "JGroups protocol stack configurator is null");
		Assert.hasText(clusterName,"JGroups cluster name is null or empty");
		
		JChannel channel = new JChannel(protocolStackConfigurator);
		channel.connect(clusterName);
		
		return channel;

	}

	@Override
	public Class<?> getObjectType() {
		return JChannel.class;
	}

	public void setProtocolStackConfigurator(ProtocolStackConfigurator protocolStackConfigurator) {
		this.protocolStackConfigurator = protocolStackConfigurator;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;		
	}


}