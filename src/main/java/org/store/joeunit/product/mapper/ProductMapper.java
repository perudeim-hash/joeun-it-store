package org.store.joeunit.product.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.store.joeunit.product.dto.ProductDto;

import java.util.List;
import java.util.Map;
/**
 * ProductMapper
 *
 * MyBatis Mapper 인터페이스
 *
 * Service 계층과 Database를 연결한다.
 *
 * 주요 기능
 * 1. 상품목록 조회
 * 2. 상품상세 조회
 * 3. 상품등록
 * 4. 상품수정
 * 5. 상품삭제
 * 6. 상품검색
 * 7. 카테고리별 조회
 * 8. 페이징 조회
 * 9. 베스트 상품 조회
 * 10. 신규 상품 조회
 * 11. 재고 수정
 * 12. 판매량 수정
 *
 * Service
 *      ↓
 * ProductMapper
 *      ↓
 * ProductMapper.xml
 *      ↓
 * Oracle Database
 */
@Mapper
public interface ProductMapper {

    List<ProductDto> getList();

    ProductDto getById(Integer productId);

    int insert(ProductDto productDto);

    int update(ProductDto productDto);

    int delete(Integer productId);

    List<ProductDto> search(String keyword);

    List<ProductDto> getCategoryList(Integer categoryId);

    List<ProductDto> getPageList(Map<String, Object> paramMap);

    List<ProductDto> getCategoryPageList(Map<String, Object> paramMap);

    List<ProductDto> getBestProducts();

    List<ProductDto> getNewProducts();

    int getTotalCount();

    int getCategoryTotalCount(Integer categoryId);

    int increaseSalesAndDecreaseStock(
            @Param("productId") Integer productId,
            @Param("quantity") Integer quantity
    );

    int decreaseSalesAndIncreaseStock(
            @Param("productId") Integer productId,
            @Param("quantity") Integer quantity
    );
}