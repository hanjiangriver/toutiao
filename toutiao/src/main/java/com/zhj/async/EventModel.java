package com.zhj.async;

import java.util.HashMap;
import java.util.Map;

public class EventModel {
	private EventType type;
	private int actorId;
	private String entityType;
	private int entityId;
	private int entityOwnerId;
	private Map<String, String> exts = new HashMap<>();

	public EventModel(){
		
	}
	public EventModel(EventType type){
		this.type=type;
	}
	public String getExt(String key){
		return exts.get(key);
	}
	public EventModel setExt(String key,String value){
		exts.put(key, value);
		return this;
	}
	public EventType getType() {
		return type;
	}

	public EventModel setType(EventType type) {
		this.type = type;
		return this;
	}

	public int getActorId() {
		return actorId;
	}

	public EventModel setActorId(int actorId) {
		this.actorId = actorId;
		return this;
	}

	public String getEntityType() {
		return entityType;
	}

	public EventModel setEntityType(String entityType) {
		this.entityType = entityType;
		return this;
	}

	public int getEntityId() {
		return entityId;
	}

	public EventModel setEntityId(int entityId) {
		this.entityId = entityId;
		return this;
	}

	public int getEntityOwnerId() {
		return entityOwnerId;
	}

	public EventModel setEntityOwnerId(int entityOwnerId) {
		this.entityOwnerId = entityOwnerId;
		return this;
	}

	public Map<String, String> getExts() {
		return exts;
	}

	public void setExts(Map<String, String> exts) {
		this.exts = exts;
		
	}

}
