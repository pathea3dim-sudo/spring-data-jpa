package com.example.ecommerceiteapp.feature.product;

import com.example.ecommerceiteapp.feature.category.Category;
import com.example.ecommerceiteapp.feature.category.CategoryRepository;
import com.example.ecommerceiteapp.feature.product.dto.CreateProductRequest;
import com.example.ecommerceiteapp.feature.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public Page<ProductResponse> findAll(int pageNumber, int oageSize) {

        Sort sortById=Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest=PageRequest.of(pageNumber, oageSize, sortById);

        Page<Product> products=productRepository.findAll(pageRequest);

        return  products.map(productMapper::mapProductToProductResponse);
//        return null;
    }

    @Override
    public ProductResponse createNew(CreateProductRequest createProductRequest) {
        //validate category id
        Category category=categoryRepository
                .findById(createProductRequest.categoryId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Category has not been found"));
                //Tranfer data from dto to model
//        Product product=productMapper
    Product product =productMapper.mapCreateProductRequestToProduct(createProductRequest);
            //set generate system data
        product.setCategory(category);
        product.setCode(GenerateUtils.generateProductCode());// ITE-3RD-1234
        product.setSlug(GenerateUtils.generateSlug(createProductRequest.name()));

        Product savedOrder= productRepository.save(product);

        return productMapper.mapProductToProductResponse(savedOrder);
    }


}
