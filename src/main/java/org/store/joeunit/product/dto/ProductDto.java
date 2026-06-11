package org.store.joeunit.product.dto;

import lombok.*;

import java.util.Date;

/*
 * 상품 DTO
 * PRODUCT 테이블 매핑
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {

    // 상품번호
    private Integer productId;

    // 카테고리번호
    private Integer categoryId;

    // 카테고리명
    private String categoryName;

    // 상품명
    private String productName;

    // 제조사
    private String brand;

    // 모델명
    private String modelName;

    // 가격
    private Integer price;

    // 재고
    private Integer stock;

    // CPU
    private String cpu;

    // RAM
    private String ram;

    // 저장공간
    private String storageCapacity;

    // 화면크기
    private String screenSize;

    // 운영체제
    private String os;

    // 색상
    private String color;

    // 상품설명
    private String description;

    // 이미지 파일명
    private String imageName;

    // 이미지 경로
    private String imagePath;

    // SALE, SOLD_OUT, STOP
    private String status;

    // 등록일
    private Date createdAt;

    // 수정일
    private Date updatedAt;
}