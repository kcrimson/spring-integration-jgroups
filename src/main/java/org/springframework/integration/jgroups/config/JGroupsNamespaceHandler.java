package org.springframework.integration.jgroups.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class JGroupsNamespaceHandler extends NamespaceHandlerSupport{

	public void init() {
		registerBeanDefinitionParser("inbound-channel-adapter", new JGroupsInboundChannelAdapterParser());
	}

}
