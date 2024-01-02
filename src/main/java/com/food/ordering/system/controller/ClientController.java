package com.food.ordering.system.controller;

import com.food.ordering.system.dto.ClientDto;
import com.food.ordering.system.entity.Client;
import com.food.ordering.system.service.inter.ClientService;
import com.food.ordering.system.service.impl.ClientServiceImpl;

import java.util.Optional;

public class ClientController {
    private final ClientService clientService;
    private static ClientController clientController;

    private ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    public String registration(ClientDto clientDto) {
        return clientService.registration(clientDto);
    }

    public Client login(String userName, String password) {
        return clientService.login(userName, password);
    }

    public Long getMaxClientId(){
        return clientService.getMaxClientId();
    }

    public static ClientController getInstance(ClientService clientService) {
        return Optional.ofNullable(clientController)
                .orElse(new ClientController(clientService));

    }
}
