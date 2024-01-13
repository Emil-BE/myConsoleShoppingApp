package com.food.ordering.system.service.inter;

import com.food.ordering.system.dto.ClientDto;
import com.food.ordering.system.entity.Client;

public interface ClientService {
    String registration(ClientDto client);
    Client login(String userName, String password);

    Client findById(Long clientId);

    Long getMaxClientId();
}
