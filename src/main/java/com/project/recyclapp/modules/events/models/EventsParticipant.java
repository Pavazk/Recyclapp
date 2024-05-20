package com.project.recyclapp.modules.events.models;

import com.project.recyclapp.modules.users.models.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@NoArgsConstructor
@Entity
public class EventsParticipant {
    @Id
    @Column(insertable=false, updatable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "email")
    private User user;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Event events;

}