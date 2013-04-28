package org.springframework.integration.jgroups;

import org.jgroups.JChannel;
import org.springframework.integration.Message;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.integration.mapping.HeaderMapper;

public class JGroupsOutboundEndpoint extends AbstractMessageHandler {

	private final JChannel jgroupsChannel;
	private final HeaderMapper<org.jgroups.Message> headerMapper;

	public JGroupsOutboundEndpoint(JChannel jgroupsChannel, HeaderMapper<org.jgroups.Message> headerMapper) {
		super();
		this.jgroupsChannel = jgroupsChannel;
		this.headerMapper = headerMapper;
	}

	@Override
	protected void handleMessageInternal(Message<?> message) throws Exception {
		org.jgroups.Message target = new org.jgroups.Message();
		headerMapper.fromHeaders(message.getHeaders(), target);

		target.setObject(message.getPayload());

		jgroupsChannel.send(target);
	}

}
