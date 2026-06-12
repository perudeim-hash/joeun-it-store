package org.store.joeunit.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.store.joeunit.product.dto.ProductDto;
import org.store.joeunit.product.service.ProductService;

/*
 * 상품 Controller
 */

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /*
     * 상품 목록
     * 카테고리 검색
     */
    @GetMapping("/product/list")
    public String list(
            @RequestParam(required = false)
            Integer categoryId,
            Model model
    ) {

        if (categoryId == null) {

            model.addAttribute(
                    "productList",
                    productService.getList()
            );

        } else {

            model.addAttribute(
                    "productList",
                    productService.getCategoryList(categoryId)
            );

        }

        return "product/list";
    }

    /*
     * 상품 등록 화면
     */
    @GetMapping("/product/write")
    public String write() {

        return "product/write";

    }

    /*
     * 상품 상세
     */
    @GetMapping("/product/view")
    public String view(
            @RequestParam Integer productId,
            Model model
    ) {

        model.addAttribute(
                "product",
                productService.getById(productId)
        );

        return "product/view";
    }

    /*
     * 상품 수정 화면
     */
    @GetMapping("/product/modify")
    public String modify(
            @RequestParam Integer productId,
            Model model
    ) {

        model.addAttribute(
                "product",
                productService.getById(productId)
        );

        return "product/modify";
    }

    /*
     * 상품 등록 처리
     */
    @PostMapping("/product/write")
    public String writeProcess(
            ProductDto productDto
    ) throws Exception {

        MultipartFile upload =
                productDto.getUpload();

        if (upload != null && !upload.isEmpty()) {

            String fileName =
                    upload.getOriginalFilename();

            productDto.setImageName(
                    fileName
            );

            productDto.setImagePath(
                    "/images/" + fileName
            );
        }

        productService.insert(
                productDto
        );

        return "redirect:/product/list";
    }

    /*
     * 상품 수정 처리
     */
    @PostMapping("/product/modify")
    public String modifyProcess(
            ProductDto productDto
    ) throws Exception {

        MultipartFile upload =
                productDto.getUpload();

        if (upload != null && !upload.isEmpty()) {

            String fileName =
                    upload.getOriginalFilename();

            productDto.setImageName(
                    fileName
            );

            productDto.setImagePath(
                    "/images/" + fileName
            );
        }

        productService.update(
                productDto
        );

        return "redirect:/product/list";
    }

    /*
     * 상품 삭제
     */
    @GetMapping("/product/delete")
    public String delete(
            @RequestParam Integer productId
    ) {

        productService.delete(
                productId
        );

        return "redirect:/product/list";
    }

}