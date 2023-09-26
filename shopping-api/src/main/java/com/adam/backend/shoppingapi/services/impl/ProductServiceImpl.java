package com.adam.backend.shoppingapi.services.impl;

import com.adam.backend.shoppingapi.dtos.ProductDTO;
import com.adam.backend.shoppingapi.exceptions.UserNotFoundException;
import com.adam.backend.shoppingapi.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductServiceImpl implements ProductService {

    @Value("${PRODUCT_API_URL:http://localhost:8081/api/product/}")
    private String productApiURL;

    @Override
    public ProductDTO getProductByIdentifier(String productIdentifier) {
        try {
                RestTemplate restTemplate = new RestTemplate();
                String url = productApiURL + productIdentifier;
                ResponseEntity<ProductDTO> response =
                    restTemplate.getForEntity(url, ProductDTO.class);
                return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("Usuário não encontrado!", e);
        }
    }
}
