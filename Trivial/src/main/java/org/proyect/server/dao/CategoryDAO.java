package org.proyect.server.dao;

import org.hibernate.Session;
import org.proyect.server.questions.Category;

public class CategoryDAO {
    /**
     * Inserta datos de un objeto Category pasado por parámetro en la tabla Category de la base de datos.
     * @param category Objeto Category.
     */
    public static void create(Category category) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.getTransaction().begin();
            session.persist(category);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Devuelve el objeto Category cuyo name(PK) coincide con el pasado como parámetro.
     * @param name name del objeto Category.
     * @return El objeto Category.
     */
    public static Category read(String name){
        Category category = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            category = session.find(Category.class, name);
        }catch (Exception e){
            e.printStackTrace();
        }
        return category;
    }

    /**
     * Elimina de la base de datos la información perteneciente a un objeto Category pasado como parámetro.
     * @param category Objeto Category.
     */
    public static void delete(Category category){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            session.getTransaction().begin();
            session.remove(category);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
