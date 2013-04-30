package org.springframework.integration.jgroups;

import org.jgroups.Message;
import org.springframework.integration.mapping.HeaderMapper;

public interface JGroupsHeaderMapper extends HeaderMapper<Message>{

}
