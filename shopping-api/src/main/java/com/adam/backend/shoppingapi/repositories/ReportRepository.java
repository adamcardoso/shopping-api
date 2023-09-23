package com.adam.backend.shoppingapi.repositories;

import com.adam.backend.shoppingapi.dtos.ShopReportDTO;
import com.adam.backend.shoppingapi.models.Shop;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportRepository {
    List<Shop> getShopByFilters(
            LocalDateTime dataInicio,
            LocalDateTime dataFim,
            Float valorMinimo);
    ShopReportDTO getReportByDate(
            LocalDateTime dataInicio,
            LocalDateTime dataFim);
}
