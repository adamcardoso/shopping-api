package com.adam.backend.shoppingapi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @NotBlank
    private String productIdentifier;
    @NotBlank
    private String nome;
    @NotNull
    private Float preco;

    private String descricao;

    @NotNull
    private CategoryDTO category;

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.category = categoryDTO;
    }

    public CategoryDTO getCategoryDTO() {
        return this.category;
    }
}
