package org.springframework.integration.jgroups;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.integration.support.MessageBuilder;

public class JGroupsOutboundEndpointTest {

	@Test
	public void should_sent_message_over_jgroups_channel() throws Exception {
		JChannel jgroupsChannel = Mockito.mock(JChannel.class);
		JGroupsHeaderMapper headerMapper = Mockito.mock(JGroupsHeaderMapper.class);
		
		
		JGroupsOutboundEndpoint outboundEndpoint = new JGroupsOutboundEndpoint(jgroupsChannel, headerMapper);
	
		outboundEndpoint.handleMessage(MessageBuilder.withPayload("message").build());
		
		ArgumentCaptor<Message> captor = ArgumentCaptor.forClass(Message.class);
		
		verify(jgroupsChannel).send(captor.capture());
		
		assertThat(captor.getValue().getObject()).isEqualTo("message");
		
	}

}
