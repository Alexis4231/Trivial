package org.proyect.server;

import org.proyect.server.dao.GameDAO;
import org.proyect.server.dao.PlayerDAO;
import org.proyect.server.dao.QuestionDAO;
import org.proyect.server.games.Game;
import org.proyect.server.games.Player;
import org.proyect.server.questions.Question;

import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Server {

    private static Scanner scInt = new Scanner(System.in);
    private static Scanner scString = new Scanner(System.in);
    private static boolean serverOn = false;

    /**
     * Arranca el servidor en el puerto 48120.
     */
    private static void startServer(){
        if(serverOn){
            System.out.println("EL SERVIDOR YA ESTÁ ENCENDIDO");
            System.out.println();
        }else{
            serverOn = true;
            System.out.println("SERVIDOR ENCENDIDO");
            System.out.println();
            Thread thread = new ServerClient();
            thread.start();
        }
    }

    /**
     * Muestra por pantalla las partidas almacenadas en la base de datos junto con el nombre del jugador.
     */
    private static void consultHistorical(){
        Game game = null;
        for(int id : GameDAO.getIds()){
            game = GameDAO.read(id);
            System.out.println("Jugador: " + game.getPlayer().getName() + " | Fecha: " + game.getDate().format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")));
        }
    }

    /**
     * Muestra por pantalla las 10 partidas con mayor puntuación.
     */
    private static void consultTop(){
        Game game = null;
        for(int id : GameDAO.getBestIds()){
            game = GameDAO.read(id);
            System.out.println("Jugador: " + game.getPlayer().getName() + " | Puntuación: " + game.getScore() + " | Fecha: " + game.getDate().format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")));
        }
    }

    /**
     * Elimina toda la información almacenada en la tabla Game de la base de datos y establece el valor de MaxScore en 0 para
     * todos los jugadores.
     */
    private static void restartResult(){
        Game game = null;
        for(int id : GameDAO.getIds()){
            game = GameDAO.read(id);
            game.getPlayer().setMaxScore(0);
            PlayerDAO.update(game.getPlayer());
            GameDAO.delete(game);
        }

        System.out.println("PARTIDAS BORRADAS");
        System.out.println();
    }

    /**
     * Muestra las 5 preguntas más fáciles según el número de aciertos y las 5 preguntas más difíciles según el número de fallos.
     */
    private static void EasyAndHardQuestions(){
        System.out.println("PREGUNTAS MAS SENCILLAS");
        int count = 0;
        for(Question question : QuestionDAO.getEasyQuestions()){
            count++;
            System.out.println(count + "- " + question.getQuestion());
        }
        System.out.println("PREGUNTAS MAS DIFÍCILES");
        count = 0;
        for(Question question : QuestionDAO.getHardQuestions()){
            count++;
            System.out.println(count + "- " + question.getQuestion());
        }
        System.out.println();
    }

    /**
     * Inserta en la base de datos un nuevo jugador con el nombre y contraseña ingresadas por el usuario.
     */
    private static void createPlayer(){
        System.out.println("CREAR JUGADOR");
        System.out.print("Introduce el nombre: ");
        String name = scString.next();
        System.out.print("Introduce la contraseña: ");
        String pass = scString.next();
        if(PlayerDAO.existPlayer(name,pass)) {
            System.out.println("El jugador " + name + " ya ha sido creado");
            System.out.println();
        }else{
            Player player = new Player(name, pass);
            PlayerDAO.create(player);
            System.out.println("Jugador creado correctamente");
            System.out.println();
        }
    }

    /**
     * Finaliza el programa.
     */
    private static void exit() {
        System.out.println("¡Hasta la próxima!");System.exit(0);
    }


    public static void main(String[] args){
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        boolean exit = false;
        while (!exit) {
            try {
                System.out.println("MENU DEL SERVIDOR");
                System.out.println("1. Arrancar el servidor");
                System.out.println("2. Consultar el histórico de jugadas");
                System.out.println("3. Consultar el top 10 de jugadas");
                System.out.println("4. Resetear resultados");
                System.out.println("5. Mostrar las preguntas más sencillas y más difíciles");
                System.out.println("6. Crear jugador");
                System.out.println("7. Salir");
                System.out.print("Introduce una opción: ");
                int choice = scInt.nextInt();
                switch (choice) {
                    case 1:
                        startServer();
                        break;
                    case 2:
                        consultHistorical();
                        break;
                    case 3:
                        consultTop();
                        break;
                    case 4:
                        restartResult();
                        break;
                    case 5:
                        EasyAndHardQuestions();
                        break;
                    case 6:
                        createPlayer();
                        break;
                    case 7:
                        exit();
                        break;
                    default:
                        System.out.println("Introduce una opción valida");
                        System.out.println();
                }
            } catch(InputMismatchException ex){
                System.out.println("Introduce valores numéricos");
                System.out.println();
                scInt.next();
            }
        }
    }
}
