package org.store.joeunit.order;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping("/test/order")
    public String test() {
        return "주문 기능 테스트 성공! 내 이름으로 커밋 완료!";
    }
}