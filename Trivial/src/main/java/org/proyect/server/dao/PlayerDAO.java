package org.proyect.server.dao;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.proyect.server.games.Player;

public class PlayerDAO {
    /**
     * Inserta datos de un objeto Player pasado por parámetro en la tabla Player de la base de datos.
     * @param player Objeto Player.
     */
    public static void create(Player player) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.getTransaction().begin();
            session.persist(player);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Devuelve el objeto Player cuyo name(PK) coincide con el pasado como parámetro.
     * @param name name del objeto Player.
     * @return El objeto Player.
     */
    public static Player read(String name){
        Player player = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            player = session.find(Player.class, name);
        }catch (Exception e){
            e.printStackTrace();
        }
        return player;
    }

    /**
     * Actualiza los datos de un objeto Player pasado como parámetro.
     * @param player Objeto Player.
     */
    public static void update(Player player){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.getTransaction().begin();
            session.merge(player);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Elimina de la base de datos la información perteneciente a un objeto Player pasado como parámetro.
     * @param player Objeto Player.
     */
    public static void delete(Player player){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.getTransaction().begin();
            session.remove(player);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Devuelve un booleano indicando si existen los datos name y pass de un objeto Player en una fila de la base de datos, en la tabla Player.
     * @param name Nombre del Player.
     * @param pass Contraseña del Player.
     * @return El resultado de la consulta (False si no existe y True si existe).
     */
    public static boolean existPlayer(String name, String pass) {
        boolean result = true;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query q = session.createNamedQuery("getPlayerByNameAndPass",Player.class);
            q.setParameter("name",name);
            q.setParameter("pass",pass);
            q.getSingleResult();
        }catch (NoResultException e){
            result = false;
        }
        return result;
    }
}
