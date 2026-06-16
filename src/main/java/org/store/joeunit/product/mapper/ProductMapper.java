package org.store.joeunit.product.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.store.joeunit.product.dto.ProductDto;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {

    List<ProductDto> getList();

    ProductDto getById(Integer productId);

    int insert(ProductDto productDto);

    int update(ProductDto productDto);

    int delete(Integer productId);

    List<ProductDto> search(String keyword);

    List<ProductDto> getCategoryList(Integer categoryId);

    List<ProductDto> getPageList(Map<String,Object> paramMap);

    List<ProductDto> getCategoryPageList(Map<String,Object> paramMap);

    List<ProductDto> getBestProducts();

    List<ProductDto> getNewProducts();


    int getTotalCount();

    int getCategoryTotalCount(Integer categoryId);

}