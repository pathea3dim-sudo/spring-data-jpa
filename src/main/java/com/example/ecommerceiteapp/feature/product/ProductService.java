package com.example.ecommerceiteapp.feature.product;

import com.example.ecommerceiteapp.feature.product.dto.CreateProductRequest;
import com.example.ecommerceiteapp.feature.product.dto.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {


    /**
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */

    Page<ProductResponse> findAll(int pageNumber, int pageSize);




    /**
     * Create a new product
     * @param createProductRequest is requesting data for creating product
     * @return {@link ProductResponse}
     * @autorn tang_kiki
     *
     */


    ProductResponse createNew(CreateProductRequest createProductRequest);






}
