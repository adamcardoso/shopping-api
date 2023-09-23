package com.adam.backend.shoppingapi.services;

import com.adam.backend.shoppingapi.dtos.ProductDTO;
import com.adam.backend.shoppingapi.dtos.UserDTO;
import com.adam.backend.shoppingapi.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductService {
    public ProductDTO getProductByIdentifier(
            String productIdentifier) throws UserNotFoundException {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8081/product/" + productIdentifier;
            ResponseEntity<ProductDTO> response =
                    restTemplate.getForEntity(url, ProductDTO.class);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException();
        }
    }
    public UserDTO getUserByCpf(String cpf) throws UserNotFoundException {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/user/cpf/" + cpf;
            ResponseEntity<UserDTO> response =
                    restTemplate.getForEntity(url, UserDTO.class);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException();
        }
    }
}
