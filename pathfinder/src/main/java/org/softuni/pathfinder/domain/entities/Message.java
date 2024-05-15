package org.softuni.pathfinder.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
public class Message  extends  BaseEntity{
    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "text_content")
    private String textContent;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "recipient_id")
    private User recipient;

}
