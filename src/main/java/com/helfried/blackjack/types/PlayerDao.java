package com.helfried.blackjack.types;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDao {

    public static final String URL = "jdbc:mysql://localhost:3306/blackjack_players?serverTimezone=UTC";
    public static final String USER = "root";
    public static final String PASSWORD = "password";

    public static int createNewPlayer(String name) {
        ResultSet resultSet = null;
        int id = 0;
        String sql = "INSERT INTO player(player_name) VALUES (?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 1) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next())
                    id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return id;
    }

    public static List<Player> listPlayers() {
        List<Player> savedPlayers = new ArrayList<>();
        String sql = "SELECT id, player_name, chips, rounds_played FROM player";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("player_name");
                int chips = resultSet.getInt("chips");
                int roundsPlayed = resultSet.getInt("rounds_played");
                Player player = new Player(id, name, chips, roundsPlayed);
                savedPlayers.add(player);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return savedPlayers;
    }


    public static void updatePlayerStats() {

    }

}


