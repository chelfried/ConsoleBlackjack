package com.helfried.blackjack.types;

import com.helfried.blackjack.config.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PlayerDao {

    public static int createNewPlayer(String name) {
        Session session = null;
        Transaction transaction = null;
        PlayerData newPlayer = new PlayerData(name);
        Integer id = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            id = (int) session.save(newPlayer);
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

    public static List<PlayerData> listPlayers() {
        List<PlayerData> savedPlayers;
        try (Session session = getSession()) {
            String queryPlayers = "FROM PlayerData p";
            Query<PlayerData> query = session.createQuery(queryPlayers);
            savedPlayers = query.getResultList();
        }
        return savedPlayers;
    }

    public static boolean checkForId(int id) {
        List<PlayerData> foundId;
        try (Session session = getSession()) {
            String queryIds = "FROM PlayerData i WHERE i.id = :id";
            foundId = session.createQuery(queryIds).setParameter("id", id).getResultList();
        }
        return !foundId.isEmpty();
    }

    public static void updatePlayerStats(int id, int chips, int roundsPlayed, int remainingHints) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            PlayerData playerToUpdate = session.find(PlayerData.class, id);
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


