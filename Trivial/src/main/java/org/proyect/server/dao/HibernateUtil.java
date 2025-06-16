package org.proyect.server.dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.proyect.server.games.Game;
import org.proyect.server.games.Player;
import org.proyect.server.questions.Answer;
import org.proyect.server.questions.Category;
import org.proyect.server.questions.Question;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    /**
     * Devuelve una instancia de SessionFactory para gestionar las sesiones en Hibernate.
     * @return La instancia de SessionFactory.
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try (StandardServiceRegistry registry = new
                    StandardServiceRegistryBuilder().build()) {
                sessionFactory = new MetadataSources(registry)
                        .addAnnotatedClass(Game.class)
                        .addAnnotatedClass(Player.class)
                        .addAnnotatedClass(Answer.class)
                        .addAnnotatedClass(Category.class)
                        .addAnnotatedClass(Question.class)
                        .buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}

