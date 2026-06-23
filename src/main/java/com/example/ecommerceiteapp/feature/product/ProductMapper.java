package com.example.ecommerceiteapp.feature.product;


import com.example.ecommerceiteapp.feature.product.dto.CreateProductRequest;
import com.example.ecommerceiteapp.feature.product.dto.ProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product mapCreateProductRequestToProduct(CreateProductRequest createProductRequest);

    ProductResponse mapProductToProductResponse(Product product);


}
