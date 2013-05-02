package org.springframework.integration.jgroups;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.jgroups.JChannel;
import org.jgroups.conf.ProtocolConfiguration;
import org.jgroups.conf.ProtocolStackConfigurator;
import org.jgroups.stack.ProtocolStack;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class JGroupsChannelFactoryBeanTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void should_create_jchannel_using_provided_configurator_and_cluster_name() throws Exception {
		JGroupsChannelFactoryBean factoryBean = new JGroupsChannelFactoryBean();

		ProtocolStackConfigurator protocolStackConfigurator = mock(ProtocolStackConfigurator.class);
		List<ProtocolConfiguration> protocols = asList(new ProtocolConfiguration("UDP"));
		when(protocolStackConfigurator.getProtocolStack()).thenReturn(protocols);
		

		factoryBean.setProtocolStackConfigurator(protocolStackConfigurator);
		factoryBean.setClusterName("cluster");
		factoryBean.afterPropertiesSet();
		
		JChannel channel = factoryBean.getObject();
		
		ProtocolStack protocolStack = channel.getProtocolStack();
		assertThat(protocolStack.getProtocols()).onProperty("name").containsOnly("UDP");
		
		assertThat(channel.getClusterName()).isEqualTo("cluster");
	}

	@Test
	public void should_throw_exception_when_configurator_is_null() throws Exception {
		JGroupsChannelFactoryBean factoryBean = new JGroupsChannelFactoryBean();

		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(is("JGroups protocol stack configurator is null"));
		
		factoryBean.afterPropertiesSet();
		
		@SuppressWarnings("unused")
		JChannel channel = factoryBean.getObject();
	}

	@Test
	public void should_throw_exception_when_cluster_name_is_null() throws Exception {
		JGroupsChannelFactoryBean factoryBean = new JGroupsChannelFactoryBean();
		factoryBean.setProtocolStackConfigurator(mock(ProtocolStackConfigurator.class));
		
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(is("JGroups cluster name is null or empty"));

		factoryBean.afterPropertiesSet();
		
		@SuppressWarnings("unused")
		JChannel channel = factoryBean.getObject();
	}
	
}
