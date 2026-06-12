package org.store.joeunit.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.store.joeunit.product.dto.ProductDto;
import org.store.joeunit.product.service.ProductService;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

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

        int totalPage = (int)Math.ceil((double)totalCount / 8);

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
    public String view(@RequestParam Integer productId, Model model) {

        model.addAttribute("product", productService.getById(productId));

        return "product/view";
    }

    @GetMapping("/product/modify")
    public String modify(@RequestParam Integer productId, Model model) {

        model.addAttribute("product", productService.getById(productId));

        return "product/modify";
    }

    @PostMapping("/product/write")
    public String writeProcess(ProductDto productDto) throws Exception {

        MultipartFile upload = productDto.getUpload();

        if (upload != null && !upload.isEmpty()) {

            String fileName = upload.getOriginalFilename();

            productDto.setImageName(fileName);
            productDto.setImagePath("/images/" + fileName);
        }

        productService.insert(productDto);

        return "redirect:/product/list";
    }

    @PostMapping("/product/modify")
    public String modifyProcess(ProductDto productDto) throws Exception {

        MultipartFile upload = productDto.getUpload();

        if (upload != null && !upload.isEmpty()) {

            String fileName = upload.getOriginalFilename();

            productDto.setImageName(fileName);
            productDto.setImagePath("/images/" + fileName);
        }

        productService.update(productDto);

        return "redirect:/product/list";
    }

    @GetMapping("/product/delete")
    public String delete(@RequestParam Integer productId) {

        productService.delete(productId);

        return "redirect:/product/list";
    }

    @GetMapping("/product/category/{categoryId}")
    @ResponseBody
    public Map<String,Object> categoryId(
            @PathVariable Integer categoryId,
            @RequestParam(defaultValue = "1") int page) {

        List<ProductDto> productList;
        int totalCount;

        if(categoryId == 0){

            productList = productService.getPageList(page);
            totalCount = productService.getTotalCount();

        } else {

            productList = productService.getCategoryPageList(categoryId,page);
            totalCount = productService.getCategoryTotalCount(categoryId);

        }

        int totalPage =
                (int)Math.ceil((double)totalCount / 8);

        Map<String,Object> result =
                new HashMap<>();

        result.put("productList",productList);
        result.put("currentPage",page);
        result.put("totalPage",totalPage);

        return result;
    }
}