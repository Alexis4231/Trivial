package org.proyect.server.questions;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String answer;
    private boolean correct;
    @ManyToOne
    private Question question;

    public Answer(String answer, boolean correct, Question question){
        this.answer = answer;
        this.correct = correct;
        this.question = question;
    }
}
