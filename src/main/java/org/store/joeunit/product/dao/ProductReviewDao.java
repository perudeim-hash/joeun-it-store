package org.store.joeunit.product.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.store.joeunit.product.dto.ProductReviewDto;

import java.util.List;

@Mapper
public interface ProductReviewDao {

    // 특정 상품의 리뷰 목록 조회
    List<ProductReviewDto> getList(
            @Param("productId")
            Integer productId
    );

    // 리뷰 번호로 리뷰 1개 조회
    ProductReviewDto getById(
            @Param("reviewId")
            Long reviewId
    );

    // 리뷰 등록
    int insert(
            ProductReviewDto productReviewDto
    );

    // 리뷰 수정
    int update(
            ProductReviewDto productReviewDto
    );

    // 리뷰 삭제
    int delete(
            @Param("reviewId")
            Long reviewId
    );

    // 해당 회원이 해당 상품을 구매했는지 확인
    int hasPurchased(
            @Param("memberId")
            Long memberId,

            @Param("productId")
            Integer productId
    );

    // 해당 회원이 이미 리뷰를 작성했는지 확인
    int hasReviewed(
            @Param("memberId")
            Long memberId,

            @Param("productId")
            Integer productId
    );

    // 상품 평균 별점 조회
    Double getAverageRating(
            @Param("productId")
            Integer productId
    );

    // 상품 리뷰 개수 조회
    int getReviewCount(
            @Param("productId")
            Integer productId
    );
}