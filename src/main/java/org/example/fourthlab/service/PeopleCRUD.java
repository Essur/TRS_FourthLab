package org.example.fourthlab.service;

import org.example.fourthlab.config.DatabaseConnection;
import org.example.fourthlab.model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PeopleCRUD {
    public boolean createPerson(String firstName, String middleName, String lastName, String dateOfBirth) {
        String query = "INSERT INTO People (first_name, middle_name, last_name, date_of_birth) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.connect()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {

                stmt.setString(1, firstName);
                stmt.setString(2, middleName);
                stmt.setString(3, lastName);
                stmt.setString(4, dateOfBirth);

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return true;
    }

    public List<Person> retrieveAllPeople() {
        List<Person> peopleList = new ArrayList<>();
        String query = "SELECT id, first_name, middle_name, last_name, date_of_birth FROM People";

        try (Connection connection = DatabaseConnection.connect()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query);
                 ResultSet resultSet = stmt.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String firstName = resultSet.getString("first_name");
                    String middleName = resultSet.getString("middle_name");
                    String lastName = resultSet.getString("last_name");
                    String dateOfBirth = resultSet.getString("date_of_birth");

                    Person person = new Person(firstName, middleName, lastName, dateOfBirth);
                    person.setId(id);
                    peopleList.add(person);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return peopleList;
    }

    public Person retrievePersonById(int id) {
        String query = "SELECT * FROM People WHERE id = ?";
        try (Connection connection = DatabaseConnection.connect()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {

                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    Person p = new Person(rs.getString("first_name"),
                            rs.getString("middle_name"),
                            rs.getString("last_name"),
                            rs.getString("date_of_birth"));
                    p.setId(rs.getInt("id"));
                    return p;
                }

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public boolean updatePerson(Person person) {
        String query = "UPDATE People SET first_name = ?, middle_name = ?, last_name = ?, date_of_birth = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.connect()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {

                stmt.setString(1, person.getFirstName());
                stmt.setString(2, person.getMiddleName());
                stmt.setString(3, person.getLastName());
                stmt.setString(4, person.getDateOfBirth());
                stmt.setInt(5, person.getId());

                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;  // Update failed
    }

    public boolean deletePerson(int id) {
        String query = "DELETE FROM People WHERE id = ?";
        try (Connection connection = DatabaseConnection.connect()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {

                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return true;
    }


}


