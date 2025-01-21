package com.fastfood.api.adapters.inbound;

import com.fastfood.api.application.usecases.ProductUseCase;
import com.fastfood.api.domain.product.Category;
import com.fastfood.api.domain.product.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductUseCase productUseCase;

    @PostMapping("/product")
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        return productUseCase.createProduct(productDTO);
    }

    @GetMapping("/product/{id}")
    public ProductDTO findProductById(@PathVariable Long id) {
        return productUseCase.findProductById(id);
    }

    @GetMapping("/product")
    public List<ProductDTO> findProductByCategory(@RequestParam Category category) {
        return productUseCase.findProductByCategory(category);
    }

    @PatchMapping("/product/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productUseCase.updateProduct(id, productDTO);
    }

    @DeleteMapping("/product/{id}")
    public void disableProductById(@PathVariable Long id) {
        productUseCase.disableProductById(id);
    }
}
