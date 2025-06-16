package org.proyect.server.dao;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.proyect.server.questions.Answer;
import java.util.List;

public class AnswerDAO {
    /**
     * Inserta datos de un objeto Answer pasado por parámetro en la tabla Answer de la base de datos.
     * @param answer Objeto Answer.
     */
    public static void create(Answer answer) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.getTransaction().begin();
            session.persist(answer);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Devuelve el objeto Answer cuyo id coincide con el pasado como parámetro.
     * @param id Id del objeto Answer.
     * @return El objeto Answer.
     */
    public static Answer read(int id){
        Answer answer = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            answer = session.find(Answer.class, id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return answer;
    }

    /**
     * Elimina de la base de datos la información perteneciente a un objeto Answer pasado como parámetro.
     * @param answer Objeto Answer.
     */
    public static void delete(Answer answer){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.getTransaction().begin();
            session.remove(answer);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Devuelve la lista de Answers pertenecientes a una misma Question recogida a través de su id pasada como parámetro.
     * @param id Id de la Question.
     * @return La lista de Answers.
     */
    public static List<Answer> getAnswersByQuestionId(int id){
        List<Answer> result;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT ans from Answer ans WHERE ans.question.id = :id";
            Query q = session.createQuery(hql, Answer.class);
            q.setParameter("id",id);
            result = (List<Answer>) q.getResultList();
        }
        return result;
    }
}
