package com.adam.backend.shoppingapi.services;

import com.adam.backend.shoppingapi.converter.DTOConverter;
import com.adam.backend.shoppingapi.dtos.ItemDTO;
import com.adam.backend.shoppingapi.dtos.ProductDTO;
import com.adam.backend.shoppingapi.dtos.ShopDTO;
import com.adam.backend.shoppingapi.dtos.ShopReportDTO;
import com.adam.backend.shoppingapi.exceptions.UserNotFoundException;
import com.adam.backend.shoppingapi.models.Shop;
import com.adam.backend.shoppingapi.repositories.ReportRepository;
import com.adam.backend.shoppingapi.repositories.ShopRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShopService {
    private final ShopRepository shopRepository;
    private final ReportRepository reportRepository;
    private final ProductService productService;
    private final UserService userService;

    public ShopService(ShopRepository shopRepository, ReportRepository reportRepository, ProductService productService, UserService userService) {
        this.shopRepository = shopRepository;
        this.reportRepository = reportRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public List<ShopDTO> getAll() {
        List<Shop> shops = shopRepository.findAll();
        return shops
                .stream()
                .map(ShopDTO::convert)
                .toList();
    }

    public List<ShopDTO> getByUser(String userIdentifier) {
        List<Shop> shops = shopRepository
                .findAllByUserIdentifier(userIdentifier);
        return shops
                .stream()
                .map(ShopDTO::convert)
                .toList();
    }

    public List<ShopDTO> getByDate(ShopDTO shopDTO) {
        List<Shop> shops = shopRepository
                .findAllByDateGreaterThan(shopDTO.getDate());
        return shops
                .stream()
                .map(ShopDTO::convert)
                .toList();
    }
    public ShopDTO findById(long ProductId) {
        Optional<Shop> shop = shopRepository.findById(ProductId);
        return shop.map(ShopDTO::convert).orElse(null);
    }

    public ShopDTO save(ShopDTO shopDTO) throws UserNotFoundException {
        if (userService.getUserByCpf(shopDTO.getUserIdentifier()) == null) {
            return null;
        }
        if (!validateProducts(shopDTO.getItems())) {
            return null;
        }
        shopDTO.setTotal(shopDTO.getItems()
                .stream()
                .map(ItemDTO::getPrice)
                .reduce((float) 0, Float::sum));
        Shop shop = Shop.convert(shopDTO);

        // Criando uma nova instância de java.util.Date
        Date date = new Date();

        // Convertendo java.util.Date para java.time.LocalDateTime
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

        shop.setDate(localDateTime);
        shop = shopRepository.save(shop);
        return DTOConverter.convert(shop);
    }

    public List<ShopDTO> getShopsByFilter(
            LocalDateTime dataInicio,
            LocalDateTime dataFim,
            Float valorMinimo) {
        List<Shop> shops =  reportRepository.getShopByFilters(dataInicio, dataFim, valorMinimo);
        return shops
                .stream()
                .map(DTOConverter::convert)
                .toList();
    }

    public ShopReportDTO getReportByDate(
            LocalDateTime dataInicio,
            LocalDateTime dataFim) {
        return reportRepository.getReportByDate(dataInicio, dataFim);
    }

    private boolean validateProducts(List<ItemDTO> items) throws UserNotFoundException {
        for (ItemDTO item : items) {
            ProductDTO productDTO = productService
                    .getProductByIdentifier(
                            item.getProductIdentifier());
            if (productDTO == null) {
                return false;
            }
            item.setPrice(productDTO.getPreco());
        }
        return true;
    }
}
