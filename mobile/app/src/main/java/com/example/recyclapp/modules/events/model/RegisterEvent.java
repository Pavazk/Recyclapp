package com.example.recyclapp.modules.events.model;

import com.example.recyclapp.modules.main.data.User;

import java.util.List;

public class RegisterEvent {
    private String name;
    private String description;
    private EventType eventType;
    private String code_owner;
    private CollectionType collectionType;
    private List<User> participants;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public CollectionType getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(CollectionType collectionType) {
        this.collectionType = collectionType;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public String getCode_owner() {
        return code_owner;
    }

    public void setCode_owner(String code_owner) {
        this.code_owner = code_owner;
    }

    public RegisterEvent(String name, String description, EventType eventType, CollectionType collectionType, List<User> participants) {
        this.name = name;
        this.description = description;
        this.eventType = eventType;
        this.collectionType = collectionType;
        this.participants = participants;
    }

    public RegisterEvent() {
    }
}