package com.project.recyclapp.modules.events.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.recyclapp.modules.users.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Event {

    @Id
    @Column(insertable=false, updatable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Por favor ingrese el nombre")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Por favor ingrese la descripción")
    @Size(max = 255)
    private String description;

    @ManyToOne
    @JoinColumn(referencedColumnName = "email")
    private User owner;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private EventType eventType;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private CollectionType collectionType;

    @JsonIgnore
    @OneToMany(mappedBy = "events")
    private List<EventsParticipant> eventsParticipants;
}