package sarik.dev.repository;

import sarik.dev.config.DatabaseConfig;
import sarik.dev.model.Contact;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ContactRepository {

    public static boolean saveContact(Contact contact) {
        String sql = "INSERT INTO contacts (name, surname, phone) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(
                DatabaseConfig.URL,
                DatabaseConfig.USER,
                DatabaseConfig.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getSurname());
            preparedStatement.setString(3, contact.getPhone());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Contact> searchContacts(String searchText) {
        List<Contact> contacts = new LinkedList<>();
        String sql = "SELECT * FROM contacts WHERE LOWER(name) LIKE ? OR LOWER(surname) LIKE ? OR phone LIKE ?";

        try (Connection connection = DriverManager.getConnection(
                DatabaseConfig.URL,
                DatabaseConfig.USER,
                DatabaseConfig.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            String searchPattern = "%" + searchText.toLowerCase() + "%";
            preparedStatement.setString(1, searchPattern);
            preparedStatement.setString(2, searchPattern);
            preparedStatement.setString(3, searchPattern);

            ResultSet resultSet = preparedStatement.executeQuery();

            whileResultSet(contacts, resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return contacts;
    }

    private static void whileResultSet(List<Contact> contacts, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Contact contact = new Contact();
            contact.setId(resultSet.getLong("id"));
            contact.setName(resultSet.getString("name"));
            contact.setSurname(resultSet.getString("surname"));
            contact.setPhone(resultSet.getString("phone"));
            contacts.add(contact);
        }
    }

    public static List<Contact> getAllContacts() {
        List<Contact> contacts = new LinkedList<>();
        String sql = "SELECT * FROM contacts";
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            whileResultSet(contacts, resultSet);
            return contacts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean updateContact(Contact contact) {
        String sql = "UPDATE contacts SET name = ?, surname = ?, phone = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getSurname());
            preparedStatement.setString(3, contact.getPhone());
            preparedStatement.setLong(4, contact.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteContact(String phone) {
        String sql = "DELETE FROM contacts WHERE phone = ?";
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, phone);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Contact getByPhone(String phone) {
        String sql = "SELECT * FROM contacts WHERE phone = ?";
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, phone);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Contact contact = new Contact();
                contact.setId(resultSet.getLong("id"));
                contact.setName(resultSet.getString("name"));
                contact.setSurname(resultSet.getString("surname"));
                contact.setPhone(resultSet.getString("phone"));
                return contact;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
