package com.shekhovtsov.core.service;

import com.shekhovtsov.core.converter.ProductConverter;
import com.shekhovtsov.core.dto.PageDto;
import com.shekhovtsov.core.dto.ProductDto;
import com.shekhovtsov.core.dto.SearchDto;
import com.shekhovtsov.core.exception.ResourceNotFoundException;
import com.shekhovtsov.core.model.Category;
import com.shekhovtsov.core.model.Product;
import com.shekhovtsov.core.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryServiceImpl categoryService;
    private final PictureService pictureService;
    private final ProductConverter productConverter;



    @Override
    public PageDto<ProductDto> search(SearchDto searchDto) {
        if (searchDto.getPage() < 1) {
            searchDto.setPage(1);
        }
        BigDecimal minPrice = searchDto.getMinPrice();
        BigDecimal maxPrice = searchDto.getMaxPrice();

        List<Product> products = productRepository.findByPriceBetween(minPrice,maxPrice);
        List<ProductDto> productDtos = products.stream()
                .map(productConverter::entityToDto)
                .collect(Collectors.toList());
        PageDto<ProductDto> out = new PageDto<>();
        out.setPage(1);
        out.setItems(productDtos);
        out.setTotalPages(1);
        return out;
    }


    @Override
    public Optional<ProductDto> findById(Long id) throws ResourceNotFoundException {
        return Optional.ofNullable(productConverter.entityToDto(productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found id " + id))));
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }


    @Override
    public Product createNewProduct(@Valid ProductDto productDto, MultipartFile image) throws ResourceNotFoundException {
        Product product = new Product(productDto.getTitle(),productDto.getPrice(),productDto.getCategoryId());
        productRepository.save(product);
        pictureService.createPicture(image,product);
        return product;
    }
}
