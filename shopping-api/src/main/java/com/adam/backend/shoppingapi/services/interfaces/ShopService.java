package com.adam.backend.shoppingapi.services.interfaces;

import com.adam.backend.shoppingapi.dtos.ShopDTO;
import com.adam.backend.shoppingapi.dtos.ShopReportDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShopService {
    List<ShopDTO> getAll();

    List<ShopDTO> getByUser(String userIdentifier);

    ShopDTO save(ShopDTO shopDTO, String key);

    List<ShopDTO> getByDate(ShopDTO shopDTO);

    Optional<ShopDTO> findById(long ProductId);

    List<ShopDTO> getShopsByFilter(LocalDateTime dataInicio, LocalDateTime dataFim, Float valorMinimo);

    ShopReportDTO getReportByDate(LocalDateTime dataInicio, LocalDateTime dataFim);
}
