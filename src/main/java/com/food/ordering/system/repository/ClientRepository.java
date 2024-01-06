package com.food.ordering.system.repository;

import com.food.ordering.system.config.Config;
import com.food.ordering.system.db.Util;
import com.food.ordering.system.entity.Client;
import com.food.ordering.system.enums.Gender;
import com.food.ordering.system.exceptions.ClietnNotFoundException;

import java.sql.DriverManager;
import java.util.Optional;

public class ClientRepository {
    private static ClientRepository clientRepository;


    private ClientRepository() {

    }

    public boolean insert(Client client) {
        try (var connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER_NAME, Config.DB_PASSWORD);
        ) {

            var preparedStatement = connection.prepareStatement(
                    "insert into client(id, name, surname, gender, budget, username, password)" +
                            "values(?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setLong(1, client.getId());
            preparedStatement.setString(2, client.getName());
            preparedStatement.setString(3, client.getSurname());
            preparedStatement.setString(4, client.getGender().name());
            preparedStatement.setBigDecimal(5, client.getBudget());
            preparedStatement.setString(6, client.getUsername());
            preparedStatement.setString(7, client.getPassword());
            return preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public Optional<Client> findByUserName(String userName) {
        Client client = null;
        try (var connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER_NAME, Config.DB_PASSWORD)) {
            var prepareStatement = connection.prepareStatement("select * from client where username = ?");
            prepareStatement.setString(1, userName);
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                var id = resultSet.getLong(1);
                var name = resultSet.getString(2);
                var surname = resultSet.getString(3);
                var gender = Gender.valueOf(resultSet.getString(4));
                var budget = resultSet.getBigDecimal(5);
                var userNameDb = resultSet.getString(6);
                var password = resultSet.getString(7);
                client = new Client(id, name, surname, gender, budget, userNameDb, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(client);
    }

    public Client findById(Long clientId) {
        return Util.clientTable.stream()
                .filter(client -> client.getId() == clientId)
                .findAny()
                .orElseThrow(() -> new ClietnNotFoundException("Client not found by id: " + clientId));
    }

    public static ClientRepository getInstance() {
        return Optional.ofNullable(clientRepository).orElse(new ClientRepository());
    }

    public Long getMaxId() {
        var maxId = 0L;
        try (var connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER_NAME, Config.DB_PASSWORD)) {

            var statement = connection.createStatement();
            var sql = "select max(id) from client";
            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                maxId = resultSet.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return maxId;
    }
}
