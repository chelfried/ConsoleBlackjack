package com.helfried.blackjack.types;

import com.helfried.blackjack.Blackjack;
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
        List<Player> savedPlayers;
        try (Session session = getSession()) {
            String queryPlayers = "FROM Player p";
            Query<Player> query = session.createQuery(queryPlayers);
            savedPlayers = query.getResultList();
        }
        return savedPlayers;
    }


    public static void updatePlayerStats(int id, int chips, int roundsPlayed, int remainingHints) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            Player playerToUpdate = session.find(Player.class, id);
            transaction = session.beginTransaction();
            playerToUpdate.setChips(chips);
            playerToUpdate.setRoundsPlayed(playerToUpdate.getRoundsPlayed() + roundsPlayed);
            playerToUpdate.setRemainingHints(remainingHints);
            session.update(playerToUpdate);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
    }

    private static Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

}


