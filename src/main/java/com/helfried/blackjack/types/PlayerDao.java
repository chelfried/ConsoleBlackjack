package com.helfried.blackjack.types;

import java.sql.*;

public class PlayerDao {

    public static final String URL = "jdbc:mysql://localhost:3306/blackjack?serverTimezone=UTC";
    public static final String USER = "root";
    public static final String PASSWORD = "password";

    public static int createNewPlayer(String name) {
        ResultSet resultSet = null;
        int id = 0;
        String sql = "INSERT INTO player(player_name) VALUES (?)";
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, name);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next())
                    id = resultSet.getInt(1);
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                if(resultSet != null)  resultSet.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return id;
    }

    public static void loadPlayer(int choice) {

    }

    public static void listPlayers() {

    }

    public static void updateChips() {

    }

}


