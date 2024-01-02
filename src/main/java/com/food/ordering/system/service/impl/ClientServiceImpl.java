package com.food.ordering.system.service.impl;

import com.food.ordering.system.dto.ClientDto;
import com.food.ordering.system.entity.Client;
import com.food.ordering.system.exceptions.IncorrectPasswordException;
import com.food.ordering.system.exceptions.InvalidFieldException;
import com.food.ordering.system.exceptions.UserNameNotFoundException;
import com.food.ordering.system.mapper.ClientMapper;
import com.food.ordering.system.repository.ClientRepository;
import com.food.ordering.system.service.inter.ClientService;

import java.util.Objects;
import java.util.Optional;

public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private static ClientServiceImpl clientService;

    private ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public String registration(ClientDto clientDto) {
        checkRequiredFields(clientDto);
        checkPassswordEquality(clientDto.getPassword(), clientDto.getRepeatedPassword());
        var client = ClientMapper.clientDtoToClientEntity(clientDto);

        return clientRepository.insert(client) ?
                "successfully registered" :
                "unsuccessfully registered";
    }

    @Override
    public Client login(String userName, String password) {
        var clientOptional = clientRepository.findByUserName(userName);
        checkLogin(clientOptional, userName, password);
        return clientOptional.get();
    }

    @Override
    public Long getMaxClientId() {
        return clientRepository.getMaxId();
    }

    private void checkRequiredFields(ClientDto clientDto) {
        if (Objects.isNull(clientDto.getId()) ||
                Objects.isNull(clientDto.getName()) ||
                Objects.isNull(clientDto.getGender()) ||
                Objects.isNull(clientDto.getUsername()) ||
                Objects.isNull(clientDto.getPassword()) ||
                Objects.isNull(clientDto.getRepeatedPassword()))
            throw new InvalidFieldException("Invalid field");

    }

    private void checkPassswordEquality(String password, String repeatedPassword) {
        if (!isValidPasswords(password, repeatedPassword))
            throw new IncorrectPasswordException("password not same");
    }

    private boolean isValidPasswords(String password, String repeatedPassword) {
        return password.equals(repeatedPassword);
    }

    private void checkLogin(Optional<Client> optionalClient,
                            String userName,
                            String password) {
        checkUserNameForLogin(optionalClient, userName);
        checkPasswordForUsername(optionalClient, password);
    }

    private void checkUserNameForLogin(Optional<Client> optionalClient,
                                       String userName) {
        if (optionalClient.isEmpty())
            throw new UserNameNotFoundException("username :" + userName + " not found");
    }

    private void checkPasswordForUsername(Optional<Client> optionalClient,
                                          String password) {
        optionalClient.ifPresent(client -> {
            if (!client.getPassword().equals(password))
                throw new IncorrectPasswordException("username or password incorrect");
        });

    }

    public static ClientServiceImpl getInstance(ClientRepository clientRepository) {
        return Optional.ofNullable(clientService).orElse(new ClientServiceImpl(clientRepository));
    }
}
