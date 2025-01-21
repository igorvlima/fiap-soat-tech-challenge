package com.fastfood.api.adapters.outbound.repositories.product;

import com.fastfood.api.adapters.outbound.entities.product.JpaProductEntity;
import com.fastfood.api.adapters.outbound.entities.product.JpaProductImageEntity;
import com.fastfood.api.domain.product.Category;
import com.fastfood.api.domain.product.Product;
import com.fastfood.api.domain.product.ProductImage;
import com.fastfood.api.exceptions.ProductNotFoundException;
import com.fastfood.api.utils.mappers.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryImplTest {

    @Mock
    private JpaProductRepository jpaProductRepository;

    @Mock
    private JpaProductImageRepository jpaProductImageRepository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductRepositoryImpl productRepository;

    @Test
    void save_shouldReturnSavedProduct() {
        Product product = new Product(1L, "Hamburguer", BigDecimal.TEN, "Delicioso hamburguer", Category.LANCHE, true, List.of(new ProductImage("url")), LocalDateTime.now(), LocalDateTime.now());
        JpaProductEntity productEntity = new JpaProductEntity(product);
        List<JpaProductImageEntity> imageEntities = List.of(new JpaProductImageEntity());

        Mockito.when(jpaProductRepository.save(Mockito.any(JpaProductEntity.class))).thenReturn(productEntity);
        Mockito.when(jpaProductImageRepository.saveAll(Mockito.anyList())).thenReturn(imageEntities);
        Mockito.when(mapper.jpaToDomain(productEntity, imageEntities)).thenReturn(product);

        Product result = productRepository.save(product);

        assertEquals(product, result);
    }

    @Test
    void findAll_shouldReturnListOfProducts() {
        List<JpaProductEntity> productEntities = List.of(new JpaProductEntity());
        List<JpaProductImageEntity> imageEntities = List.of(new JpaProductImageEntity());
        List<Product> products = List.of(new Product());

        Mockito.when(jpaProductRepository.findAllById(Mockito.anyList())).thenReturn(productEntities);
        Mockito.when(jpaProductImageRepository.findAll()).thenReturn(imageEntities);
        Mockito.when(mapper.jpaToDomainList(productEntities, imageEntities)).thenReturn(products);

        List<Product> result = productRepository.findAll(List.of(1L));

        assertEquals(products, result);
    }

    @Test
    void findById_shouldReturnProduct_whenProductExists() {
        JpaProductEntity productEntity = new JpaProductEntity();
        List<JpaProductImageEntity> imageEntities = List.of(new JpaProductImageEntity());
        Product product = new Product();

        Mockito.when(jpaProductRepository.findById(1L)).thenReturn(Optional.of(productEntity));
        Mockito.when(jpaProductImageRepository.findByProductId(1L)).thenReturn(imageEntities);
        Mockito.when(mapper.jpaToDomain(productEntity, imageEntities)).thenReturn(product);

        Optional<Product> result = productRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(product, result.get());
    }

    @Test
    void findById_shouldThrowException_whenProductDoesNotExist() {
        Mockito.when(jpaProductRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productRepository.findById(1L));
    }

    @Test
    void findProductByCategory_shouldReturnProducts() {
        List<JpaProductEntity> productEntities = List.of(new JpaProductEntity());
        List<JpaProductImageEntity> imageEntities = List.of(new JpaProductImageEntity());
        List<Product> products = List.of(new Product());

        Mockito.when(jpaProductRepository.findProductByCategory("LANCHE")).thenReturn(productEntities);
        Mockito.when(jpaProductImageRepository.findAll()).thenReturn(imageEntities);
        Mockito.when(mapper.jpaToDomainList(productEntities, imageEntities)).thenReturn(products);

        List<Product> result = productRepository.findProductByCategory(Category.LANCHE);

        assertEquals(products, result);
    }

    @Test
    void deleteById_shouldCallRepositoryDisableProductById() {
        productRepository.deleteById(1L);

        Mockito.verify(jpaProductRepository).disableProductById(1L);
    }

    @Test
    void updateProduct_shouldReturnUpdatedProduct() {
        Product product = new Product(1L, "Hamburguer", BigDecimal.TEN, "Delicioso hamburguer", Category.LANCHE, true, List.of(new ProductImage("url")), LocalDateTime.now(), LocalDateTime.now());
        JpaProductEntity productEntity = new JpaProductEntity(product);
        List<JpaProductImageEntity> imageEntities = List.of(new JpaProductImageEntity());

        Mockito.when(jpaProductRepository.findById(1L)).thenReturn(Optional.of(productEntity));
        Mockito.when(jpaProductRepository.save(Mockito.any(JpaProductEntity.class))).thenReturn(productEntity);
        Mockito.when(jpaProductImageRepository.saveAll(Mockito.anyList())).thenReturn(imageEntities);
        Mockito.when(mapper.jpaToDomain(productEntity, imageEntities)).thenReturn(product);

        Product result = productRepository.updateProduct(1L, product);

        assertEquals(product, result);
    }
}