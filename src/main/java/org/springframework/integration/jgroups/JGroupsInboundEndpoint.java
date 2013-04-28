package org.springframework.integration.jgroups;

import java.util.Map;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.conf.XmlConfigurator;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mapping.HeaderMapper;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.util.Assert;

public class JGroupsInboundEndpoint extends MessageProducerSupport {

	private final JChannel jgroupsChannel;
	private final HeaderMapper<Message> headerMapper;

	private String clusterName;

	public JGroupsInboundEndpoint() throws Exception {
		jgroupsChannel = new JChannel(XmlConfigurator.getInstance(XmlConfigurator.class.getResource("/udp.xml")));
		headerMapper = new DefaultJGroupsHeaderMapper();
	}

	public JGroupsInboundEndpoint(JChannel jgroupsChannel) {
		this.jgroupsChannel = jgroupsChannel;
		headerMapper = new DefaultJGroupsHeaderMapper();
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {

		Assert.notNull(clusterName, "JGroups cluster name shouldn't be null");

		this.clusterName = clusterName;
	}

	@Override
	protected void doStart() {

		try {

			jgroupsChannel.connect(clusterName);
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

	@Override
	protected void doStop() {
		jgroupsChannel.disconnect();
	}

}