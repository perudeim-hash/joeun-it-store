package org.store.joeunit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.store.joeunit.product.service.ProductService;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;

    @GetMapping("/")
    public String main(Model model) {

        model.addAttribute(
                "recommendProducts",
                productService.getBestProducts()
        );

        model.addAttribute(
                "newProducts",
                productService.getNewProducts()
        );
        model.addAttribute(
                "recommendProducts",
                productService.getBestProducts()
        );

        model.addAttribute(
                "newProducts",
                productService.getNewProducts()
        );

        return "common/main";
    }


}
