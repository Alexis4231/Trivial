package org.proyect.server;

import org.proyect.server.dao.AnswerDAO;
import org.proyect.server.dao.GameDAO;
import org.proyect.server.dao.PlayerDAO;
import org.proyect.server.dao.QuestionDAO;
import org.proyect.server.games.Game;
import org.proyect.server.games.Player;
import org.proyect.server.questions.Answer;
import org.proyect.server.questions.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class ClientGame extends Thread{
    private Socket socket;
    private Game game;
    private List<Question> questions = new ArrayList<>();

    public ClientGame(Socket socket){
        this.socket = socket;
    }

    /**
     * Genera los hilos en ClientGame. Encargado de atender al cliente.
     */
    @Override
    public void run() {
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader br = new BufferedReader(new InputStreamReader((socket.getInputStream())));
            pw.println("Nombre: ");
            String name = br.readLine();
            pw.println("Contraseña: ");
            String pass = br.readLine();
            if(PlayerDAO.existPlayer(name,pass)){
                pw.println("Cod:10");
                Player player = PlayerDAO.read(name);
                game = new Game(player,0);
                GameDAO.create(game);
                Random random = new Random();
                for(int i = 0; i<6; i++) {
                    questions.add(QuestionDAO.read(random.nextInt(QuestionDAO.getNumberOfQuestions()) + 1));
                }
                for(int i=0; i<questions.size(); i++){
                    pw.println(questions.get(i).getQuestion());
                    int count = 0;
                    List<Answer> answerList1 = AnswerDAO.getAnswersByQuestionId(questions.get(i).getId());
                    List<Answer> answerList2 = AnswerDAO.getAnswersByQuestionId(questions.get(i).getId());
                    Collections.shuffle(answerList1);
                    for(Answer answer : answerList1){
                        count++;
                        pw.println(count + " - " + answer.getAnswer());
                    }
                    pw.println("Selecciona una opción: ");
                    String resultMessage = "";
                    int choose = 0;
                    try {
                        choose = Integer.parseInt(br.readLine());
                    }catch (InputMismatchException e){
                        resultMessage = "Por favor, introduce un número";
                    }
                    int countFinal = 0;
                    int chooseFinal = 0;
                    for(Answer answer : answerList2){
                        countFinal++;
                        if(answer.getAnswer().equals(answerList1.get(choose-1).getAnswer())){
                            chooseFinal = countFinal;
                        }
                    }
                    System.out.println(chooseFinal);

                    if(questions.get(i).isCorrect(chooseFinal)){
                        game.setScore(game.getScore()+1);
                        GameDAO.update(game);
                        resultMessage = "Bien, has acertado, tienes " + game.getScore() + " puntos";
                        questions.get(i).setNumCorrect(questions.get(i).getNumCorrect()+1);

                    }else{
                        resultMessage = "Lo siento, has fallado, la respuesta correcta era " + questions.get(i).getCorrectOption() + ". Tienes " + game.getScore() + " puntos";
                        questions.get(i).setNumFailure(questions.get(i).getNumFailure()+1);
                    }
                    QuestionDAO.update(questions.get(i));
                    pw.println(resultMessage);
                }
                player.addGame(game);
                boolean updateScore = player.updateMaxScore(game.getScore());
                if(updateScore){
                    PlayerDAO.update(player);
                }
                pw.println("Has terminado. Tu puntuación es de " + game.getScore() + " puntos. Has jugado un total de " + player.getGames().size() + " partidas y tu puntuación más alta ha sido " + player.getMaxScore());
            }else{
                pw.println("Cod:11");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
