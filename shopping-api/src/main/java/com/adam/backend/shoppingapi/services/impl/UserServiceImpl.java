package com.adam.backend.shoppingapi.services.impl;

import com.adam.backend.shoppingapi.dtos.UserDTO;
import com.adam.backend.shoppingapi.exceptions.UserNotFoundException;
import com.adam.backend.shoppingapi.services.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class UserServiceImpl implements UserService {

    private static final String userApiURL = "http://localhost:8080/api";


    @Override
    public UserDTO getUserByCpf(String cpf, String key) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromHttpUrl(userApiURL + "/user/cpf/" + cpf);
            builder.queryParam("key", key);
            ResponseEntity<UserDTO> response = restTemplate
                    .getForEntity(builder.toUriString(), UserDTO.class);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("Usuário não encontrado!", e);
        }
    }
}
