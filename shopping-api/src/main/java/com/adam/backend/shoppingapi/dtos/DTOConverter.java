package com.adam.backend.shoppingapi.dtos;

import com.adam.backend.shoppingapi.models.Item;
import com.adam.backend.shoppingapi.models.Shop;

import java.util.stream.Collectors;

public class DTOConverter {

    private DTOConverter() {
        // Private constructor to prevent instantiation
        throw new UnsupportedOperationException("Utility class - do not instantiate");
    }

    public static ItemDTO convert(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setProductIdentifier(
                item.getProductIdentifier());
        itemDTO.setPrice(item.getPrice());
        return itemDTO;
    }
    public static ShopDTO convert(Shop shop) {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setUserIdentifier(shop.getUserIdentifier());
        shopDTO.setTotal(shop.getTotal());
        shopDTO.setDate(shop.getDate());
        shopDTO.setItems(shop
                .getItems()
                .stream()
                .map(DTOConverter::convert)
                .collect(Collectors.toList()));
        return shopDTO;
    }
}
