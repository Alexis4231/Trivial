package org.proyect.server.games;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.generator.EventType;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Player player;
    private int score;
    @CurrentTimestamp(event = EventType.INSERT)
    private LocalDateTime date;

    public Game(Player player, int score){
        this.player = player;
        this.score = score;
    }
}
