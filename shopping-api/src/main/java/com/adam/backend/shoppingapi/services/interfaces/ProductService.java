package com.adam.backend.shoppingapi.services.interfaces;

import com.adam.backend.shoppingapi.dtos.ProductDTO;

public interface ProductService {
    ProductDTO getProductByIdentifier(String productIdentifier);
}
