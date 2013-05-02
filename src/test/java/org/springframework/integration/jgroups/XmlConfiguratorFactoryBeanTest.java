package org.springframework.integration.jgroups;

import static org.fest.assertions.Assertions.assertThat;
import static org.hamcrest.core.Is.is;

import org.jgroups.conf.ProtocolStackConfigurator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.integration.jgroups.config.XmlConfiguratorFactoryBean;

public class XmlConfiguratorFactoryBeanTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void should_load_configuration_from_xml_file() throws Exception {
		XmlConfiguratorFactoryBean factoryBean = new XmlConfiguratorFactoryBean();
		factoryBean.setResource(new ClassPathResource("/udp.xml"));
		factoryBean.afterPropertiesSet();

		ProtocolStackConfigurator configurator = factoryBean.getObject();

		assertThat(configurator.getProtocolStack()).isNotEmpty();
	}

	@Test
	public void should_throw_exception_when_no_xml_resource_in_not_set() throws Exception {
		XmlConfiguratorFactoryBean factoryBean = new XmlConfiguratorFactoryBean();

		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(is("no XML resource with JGroups configration"));

		factoryBean.afterPropertiesSet();

		@SuppressWarnings("unused")
		ProtocolStackConfigurator stackConfigurator = factoryBean.getObject();
	}
}
