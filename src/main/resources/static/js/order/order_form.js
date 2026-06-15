// 입력값이 바뀔 때마다 화면의 총 결제금액을 다시 계산하는 함수
function updateTotal() {
    const price = parseInt(document.getElementById("orderPrice").value) || 0;
    const qty = parseInt(document.getElementById("quantity").value) || 0;
    const total = price * qty;

    // 계산된 금액을 화면에 천 단위 콤마(,) 찍어서 보여주기
    document.getElementById("totalPriceDisplay").innerText = "총 결제금액: " + total.toLocaleString() + "원";
}

function placeOrder() {
    // 1. 현재 입력된 값들 가져와서 총액 계산
    const price = parseInt(document.getElementById("orderPrice").value) || 0;
    const qty = parseInt(document.getElementById("quantity").value) || 0;
    const total = price * qty;

    // 2. 서버로 보낼 데이터 세팅
    const orderData = {
        memberId: 2,
        receiverName: "테스트구매자",
        receiverPhone: "010-1234-5678",
        zipcode: "12345",
        address: "서울시 강남구",
        detailAddress: "테스트빌딩",
        totalPrice: total, // 동적 계산된 총액 적용
        discountPrice: 0,
        finalPrice: total, // 동적 계산된 총액 적용
        memberMembership: "GOLD",
        orderItems: [
            {
                productId: document.getElementById("productId").value,
                productName: document.getElementById("productName").value,
                orderPrice: price,
                quantity: qty,
                itemTotalPrice: total // 동적 계산된 총액 적용
            }
        ]
    };

    // 3. 서버 통신
    fetch('/api/order/create', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(orderData)
    })
        .then(response => {
            if (response.ok) {
                alert("결제가 완료되었습니다!");
                window.location.href = "/order/list";
            } else {
                alert("서버 오류가 발생했습니다.");
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert("통신 중 에러가 발생했습니다.");
        });
}