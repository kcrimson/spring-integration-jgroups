package org.springframework.integration.jgroups;

import static org.fest.assertions.Assertions.assertThat;

import org.jgroups.conf.ProtocolStackConfigurator;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class XmlConfiguratorFactoryBeanTest {

	
	@Test
	public void should_load_configuration_from_xml_file() throws Exception{
		XmlConfiguratorFactoryBean factoryBean = new XmlConfiguratorFactoryBean();
		factoryBean.setResource(new ClassPathResource("/udp.xml"));
		
		ProtocolStackConfigurator configurator = factoryBean.getObject();
		
		assertThat(configurator.getProtocolStack()).isNotEmpty();
	}
	
}
