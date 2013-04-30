package org.springframework.integration.jgroups;

import java.util.Map;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mapping.HeaderMapper;
import org.springframework.integration.support.MessageBuilder;

public class JGroupsInboundEndpoint extends MessageProducerSupport {

	private final JChannel jgroupsChannel;
	private final HeaderMapper<Message> headerMapper;

	public JGroupsInboundEndpoint(JChannel jgroupsChannel) {
		this.jgroupsChannel = jgroupsChannel;
		headerMapper = new DefaultJGroupsHeaderMapper();
	}

	@Override
	protected void doStart() {

		try {

			jgroupsChannel.setReceiver(new ReceiverAdapter() {

				@Override
				public void receive(Message msg) {
					Object object = msg.getObject();

					Map<String, Object> headers = headerMapper.toHeaders(msg);

					sendMessage(MessageBuilder.withPayload(object).copyHeaders(headers).build());
				}

			});

		} catch (Exception e) {
			throw new RuntimeException("unable to connect to cluster", e);
		}

	}

	public JChannel getJChannel() {
		return jgroupsChannel;
	}

}