package org.store.joeunit.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.store.joeunit.product.dto.ProductDto;
import org.store.joeunit.product.mapper.ProductMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {
    /**
     * ProductService
     *
     * 상품 관련 비즈니스 로직을 처리하는 Service 계층
     *
     * Controller는 요청을 받고
     * Service는 실제 업무 로직을 처리한다.
     *
     * 주요 기능
     * 1. 상품 목록 조회
     * 2. 카테고리별 상품 조회
     * 3. 상품 상세 조회
     * 4. 상품 등록
     * 5. 상품 수정
     * 6. 상품 삭제
     * 7. 상품 개수 조회
     * 8. 페이징 처리
     * 9. 베스트 상품 조회
     * 10. 신규 상품 조회
     * 11. 재고 증가/감소 처리
     * 12. 판매량 증가/감소 처리
     *
     * 동작 구조
     *
     * ProductController
     *        ↓
     * ProductService
     *        ↓
     * ProductMapper
     *        ↓
     * Database
     */
    private final ProductMapper productMapper;

    public List<ProductDto> getList() {
        return productMapper.getList();
    }

    public ProductDto getById(Integer productId) {
        return productMapper.getById(productId);
    }

    public List<ProductDto> getCategoryList(Integer categoryId) {
        return productMapper.getCategoryList(categoryId);
    }

    public int insert(ProductDto productDto) {
        return productMapper.insert(productDto);
    }

    public int update(ProductDto productDto) {
        return productMapper.update(productDto);
    }

    public int delete(Integer productId) {
        return productMapper.delete(productId);
    }

    public List<ProductDto> getPageList(int page) {

        int pageSize = 8;
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("startRow", startRow);
        paramMap.put("endRow", endRow);

        return productMapper.getPageList(paramMap);
    }

    public List<ProductDto> getCategoryPageList(Integer categoryId, int page) {

        int pageSize = 8;
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("categoryId", categoryId);
        paramMap.put("startRow", startRow);
        paramMap.put("endRow", endRow);

        return productMapper.getCategoryPageList(paramMap);
    }

    public int getTotalCount() {
        return productMapper.getTotalCount();
    }

    public int getCategoryTotalCount(Integer categoryId) {
        return productMapper.getCategoryTotalCount(categoryId);
    }
    public List<ProductDto> getBestProducts() {
        return productMapper.getBestProducts();
    }

    public List<ProductDto> getNewProducts() {
        return productMapper.getNewProducts();
    }

    public int increaseSalesAndDecreaseStock(Integer productId, Integer quantity) {
        return productMapper.increaseSalesAndDecreaseStock(productId, quantity);
    }
    public int decreaseSalesAndIncreaseStock(Integer productId, Integer quantity) {
        return productMapper.decreaseSalesAndIncreaseStock(productId, quantity);
    }
    public  List<ProductDto> search(String keyword) {
        return productMapper.search(keyword);
    }


}