package com.adam.backend.shoppingapi.models;

import com.adam.backend.shoppingapi.dtos.ShopDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="shop")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userIdentifier;
    private Double total;
    private LocalDateTime date;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "item",
            joinColumns = @JoinColumn(name = "shop_id"))
    private List<Item> items;
    // gets e sets
    public static Shop convert(ShopDTO shopDTO) {
        Shop shop = new Shop();
        shop.setId(shopDTO.getId());
        shop.setUserIdentifier(shopDTO.getUserIdentifier());
        shop.setTotal(shopDTO.getTotal());
        shop.setDate(shopDTO.getDate());
        shop.setItems(shopDTO
                .getItems()
                .stream()
                .map(Item::convert)
                .toList());
        return shop;
    }
}
