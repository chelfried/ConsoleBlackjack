package com.helfried.blackjack.types;

import com.helfried.blackjack.config.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.ArrayList;
import java.util.List;

public class PlayerDao {

    public static final String URL = "jdbc:mysql://localhost:3306/blackjack_players?serverTimezone=UTC";
    public static final String USER = "root";
    public static final String PASSWORD = "password";

    public static int createNewPlayer(String name) {
        Session session = null;
        Transaction transaction = null;
        Player player = new Player(name);
        Integer id = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            id = (Integer) session.save(player);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return id;
    }

    public static List<Player> listPlayers() {
        List<Player> savedPlayers = new ArrayList<>();

        try (Session session = getSession()) {
            String queryPlayers = "FROM Player p";
            Query<Player> query = session.createQuery(queryPlayers);
            List<Player> foundPlayers = query.getResultList();
            savedPlayers.addAll(foundPlayers);
        }
        return savedPlayers;
    }


    public static void updatePlayerStats() {

    }

    private static Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

}


