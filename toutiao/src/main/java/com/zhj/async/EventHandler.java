package com.zhj.async;

import java.util.List;

public interface EventHandler {

	public void doHandle(EventModel model);
	public List<EventType> getSupportEventTypes();
}
