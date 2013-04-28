package org.springframework.integration.jgroups;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.jgroups.JChannel;
import org.jgroups.conf.XmlConfigurator;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.integration.Message;
import org.springframework.integration.core.PollableChannel;
import org.springframework.integration.jgroups.JGroupsInboundEndpoint;
import org.springframework.integration.support.channel.BeanFactoryChannelResolver;

import com.google.common.util.concurrent.SettableFuture;

public class AppTest {

	@Test
	public void test() throws Exception {

		XmlConfigurator configurator = XmlConfigurator.getInstance(XmlConfigurator.class.getResource("/udp.xml"));

		JChannel jchannel = new JChannel(configurator);

		JGroupsInboundEndpoint inboundEndpoint = new JGroupsInboundEndpoint(jchannel);

		inboundEndpoint.setAutoStartup(true);
		inboundEndpoint.start();

	}

	@Test
	public void test1() throws Exception {

		// start coordinator
		XmlConfigurator configurator = XmlConfigurator.getInstance(XmlConfigurator.class.getResource("/udp.xml"));

		JChannel jchannel = new JChannel(configurator);

		jchannel.connect("cluster");

		//
		GenericXmlApplicationContext applicationContext = new GenericXmlApplicationContext("inbound-jgroups-channel-adapter.xml");

		BeanFactoryChannelResolver channelResolver = new BeanFactoryChannelResolver(applicationContext);

		final PollableChannel messageChannel = (PollableChannel) channelResolver.resolveChannelName("inbound");

		final SettableFuture<String> future = SettableFuture.create();

		new Thread(new Runnable() {

			public void run() {
				Message<String> message = (Message<String>) messageChannel.receive();
				future.set(message.getPayload());
			}
		}).start();

		jchannel.send(null, "Hello!!!");
		String result = future.get(5, TimeUnit.SECONDS);
		assertEquals("Hello!!!", result);
	}

}
