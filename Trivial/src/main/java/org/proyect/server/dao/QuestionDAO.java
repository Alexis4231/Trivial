package org.proyect.server.dao;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.proyect.server.questions.Question;

import java.util.List;


public class QuestionDAO {
    /**
     * Inserta datos de un objeto Question pasado por parámetro en la tabla Question de la base de datos.
     * @param question Objeto Question.
     */
    public static void create(Question question) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.getTransaction().begin();
            session.persist(question);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Devuelve el objeto Question cuyo id coincide con el pasado como parámetro.
     * @param id id del objeto Question.
     * @return El objeto Question.
     */
    public static Question read(int id){
        Question question = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            question = session.find(Question.class, id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return question;
    }

    /**
     * Actualiza los datos de un objeto Question pasado como parámetro.
     * @param question Objeto Question.
     */
    public static void update(Question question){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.getTransaction().begin();
            session.merge(question);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Elimina de la base de datos la información perteneciente a un objeto Question pasado como parámetro.
     * @param question Objeto Question.
     */
    public static void delete(Question question){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.getTransaction().begin();
            session.remove(question);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Devuelve una lista con los 5 objetos Question que tienen mayor valor en NumFailure en la base de datos.
     * @return Lista de objetos Question.
     */
    public static List<Question> getHardQuestions(){
        List<Question> result;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select q from Question q order by q.numFailure desc limit 5";
            Query q = session.createQuery(hql, Question.class);
            result = q.getResultList();
        }
        return result;
    }

    /**
     * Devuelve una lista con los 5 objetos Question que tienen mayor valor en NumCorrect en la base de datos.
     * @return Lista de objetos Question.
     */
    public static List<Question> getEasyQuestions(){
        List<Question> result;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select q from Question q order by q.numCorrect desc limit 5";
            Query q = session.createQuery(hql, Question.class);
            result = q.getResultList();
        }
        return result;
    }

    /**
     * Devuelve el número de ids existentes en la tabla Question de la base de datos.
     * @return Número de ids.
     */
    public static int getNumberOfQuestions(){
        int count = 0;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            String hql = "select count(q.id) from Question q";
            Query q = session.createQuery(hql,Long.class);
            Long result = (Long) q.getSingleResult();
            count = result.intValue();
        }
        return count;
    }
}