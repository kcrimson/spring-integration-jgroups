package org.springframework.integration.jgroups.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractConsumerEndpointParser;
import org.springframework.integration.jgroups.JGroupsOutboundEndpoint;
import org.w3c.dom.Element;

public class JGroupsOutboundChannelAdapterParser extends
		AbstractConsumerEndpointParser {

	@Override
	protected String getInputChannelAttributeName() {
		return "channel";
	}

	@Override
	protected BeanDefinitionBuilder parseHandler(Element element,
			ParserContext parserContext) {
		return BeanDefinitionBuilder.genericBeanDefinition(
				JGroupsOutboundEndpoint.class).addConstructorArgReference(
				element.getAttribute("cluster"));
	}

}
