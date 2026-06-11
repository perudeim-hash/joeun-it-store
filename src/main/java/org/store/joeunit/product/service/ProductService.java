package org.store.joeunit.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.store.joeunit.product.dto.ProductDto;
import org.store.joeunit.product.mapper.ProductMapper;

import java.util.List;

/*
 * 상품 Service
 */

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;

    /*
     * 상품 전체 조회
     */
    public List<ProductDto> getList() {

        return productMapper.getList();

    }

    /*
     * 상품 상세 조회
     */
    public ProductDto getById(
            Integer productId
    ) {

        return productMapper.getById(
                productId
        );

    }

    /*
     * 카테고리별 조회
     */
    public List<ProductDto> getCategoryList(
            Integer categoryId
    ) {

        return productMapper.getCategoryList(
                categoryId
        );

    }

    /*
     * 상품 등록
     */
    public int insert(
            ProductDto productDto
    ) {

        return productMapper.insert(
                productDto
        );

    }

    /*
     * 상품 수정
     */
    public int update(
            ProductDto productDto
    ) {

        return productMapper.update(
                productDto
        );

    }

    /*
     * 상품 삭제
     */
    public int delete(
            Integer productId
    ) {

        return productMapper.delete(
                productId
        );

    }

}