package org.springframework.integration.jgroups;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.jgroups.JChannel;
import org.jgroups.Receiver;
import org.junit.Test;

public class JGroupsInboundEndpointTest {

	@Test
	public void should_set_receiver_on_endpoint_start() {

		JChannel jgroupsChannel = mock(JChannel.class);
		JGroupsInboundEndpoint inboundEndpoint = new JGroupsInboundEndpoint(jgroupsChannel);

		inboundEndpoint.start();

		verify(jgroupsChannel).setReceiver(any(Receiver.class));

	}
}
