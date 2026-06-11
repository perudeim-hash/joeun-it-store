package org.store.joeunit.product.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.store.joeunit.product.dto.ProductDto;

import java.util.List;

/*
 * 상품(Product) Mapper
 *
 * 담당 기능
 * - 상품 목록 조회
 * - 상품 상세 조회
 * - 상품 등록
 * - 상품 수정
 * - 상품 삭제
 * - 상품 검색
 * - 카테고리 조회
 * - 페이징
 */

@Mapper
public interface ProductMapper {

    /*
     * 상품 전체 목록 조회
     */
    List<ProductDto> getList();

    /*
     * 상품 상세 조회
     * 상품번호(PK) 기준 조회
     */
    ProductDto getById(Integer productId);

    /*
     * 상품 등록
     */
    int insert(ProductDto productDto);

    /*
     * 상품 수정
     */
    int update(ProductDto productDto);

    /*
     * 상품 삭제
     */
    int delete(Integer productId);

    /*
     * 상품명 검색
     */
    List<ProductDto> search(String keyword);
}