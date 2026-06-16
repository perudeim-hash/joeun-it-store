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


}
