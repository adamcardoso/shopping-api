package com.adam.backend.shoppingapi.services.impl;

import com.adam.backend.shoppingapi.dtos.UserDTO;
import com.adam.backend.shoppingapi.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService {

    @Value("${USER_API_URL:http://localhost:8081/api/product/}")
    private String userApiURL;


    @Override
    public UserDTO getUserByCpf(String cpf, String key) {
        RestTemplate restTemplate = new RestTemplate();
        String url = userApiURL + cpf;
        ResponseEntity<UserDTO> response =
                restTemplate.getForEntity(url, UserDTO.class);
        return response.getBody();
    }
}
