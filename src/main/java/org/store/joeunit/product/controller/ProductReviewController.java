package org.store.joeunit.product.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.store.joeunit.member.dto.MemberDto;
import org.store.joeunit.product.dto.ProductReviewDto;
import org.store.joeunit.product.service.ProductReviewService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product-review")
public class ProductReviewController {

    private final ProductReviewService productReviewService;

    @PostMapping("/write")
    public String write(ProductReviewDto productReviewDto,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        MemberDto loginMember =
                (MemberDto) session.getAttribute("loggedMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        boolean purchased =
                productReviewService.hasPurchased(
                        loginMember.getMemberId(),
                        productReviewDto.getProductId()
                );

        if (!purchased) {
            redirectAttributes.addFlashAttribute(
                    "reviewError",
                    "이 상품을 구매한 회원만 리뷰를 작성할 수 있습니다."
            );

            return "redirect:/product/view?productId="
                    + productReviewDto.getProductId();
        }

        boolean alreadyReviewed =
                productReviewService.hasReviewed(
                        loginMember.getMemberId(),
                        productReviewDto.getProductId()
                );

        if (alreadyReviewed) {
            redirectAttributes.addFlashAttribute(
                    "reviewError",
                    "이 상품에는 이미 리뷰를 작성했습니다."
            );

            return "redirect:/product/view?productId="
                    + productReviewDto.getProductId();
        }

        if (productReviewDto.getRating() == null
                || productReviewDto.getRating() < 1
                || productReviewDto.getRating() > 5) {

            redirectAttributes.addFlashAttribute(
                    "reviewError",
                    "별점은 1점부터 5점까지 선택해야 합니다."
            );

            return "redirect:/product/view?productId="
                    + productReviewDto.getProductId();
        }

        if (productReviewDto.getContent() == null
                || productReviewDto.getContent().trim().isEmpty()) {

            redirectAttributes.addFlashAttribute(
                    "reviewError",
                    "리뷰 내용을 입력해주세요."
            );

            return "redirect:/product/view?productId="
                    + productReviewDto.getProductId();
        }

        if (productReviewDto.getContent().length() > 1000) {
            redirectAttributes.addFlashAttribute(
                    "reviewError",
                    "리뷰는 1000자 이내로 작성해주세요."
            );

            return "redirect:/product/view?productId="
                    + productReviewDto.getProductId();
        }

        productReviewDto.setMemberId(loginMember.getMemberId());

        productReviewService.register(productReviewDto);

        return "redirect:/product/view?productId="
                + productReviewDto.getProductId();
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long reviewId,
                         @RequestParam Integer productId,
                         HttpSession session) {

        MemberDto loginMember =
                (MemberDto) session.getAttribute("loggedMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        ProductReviewDto review =
                productReviewService.getById(reviewId);

        if (review == null) {
            return "redirect:/product/view?productId=" + productId;
        }

        boolean isWriter =
                review.getMemberId().equals(loginMember.getMemberId());

        boolean isAdmin =
                "ADMIN".equals(loginMember.getRole());

        if (!isWriter && !isAdmin) {
            return "redirect:/product/view?productId=" + productId;
        }

        productReviewService.delete(reviewId);

        return "redirect:/product/view?productId=" + productId;
    }
    @PostMapping("/update")
    public String update(ProductReviewDto productReviewDto,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {

        MemberDto loginMember =
                (MemberDto) session.getAttribute("loggedMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        ProductReviewDto savedReview =
                productReviewService.getById(
                        productReviewDto.getReviewId()
                );

        if (savedReview == null) {
            return "redirect:/product/view?productId="
                    + productReviewDto.getProductId();
        }

        boolean isWriter =
                savedReview.getMemberId().equals(
                        loginMember.getMemberId()
                );

        if (!isWriter) {
            return "redirect:/product/view?productId="
                    + productReviewDto.getProductId();
        }

        if (productReviewDto.getRating() == null
                || productReviewDto.getRating() < 1
                || productReviewDto.getRating() > 5) {

            redirectAttributes.addFlashAttribute(
                    "reviewError",
                    "별점은 1점부터 5점까지 선택해야 합니다."
            );

            return "redirect:/product/view?productId="
                    + productReviewDto.getProductId();
        }

        if (productReviewDto.getContent() == null
                || productReviewDto.getContent().trim().isEmpty()) {

            redirectAttributes.addFlashAttribute(
                    "reviewError",
                    "리뷰 내용을 입력해주세요."
            );

            return "redirect:/product/view?productId="
                    + productReviewDto.getProductId();
        }

        if (productReviewDto.getContent().length() > 1000) {
            redirectAttributes.addFlashAttribute(
                    "reviewError",
                    "리뷰는 1000자 이내로 작성해주세요."
            );

            return "redirect:/product/view?productId="
                    + productReviewDto.getProductId();
        }

        productReviewDto.setMemberId(loginMember.getMemberId());

        productReviewService.update(productReviewDto);

        return "redirect:/product/view?productId="
                + productReviewDto.getProductId();
    }
}