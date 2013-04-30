package org.springframework.integration.jgroups.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.integration.jgroups.JGroupsInboundEndpoint;
import org.w3c.dom.Element;

public class JGroupsInboundChannelAdapterParser extends AbstractSingleBeanDefinitionParser {

	@Override
	protected Class<?> getBeanClass(Element element) {
		return JGroupsInboundEndpoint.class;
	}

	@Override
	protected void doParse(Element element, BeanDefinitionBuilder builder) {
		builder.addPropertyReference("outputChannel", element.getAttribute("channel"));
	}

}
