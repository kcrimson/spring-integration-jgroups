package org.springframework.integration.jgroups;

import static org.fest.assertions.Assertions.assertThat;

import org.jgroups.JChannel;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class JGroupsXMLApplicationContextTest {

	@Test
	public void should_create_jgroups_channel_from_xml_application_context() {

		FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("src/test/resources/jgroups-jchannel.xml");

		JChannel jChannel = context.getBean(JChannel.class);

		assertThat(jChannel.getClusterName()).isEqualTo("cluster");

	}

	@Test
	public void should_create_jgroups_inbound_channel_adapter() {
		FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("src/test/resources/inbound-channel-adapter.xml");

		Object cluster = context.getBean("cluster");
		
		JGroupsInboundEndpoint clusterAdapter = context.getBean("cluster-adapter",JGroupsInboundEndpoint.class);
		
		assertThat(cluster).isSameAs(clusterAdapter.getJChannel());
		
	}
}