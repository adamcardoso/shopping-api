package com.adam.backend.shoppingapi.services.interfaces;

import com.adam.backend.shoppingapi.dtos.UserDTO;

public interface UserService {
    UserDTO getUserByCpf(String cpf);
}
