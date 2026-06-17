package org.store.joeunit.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.store.joeunit.product.dao.ProductReviewDao;
import org.store.joeunit.product.dto.ProductReviewDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductReviewService {

    // DAO 주입
    // 실제 DB 작업은 ProductReviewDao가 담당
    private final ProductReviewDao productReviewDao;

    // 특정 상품의 리뷰 목록 조회
    // readOnly = true는 조회 전용이라는 뜻
    @Transactional(readOnly = true)
    public List<ProductReviewDto> getList(Integer productId) {
        return productReviewDao.getList(productId);
    }

    // 리뷰 번호로 리뷰 1개 조회
    // 수정/삭제할 때 해당 리뷰가 존재하는지 확인할 때 사용
    @Transactional(readOnly = true)
    public ProductReviewDto getById(Long reviewId) {
        return productReviewDao.getById(reviewId);
    }

    // 리뷰 등록
    public int register(ProductReviewDto productReviewDto) {
        return productReviewDao.insert(productReviewDto);
    }

    // 리뷰 수정
    public int update(ProductReviewDto productReviewDto) {
        return productReviewDao.update(productReviewDto);
    }

    // 리뷰 삭제
    public int delete(Long reviewId) {
        return productReviewDao.delete(reviewId);
    }

    // 해당 회원이 해당 상품을 구매했는지 확인
    // DAO에서는 COUNT(*)를 int로 반환
    // Service에서는 true / false로 바꿔서 Controller가 쓰기 쉽게 만듦
    @Transactional(readOnly = true)
    public boolean hasPurchased(Long memberId, Integer productId) {
        return productReviewDao.hasPurchased(memberId, productId) > 0;
    }

    // 해당 회원이 이미 이 상품에 리뷰를 작성했는지 확인
    // 상품당 계정 1개 리뷰 제한할 때 사용
    @Transactional(readOnly = true)
    public boolean hasReviewed(Long memberId, Integer productId) {
        return productReviewDao.hasReviewed(memberId, productId) > 0;
    }

    // 상품 평균 별점 조회
    // 리뷰가 없으면 null이 나올 수 있으므로 0.0으로 처리
    // 소수점 한 자리까지 반올림
    @Transactional(readOnly = true)
    public Double getAverageRating(Integer productId) {
        Double avg = productReviewDao.getAverageRating(productId);

        if (avg == null) {
            return 0.0;
        }

        return Math.round(avg * 10) / 10.0;
    }

    // 상품 리뷰 개수 조회
    @Transactional(readOnly = true)
    public int getReviewCount(Integer productId) {
        return productReviewDao.getReviewCount(productId);
    }
}