package com.food.ordering.system.mapper;

import com.food.ordering.system.dto.ClientDto;
import com.food.ordering.system.entity.Client;

public class ClientMapper {

    private ClientMapper() {
    }

    public static Client clientDtoToClientEntity(ClientDto clientDto) {
        return new Client(clientDto.getId(), clientDto.getName(),
                clientDto.getSurname(),
                clientDto.getGender(), clientDto.getBudget(),
                clientDto.getUsername(), clientDto.getPassword());

    }
}
