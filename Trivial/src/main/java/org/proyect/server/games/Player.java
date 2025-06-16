package org.proyect.server.games;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.generator.EventType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
@NamedQuery(name = "getPlayerByNameAndPass", query = "select p from Player p where p.name = :name and p.pass = :pass")
public class Player {
    @Id
    private String name;
    private String pass;
    @CurrentTimestamp(event = EventType.INSERT)
    private LocalDate registrationDate;
    private int maxScore;
    @OneToMany(mappedBy = "player",fetch = FetchType.EAGER)
    private List<Game> games = new ArrayList<>();

    public Player(String name, String pass){
        this.name = name;
        this.pass = pass;
        this.maxScore = 0;
    }

    /**
     * Actualiza el valor maxScore con el valor pasado como parámetro si este es mayor a el contenido previamente en el atributo maxScore,
     * devolviendo un booleano indicando si se actualizó finalmente el dato del atributo o no.
     * @param points Puntos a comprobar e insertar.
     * @return El resultado de la operación.
     */
    public boolean updateMaxScore(int points){
        boolean result = false;
        if(points > this.getMaxScore()){
            result = true;
            this.maxScore = points;
        }
        return result;
    }

    /**
     * Añade un objeto Game pasado como parámetro a la lista games.
     * @param game Objeto Game a añadir.
     */
    public void addGame(Game game){
        games.add(game);
    }
}
