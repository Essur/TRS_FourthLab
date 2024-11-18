package org.example.fourthlab.service;

import org.example.fourthlab.config.DatabaseConnection;
import org.example.fourthlab.model.Letter;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class LettersCRUD {

    // Method to write a letter
    public boolean createLetter(int senderId, int receiverId, String subject, String body) {
        String query = "INSERT INTO Letters (sender_id, receiver_id, l_subject, body, sent_date) VALUES (?, ?, ?, ?, NOW())";
        try (Connection connection = DatabaseConnection.connect()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {

                stmt.setInt(1, senderId);
                stmt.setInt(2, receiverId);
                stmt.setString(3, subject);
                stmt.setString(4, body);
                stmt.setDate(5, new Date(System.currentTimeMillis()));

                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }



    public List<Letter> retrieveAllLetters() {
        List<Letter> letters = new ArrayList<>();
        String query = "SELECT * FROM Letters";
        try (Connection connection = DatabaseConnection.connect()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Letter l = new Letter(rs.getInt("sender_id"),
                            rs.getInt("receiver_id"),
                            rs.getString("l_subject"),
                            rs.getString("body"));
                    l.setCreatedAt(rs.getDate("sent_date"));
                    l.setId(rs.getInt("id"));
                    letters.add(l);
                }

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return letters;
    }

    // Method to delete a letter
    public boolean deleteLetter(int id) {
        String query = "DELETE FROM Letters WHERE id = ?";
        try (Connection connection = DatabaseConnection.connect()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {

                stmt.setInt(1, id);
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    // Method to update a letter
    public boolean updateLetter(Letter letter) {
        String query = "UPDATE Letters SET sender_id = ?, receiver_id = ?, l_subject = ?, body = ?, sent_date = NOW() WHERE id = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, letter.getSenderId());
            stmt.setInt(2, letter.getReceiverId());
            stmt.setString(3, letter.getSubject());
            stmt.setString(4, letter.getBody());
            stmt.setInt(5, letter.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
