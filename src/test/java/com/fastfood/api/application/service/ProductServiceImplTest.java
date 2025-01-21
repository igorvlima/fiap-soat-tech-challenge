package com.fastfood.api.application.service;

import com.fastfood.api.domain.product.Product;
import com.fastfood.api.domain.product.ProductDTO;
import com.fastfood.api.domain.product.ProductRepository;
import com.fastfood.api.utils.mappers.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void createProduct_shouldReturnCreatedProduct() {
        ProductDTO productDTO = new ProductDTO();
        Product product = new Product();
        Mockito.when(mapper.DTOtoDomain(productDTO)).thenReturn(product);
        Mockito.when(productRepository.save(product)).thenReturn(product);
        Mockito.when(mapper.domainToDTO(product)).thenReturn(productDTO);

        ProductDTO result = productService.createProduct(productDTO);

        assertEquals(productDTO, result);
    }

    @Test
    void findProductById_shouldReturnProduct_whenProductExists() {
        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(mapper.domainToDTO(product)).thenReturn(productDTO);

        ProductDTO result = productService.findProductById(1L);

        assertEquals(productDTO, result);
    }

    @Test
    void findProductById_shouldReturnNull_whenProductDoesNotExist() {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ProductDTO result = productService.findProductById(1L);

        assertNull(result);
    }

    @Test
    void findProductByCategory_shouldReturnProducts() {
        List<Product> products = List.of(new Product());
        List<ProductDTO> productDTOs = List.of(new ProductDTO());
        Mockito.when(productRepository.findProductByCategory(com.fastfood.api.domain.product.Category.LANCHE)).thenReturn(products);
        Mockito.when(mapper.domainToDTOList(products)).thenReturn(productDTOs);

        List<ProductDTO> result = productService.findProductByCategory(com.fastfood.api.domain.product.Category.LANCHE);

        assertEquals(productDTOs, result);
    }

    @Test
    void updateProduct_shouldReturnUpdatedProduct() {
        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();
        Mockito.when(productRepository.updateProduct(1L, product)).thenReturn(product);
        Mockito.when(mapper.DTOtoDomain(productDTO)).thenReturn(product);
        Mockito.when(mapper.domainToDTO(product)).thenReturn(productDTO);

        ProductDTO result = productService.updateProduct(1L, productDTO);

        assertEquals(productDTO, result);
    }

    @Test
    void disableProductById_shouldCallRepositoryDeleteById() {
        productService.disableProductById(1L);

        Mockito.verify(productRepository).deleteById(1L);
    }
}