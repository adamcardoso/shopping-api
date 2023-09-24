package com.adam.backend.shoppingapi.services.impl;

import com.adam.backend.shoppingapi.dtos.UserDTO;
import com.adam.backend.shoppingapi.services.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDTO getUserByCpf(String cpf) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/user/cpf/" + cpf;
        ResponseEntity<UserDTO> response =
                restTemplate.getForEntity(url, UserDTO.class);
        return response.getBody();
    }
}
