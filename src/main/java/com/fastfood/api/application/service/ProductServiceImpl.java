package com.fastfood.api.application.service;

import com.fastfood.api.application.usecases.ProductUseCase;
import com.fastfood.api.domain.product.Category;
import com.fastfood.api.domain.product.ProductDTO;
import com.fastfood.api.domain.product.ProductRepository;
import com.fastfood.api.utils.mappers.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductUseCase {
    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        var product = productRepository.save(mapper.DTOtoDomain(productDTO));
        return mapper.domainToDTO(product);
    }

    @Override
    public ProductDTO findProductById(Long id) {
        var product = productRepository.findById(id).orElse(null);
        return mapper.domainToDTO(product);
    }

    @Override
    public List<ProductDTO> findProductByCategory(Category category) {
        var products = productRepository.findProductByCategory(category);
        return mapper.domainToDTOList(products);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        var product = productRepository.updateProduct(id, mapper.DTOtoDomain(productDTO));
        return mapper.domainToDTO(product);
    }

    @Override
    public void disableProductById(Long id) {
        productRepository.deleteById(id);
    }
}
