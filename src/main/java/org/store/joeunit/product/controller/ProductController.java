package org.store.joeunit.product.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.store.joeunit.member.dto.MemberDto;
import org.store.joeunit.product.dto.ProductDto;
import org.store.joeunit.product.service.ProductReviewService;
import org.store.joeunit.product.service.ProductService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductReviewService productReviewService;

    @GetMapping("/products")
    public String products(
            @RequestParam(defaultValue = "0") Integer categoryId) {

        return "redirect:/product/list?categoryId=" + categoryId;
    }

    @GetMapping("/product/list")
    public String list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) Integer categoryId,
            Model model) {

        List<ProductDto> productDtoList;
        int totalCount;

        if (categoryId == null || categoryId == 0) {
            productDtoList = productService.getPageList(page);
            totalCount = productService.getTotalCount();
        } else {
            productDtoList = productService.getCategoryPageList(categoryId, page);
            totalCount = productService.getCategoryTotalCount(categoryId);
        }

        int totalPage = (int) Math.ceil((double) totalCount / 8);

        model.addAttribute("productList", productDtoList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("categoryId", categoryId);

        return "product/list";
    }

    @GetMapping("/product/write")
    public String write() {
        return "product/write";
    }

    @GetMapping("/product/view")
    public String view(
            @RequestParam Integer productId,
            Model model,
            HttpSession session) {

        model.addAttribute(
                "product",
                productService.getById(productId)
        );

        model.addAttribute(
                "reviewList",
                productReviewService.getList(productId)
        );

        model.addAttribute(
                "averageRating",
                productReviewService.getAverageRating(productId)
        );

        model.addAttribute(
                "reviewCount",
                productReviewService.getReviewCount(productId)
        );

        MemberDto loginMember =
                (MemberDto) session.getAttribute("loggedMember");

        model.addAttribute(
                "loginMember",
                loginMember
        );

        boolean canWriteReview = false;
        boolean hasReviewed = false;

        if (loginMember != null) {
            boolean hasPurchased =
                    productReviewService.hasPurchased(
                            loginMember.getMemberId(),
                            productId
                    );

            hasReviewed =
                    productReviewService.hasReviewed(
                            loginMember.getMemberId(),
                            productId
                    );

            canWriteReview = hasPurchased && !hasReviewed;
        }

        model.addAttribute("canWriteReview", canWriteReview);
        model.addAttribute("hasReviewed", hasReviewed);

        return "product/view";
    }

    @GetMapping("/product/modify")
    public String modify(
            @RequestParam Integer productId,
            Model model) {

        model.addAttribute(
                "product",
                productService.getById(productId)
        );

        return "product/modify";
    }

    @PostMapping("/product/write")
    public String writeProcess(
            ProductDto productDto) throws Exception {

        // 가격 음수 방지
        if (productDto.getPrice() < 0) {
            productDto.setPrice(0);
        }

        // 재고 음수 방지
        if (productDto.getStock() < 0) {
            productDto.setStock(0);
        }

        MultipartFile upload =
                productDto.getUpload();

        if (upload != null && !upload.isEmpty()) {

            String originalFileName =
                    upload.getOriginalFilename();

            String savedFileName =
                    UUID.randomUUID() + "_" + originalFileName;

            Path uploadPath =
                    Paths.get("uploads");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            upload.transferTo(
                    uploadPath.resolve(savedFileName)
            );

            productDto.setImageName(savedFileName);
            productDto.setImagePath("/uploads/" + savedFileName);
        }

        productService.insert(productDto);

        return "redirect:/product/list";
    }

    @PostMapping("/product/modify")
    public String modifyProcess(
            ProductDto productDto) throws Exception {

        // 가격 음수 방지
        if (productDto.getPrice() < 0) {
            productDto.setPrice(0);
        }

        // 재고 음수 방지
        if (productDto.getStock() < 0) {
            productDto.setStock(0);
        }

        MultipartFile upload =
                productDto.getUpload();

        if (upload != null && !upload.isEmpty()) {

            String originalFileName =
                    upload.getOriginalFilename();

            String savedFileName =
                    UUID.randomUUID() + "_" + originalFileName;

            Path uploadPath =
                    Paths.get("uploads");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            upload.transferTo(
                    uploadPath.resolve(savedFileName)
            );

            productDto.setImageName(savedFileName);
            productDto.setImagePath("/uploads/" + savedFileName);
        }

        productService.update(productDto);

        return "redirect:/product/list";
    }

    @GetMapping("/product/delete")
    public String delete(
            @RequestParam Integer productId) {

        productService.delete(productId);

        return "redirect:/product/list";
    }

    @GetMapping("/product/category/{categoryId}")
    @ResponseBody
    public Map<String, Object> categoryId(
            @PathVariable Integer categoryId,
            @RequestParam(defaultValue = "1") int page) {

        List<ProductDto> productList;
        int totalCount;

        if (categoryId == 0) {
            productList = productService.getPageList(page);
            totalCount = productService.getTotalCount();
        } else {
            productList =
                    productService.getCategoryPageList(
                            categoryId,
                            page
                    );

            totalCount =
                    productService.getCategoryTotalCount(
                            categoryId
                    );
        }

        int totalPage =
                (int) Math.ceil((double) totalCount / 8);

        Map<String, Object> result =
                new HashMap<>();

        result.put("productList", productList);
        result.put("currentPage", page);
        result.put("totalPage", totalPage);

        return result;
    }
}