package com.fastfood.api.adapters.inbound;

import com.fastfood.api.application.usecases.ProductUseCase;
import com.fastfood.api.domain.product.Category;
import com.fastfood.api.domain.product.ProductDTO;
import com.fastfood.api.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductUseCase productUseCase;

    @InjectMocks
    private ProductController productController;

    @Test
    void createProduct_shouldReturnCreatedProduct() {
        ProductDTO productDTO = new ProductDTO();
        Mockito.when(productUseCase.createProduct(productDTO)).thenReturn(productDTO);

        ProductDTO result = productController.createProduct(productDTO);

        assertEquals(productDTO, result);
    }

    @Test
    void findProductById_shouldReturnProduct_whenProductExists() {
        ProductDTO productDTO = new ProductDTO();
        Mockito.when(productUseCase.findProductById(1L)).thenReturn(productDTO);

        ProductDTO result = productController.findProductById(1L);

        assertEquals(productDTO, result);
    }

    @Test
    void findProductById_shouldThrowException_whenProductDoesNotExist() {
        Mockito.when(productUseCase.findProductById(1L)).thenThrow(new ProductNotFoundException("Product not found"));

        assertThrows(ProductNotFoundException.class, () -> productController.findProductById(1L));
    }

    @Test
    void findProductByCategory_shouldReturnProducts() {
        List<ProductDTO> products = List.of(new ProductDTO());
        Mockito.when(productUseCase.findProductByCategory(Category.LANCHE)).thenReturn(products);

        List<ProductDTO> result = productController.findProductByCategory(Category.LANCHE);

        assertEquals(products, result);
    }

    @Test
    void updateProduct_shouldReturnUpdatedProduct() {
        ProductDTO productDTO = new ProductDTO();
        Mockito.when(productUseCase.updateProduct(1L, productDTO)).thenReturn(productDTO);

        ProductDTO result = productController.updateProduct(1L, productDTO);

        assertEquals(productDTO, result);
    }

    @Test
    void disableProductById_shouldCallUseCaseDisableProductById() {
        productController.disableProductById(1L);

        Mockito.verify(productUseCase).disableProductById(1L);
    }
}