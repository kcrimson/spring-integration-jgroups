package org.springframework.integration.jgroups;

import java.util.HashMap;
import java.util.Map;

import org.jgroups.Address;
import org.jgroups.Message;
import org.jgroups.Message.Flag;
import org.springframework.integration.MessageHeaders;
import org.springframework.integration.mapping.HeaderMapper;

public class DefaultJGroupsHeaderMapper implements HeaderMapper<Message> {

	public void fromHeaders(MessageHeaders headers, Message target) {

	}

	public Map<String, Object> toHeaders(Message source) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		Address src = source.getSrc();
		if (src != null) {
			map.put("src", src);
		}

		Address dest = source.getDest();
		if (dest != null) {
			map.put("dest", dest);
		}

		map.put("OOB", source.isFlagSet(Flag.OOB));
		map.put("DONT_BUNDLE", source.isFlagSet(Flag.DONT_BUNDLE));
		map.put("NO_FC", source.isFlagSet(Flag.NO_FC));
		map.put("NO_RELAY", source.isFlagSet(Flag.NO_RELAY));
		map.put("NO_RELIABILITY", source.isFlagSet(Flag.NO_RELIABILITY));
		map.put("NO_TOTAL_ORDER", source.isFlagSet(Flag.NO_TOTAL_ORDER));
		map.put("RSVP", source.isFlagSet(Flag.RSVP));
		map.put("SCOPED", source.isFlagSet(Flag.SCOPED));

		return map;
	}

}
