package org.proyect.server.dao;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.proyect.server.games.Game;
import java.util.List;

public class GameDAO {

    /**
     * Inserta datos de un objeto Game pasado por parámetro en la tabla Game de la base de datos.
     * @param game Objeto Game.
     */
    public static void create(Game game) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.getTransaction().begin();
            session.persist(game);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Devuelve el objeto Game cuyo id coincide con el pasado como parámetro.
     * @param id Id del objeto Game.
     * @return El objeto Game.
     */
    public static Game read(int id){
        Game game = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            game = session.find(Game.class, id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return game;
    }

    /**
     * Actualiza los datos de un objeto Game pasado como parámetro.
     * @param game Objeto Game.
     */
    public static void update(Game game){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.getTransaction().begin();
            session.merge(game);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Elimina de la base de datos la información perteneciente a un objeto Game pasado como parámetro.
     * @param game Objeto Game.
     */
    public static void delete(Game game){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.getTransaction().begin();
            session.remove(game);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Devuelve una lista de Integers con los ids existentes en la tabla Game.
     * @return La lista de ids.
     */
    public static List<Integer> getIds(){
        List<Integer> result;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT g.id from Game g";
            Query q = session.createQuery(hql, Integer.class);
            result = q.getResultList();
        }
        return result;
    }

    /**
     * Devuelve una lista de Integers con los ids de los juegos con mayor puntuación.
     * @return La lista de ids.
     */
    public static List<Integer> getBestIds(){
        List<Integer> result;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select g.id from Game g order by g.score DESC limit 10";
            Query q = session.createQuery(hql, Integer.class);
            result = q.getResultList();
        }
        return result;
    }

}
