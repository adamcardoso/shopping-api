package com.adam.backend.shoppingapi.api.interfaces;

import com.adam.backend.shoppingapi.dtos.ShopDTO;
import com.adam.backend.shoppingapi.dtos.ShopReportDTO;
import com.adam.backend.shoppingapi.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

public interface ShopController {
    List<ShopDTO> getShops();

    List<ShopDTO> getShops(@PathVariable String userIdentifier);

    List<ShopDTO> getShops(@RequestBody ShopDTO shopDTO);

    ResponseEntity<ShopDTO> findById(@PathVariable Long id);

    ShopDTO newShop(@Valid @RequestBody ShopDTO shopDTO) throws UserNotFoundException;

    List<ShopDTO> getShopsByFilter(
            @RequestParam(name = "dataInicio")
            @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDateTime dataInicio,
            @RequestParam(name = "dataFim", required=false)
            @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDateTime dataFim,
            @RequestParam(name = "valorMinimo", required=false)
            Float valorMinimo);

    ShopReportDTO getReportByDate(
            @RequestParam(name = "dataInicio")
            @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDateTime dataInicio,
            @RequestParam(name = "dataFim")
            @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDateTime dataFim);
}
