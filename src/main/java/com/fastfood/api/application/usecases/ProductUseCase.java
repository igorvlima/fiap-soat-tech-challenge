package com.fastfood.api.application.usecases;

import com.fastfood.api.domain.product.Category;
import com.fastfood.api.domain.product.ProductDTO;

import java.util.List;

public interface ProductUseCase {

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO findProductById(Long id);

    List<ProductDTO> findProductByCategory(Category category);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    void disableProductById(Long id);
}
