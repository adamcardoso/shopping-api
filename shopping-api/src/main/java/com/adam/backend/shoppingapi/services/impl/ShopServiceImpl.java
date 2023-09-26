package com.adam.backend.shoppingapi.services.impl;

import com.adam.backend.shoppingapi.converter.DTOConverter;
import com.adam.backend.shoppingapi.dtos.*;
import com.adam.backend.shoppingapi.exceptions.ProductNotFoundException;
import com.adam.backend.shoppingapi.exceptions.UserNotFoundException;
import com.adam.backend.shoppingapi.models.Shop;
import com.adam.backend.shoppingapi.repositories.ReportRepository;
import com.adam.backend.shoppingapi.repositories.ShopRepository;
import com.adam.backend.shoppingapi.services.interfaces.ProductService;
import com.adam.backend.shoppingapi.services.interfaces.ShopService;
import com.adam.backend.shoppingapi.services.interfaces.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final ReportRepository reportRepository;
    private final ProductService productService;
    private final UserService userService;

    public ShopServiceImpl(ShopRepository shopRepository, ReportRepository reportRepository, ProductService productService, UserService userService) {
        this.shopRepository = shopRepository;
        this.reportRepository = reportRepository;
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShopDTO> getAll() {
        List<Shop> shops = shopRepository.findAll();
        return shops
                .stream()
                .map(ShopDTO::convert)
                .toList();
    }

    @Override
    public List<ShopDTO> getByUser(String userIdentifier) {
        List<Shop> shops = shopRepository
                .findAllByUserIdentifier(userIdentifier);
        return shops
                .stream()
                .map(ShopDTO::convert)
                .toList();
    }

    @Override
    public List<ShopDTO> getByDate(ShopDTO shopDTO) {
        List<Shop> shops = shopRepository
                .findAllByDateGreaterThan(shopDTO.getDate());
        return shops
                .stream()
                .map(ShopDTO::convert)
                .toList();
    }

    @Override
    @Transactional
    public Optional<ShopDTO> findById(long ProductId) {
        try{
            Optional<Shop> shop = shopRepository.findById(ProductId);
            return shop.map(ShopDTO::convert);
        }catch(Exception e){
            throw new ProductNotFoundException("Product not found");
        }
    }

    @Override
    public ShopDTO save(ShopDTO shopDTO, String key) {
        try{
            UserDTO userDTO = userService.getUserByCpf(shopDTO.getUserIdentifier(), key);
            validateProducts(shopDTO.getItems());

            shopDTO.setTotal(shopDTO.getItems()
                    .stream()
                    .map(ItemDTO::getPrice)
                    .reduce((double) 0, Double::sum));

            Shop shop = Shop.convert(shopDTO);
            shop.setDate(LocalDateTime.now());

            shop = shopRepository.save(shop);
            return DTOConverter.convert(shop);
        }catch (Exception e){
            throw new UserNotFoundException("Usuário não encontrado!", e);
        }
    }

    @Override
    public List<ShopDTO> getShopsByFilter(LocalDateTime dataInicio, LocalDateTime dataFim, Float valorMinimo) {
        List<Shop> shops =  reportRepository.getShopByFilters(dataInicio, dataFim, valorMinimo);
        return shops
                .stream()
                .map(DTOConverter::convert)
                .toList();
    }

    @Override
    public ShopReportDTO getReportByDate(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return reportRepository.getReportByDate(dataInicio, dataFim);
    }

    private boolean validateProducts(List<ItemDTO> items) throws UserNotFoundException {
        for (ItemDTO item : items) {
            ProductDTO productDTO = productService.getProductByIdentifier(item.getProductIdentifier());
            if (productDTO == null) {
                return false;
            }
            item.setPrice(productDTO.getPreco());
        }
        return true;
    }
}
