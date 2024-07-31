package com.project.recyclapp.modules.events.models;

import com.project.recyclapp.modules.users.models.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class RegisterEvent {
    private String name;
    private String description;
    private EventType eventType;
    private String code_owner;
    private CollectionType collectionType;
    private List<User> participants;
}
