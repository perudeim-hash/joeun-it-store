package org.store.joeunit.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.store.joeunit.product.service.ProductService;
import org.store.joeunit.product.dto.ProductDto;

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
     * 상품 등록 처리
     */
    @PostMapping("/product/write")
    public String writeProcess(ProductDto productDto) {
        productService.insert(productDto);
        return "redirect:/product/list";
    }
}