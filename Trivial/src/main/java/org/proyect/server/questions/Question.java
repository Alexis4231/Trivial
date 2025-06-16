package org.proyect.server.questions;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String question;
    @OneToMany(mappedBy = "question",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();
    @ManyToOne
    private Category category;
    private int numCorrect;
    private int numFailure;

    public Question(String question, Category category, int numCorrect, int numFailure){
        this.question = question;
        this.category = category;
        this.numCorrect = numCorrect;
        this.numFailure = numFailure;
    }

    /**
     * Devuelve un String con la respuesta correcta en formato de texto.
     * @return La respuesta correcta.
     */
    public String getCorrectOption(){
        String correctAnswer = "";
        for(Answer answer : answers){
            if(answer.isCorrect()){
                correctAnswer = answer.getAnswer();
            }
        }
        return correctAnswer;
    }

    /**
     * Devuelve un booleano indicando si es correcta una de las 4 posibles opciones de la pregunta,
     * indicando la que se desea verificar en formato numérico.
     * @param num Opción que se desea comprobar.
     * @return El resultado de la operación.
     */
    public boolean isCorrect(int num){
        boolean result = false;
        int count = 0;
        for(Answer answer : answers){
            count++;
            if(answer.isCorrect() && num == count){
                result = true;
            }
        }
        return result;
    }
}
