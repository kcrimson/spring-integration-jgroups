package org.springframework.integration.jgroups;

import org.jgroups.JChannel;
import org.springframework.integration.Message;
import org.springframework.integration.handler.AbstractMessageHandler;

public class JGroupsOutboundEndpoint extends AbstractMessageHandler {

	private final JChannel jgroupsChannel;
	private final JGroupsHeaderMapper headerMapper;

	public JGroupsOutboundEndpoint(JChannel jgroupsChannel, JGroupsHeaderMapper headerMapper) {
		super();
		this.jgroupsChannel = jgroupsChannel;
		this.headerMapper = headerMapper;
	}

	public JGroupsOutboundEndpoint(JChannel jgroupsChannel) {
		this(jgroupsChannel, new DefaultJGroupsHeaderMapper());
	}

	@Override
	protected void handleMessageInternal(Message<?> message) throws Exception {
		org.jgroups.Message target = new org.jgroups.Message();
		headerMapper.fromHeaders(message.getHeaders(), target);

		target.setObject(message.getPayload());

		jgroupsChannel.send(target);
	}

}  