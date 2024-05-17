package com.project.recyclapp.models;

import com.project.recyclapp.modules.users.models.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@NoArgsConstructor
@Entity
@Table(name = "events_participants", uniqueConstraints = {
        @UniqueConstraint(name = "unique_participant", columnNames = {"user", "events"})
})
public class EventsParticipant {
    @Id
    @ColumnDefault("nextval('events_participants_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "events")
    private Event events;

}