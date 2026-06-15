function placeOrder() {
    const totalPrice = document.getElementById('hiddenTotalPrice').value;
    const finalPrice = document.getElementById('hiddenFinalPrice').value;
    const discountRate = document.getElementById('hiddenDiscountRate').value || 0;
    const discountAmount = document.getElementById('hiddenDiscountAmount').value || 0;

    const orderData = {
        totalPrice: parseInt(totalPrice),
        finalPrice: parseInt(finalPrice),
        discountRate: parseInt(discountRate),
        discountAmount: parseInt(discountAmount),
        receiverName: "구매자",
        receiverPhone: "010-0000-0000",
        zipcode: "00000",
        address: "배송지 주소",
        detailAddress: "상세 주소"
    };

    fetch('/api/order/create', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(orderData)
    })
        .then(res => res.text())
        .then(data => {
            if (data === "success") {
                alert("결제가 완료되었습니다!");
                // 🚀 성공 시 바로 주문 내역으로 강제 이동
                window.location.replace("/order/list");
            } else {
                alert("결제 실패 상세 원인: " + data);
            }
        })
        .catch(err => {
            console.error(err);
            alert("서버와의 통신 중 오류가 발생했습니다.");
        });
}