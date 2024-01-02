package com.food.ordering.system.repository;

import com.food.ordering.system.db.Util;
import com.food.ordering.system.entity.Client;
import com.food.ordering.system.enums.Gender;
import com.food.ordering.system.exceptions.ClietnNotFoundException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLClientInfoException;
import java.sql.Statement;
import java.util.Optional;

public class ClientRepository {
    private static ClientRepository clientRepository;
    private static String DB_URL = "jdbc:postgresql://localhost/shopping";
    private static String DB_USER_NAME = "postgres";
    private static String DB_PASSWORD = "12345";

    private ClientRepository() {

    }

    public boolean insert(Client client) {
        try (var connection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);
        ) {
            var statement = connection.createStatement();
            var sql = "insert into client(id, name, surname, gender, budget, username, password)\n" +
                    "values(" + client.getId() + "," +
                    "'" + client.getName() + "'," +
                    "'" + client.getSurname() + "'," +
                    "'" + client.getGender().name() + "'," +
                    "" + client.getBudget() + "," +
                    "'" + client.getUsername() + "'," +
                    "'" + client.getPassword() + "')";
            statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public Optional<Client> findByUserName(String userName) {
        Client client = null;
        try(var connection = DriverManager.getConnection(DB_URL,DB_USER_NAME,DB_PASSWORD)) {
            var statement = connection.createStatement();
            var sql = "select * from client where username = '" + userName+ "'";
            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                var id = resultSet.getLong(1);
                var name = resultSet.getString(2);
                var surname = resultSet.getString(3);
                var gender = Gender.valueOf(resultSet.getString(4));
                var budget = resultSet.getBigDecimal(5);
                var userNameDb = resultSet.getString(6);
                var password = resultSet.getString(7);
                client = new Client(id, name, surname,gender,budget,userNameDb, password);
            }
        }catch (Exception e){
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

    public Long getMaxId(){
        var maxId = 0L;
        try(var connection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD)) {

            var statement = connection.createStatement();
            var sql = "select max(id) from client";
            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                maxId = resultSet.getLong(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return maxId;
    }
}
