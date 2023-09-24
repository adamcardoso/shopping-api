package com.adam.backend.shoppingapi.services.interfaces;

import com.adam.backend.shoppingapi.dtos.ProductDTO;
import com.adam.backend.shoppingapi.dtos.UserDTO;
import com.adam.backend.shoppingapi.exceptions.UserNotFoundException;

public interface ProductService {
    ProductDTO getProductByIdentifier(String productIdentifier) throws UserNotFoundException;

    UserDTO getUserByCpf(String cpf) throws UserNotFoundException;
}
