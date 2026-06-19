package org.store.joeunit.product.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

/**
 * ProductDto
 *
 * 상품 정보를 저장하는 DTO(Data Transfer Object)
 *
 * PRODUCT 테이블과 매핑된다.
 *
 * 주요 데이터
 * - 상품번호(productId)
 * - 카테고리(categoryId)
 * - 상품명(productName)
 * - 브랜드(brand)
 * - 모델명(modelName)
 * - 가격(price)
 * - 재고(stock)
 * - CPU(cpu)
 * - RAM(ram)
 * - 저장공간(storageCapacity)
 * - 화면크기(screenSize)
 * - 운영체제(os)
 * - 색상(color)
 * - 상품설명(description)
 * - 이미지(imageName, imagePath)
 * - 판매량(salesCount)
 * - 평균평점(averageRating)
 * - 리뷰개수(reviewCount)
 *
 * 계층 간 데이터 전달용 객체
 *
 * Controller
 *      ↓
 * Service
 *      ↓
 * Mapper
 */
public class ProductDto {

    private Integer productId;

    private Integer categoryId;

    private String categoryName;

    private String productName;

    private String brand;

    private String modelName;

    private Integer price;

    private Integer stock;

    private String cpu;

    private String ram;

    private String storageCapacity;

    private String screenSize;

    private String os;

    private String color;

    private String description;

    private String imageName;

    private String imagePath;

    private MultipartFile upload;

    private String status;

    private Date createdAt;

    private Date updatedAt;

    private Integer salesCount;

    private Double averageRating;

    private Integer reviewCount;
}