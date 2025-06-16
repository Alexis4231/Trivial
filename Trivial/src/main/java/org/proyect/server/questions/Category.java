package org.proyect.server.questions;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Category {
    @Id
    private String name;
    private String color;

    public Category(String name, String color){
        this.name = name;
        this.color = color;
    }
}
