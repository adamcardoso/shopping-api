package com.adam.backend.shoppingapi.api.impl;

import com.adam.backend.shoppingapi.api.interfaces.ShopController;
import com.adam.backend.shoppingapi.dtos.ShopDTO;
import com.adam.backend.shoppingapi.dtos.ShopReportDTO;
import com.adam.backend.shoppingapi.exceptions.UserNotFoundException;
import com.adam.backend.shoppingapi.services.interfaces.ShopService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ShopControllerImpl implements ShopController {
    private final ShopService shopService;

    public ShopControllerImpl(ShopService shopService) {
        this.shopService = shopService;
    }

    @Override
    @GetMapping("/shopping")
    public List<ShopDTO> getShops() {
        return shopService.getAll();
    }

    @Override
    @GetMapping("/shopping/shopByUser/{userIdentifier}")
    public List<ShopDTO> getShops(@PathVariable String userIdentifier) {
        return shopService.getByUser(userIdentifier);
    }

    @Override
    @GetMapping("/shopping/shopByDate")
    public List<ShopDTO> getShops(@RequestBody ShopDTO shopDTO) {
        return shopService.getByDate(shopDTO);
    }

    @Override
    @GetMapping("/shopping/{id}")
    public ResponseEntity<ShopDTO> findById(@PathVariable Long id) {
        Optional<ShopDTO> shopDTOOptional = shopService.findById(id);

        return shopDTOOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @PostMapping("/shopping")
    public ShopDTO newShop(@RequestHeader(name = "key") String key,@Valid @RequestBody ShopDTO shopDTO) {
        return shopService.save(shopDTO, key);
    }

    @Override
    @GetMapping("/shopping/search")
    public List<ShopDTO> getShopsByFilter(
            @RequestParam(name = "dataInicio")
            @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDateTime dataInicio,
            @RequestParam(name = "dataFim", required=false)
            @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDateTime dataFim,
            @RequestParam(name = "valorMinimo", required=false)
            Float valorMinimo) {
        return shopService.getShopsByFilter(dataInicio, dataFim, valorMinimo);
    }

    @Override
    @GetMapping("/shopping/report")
    public ShopReportDTO getReportByDate(
            @RequestParam(name = "dataInicio")
            @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDateTime dataInicio,
            @RequestParam(name = "dataFim")
            @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDateTime dataFim) {
        return shopService.getReportByDate(dataInicio, dataFim);
    }
}
