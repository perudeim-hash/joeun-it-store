/**
 * [주문서 작성 로직]
 * 수량 및 단가 변경 시 실시간으로 총 결제 금액을 계산하여 화면에 반영합니다.
 */
function calculateTotal() {
    const qty = document.getElementById('quantity').value;
    const price = document.getElementById('orderPrice').value;
    const total = qty * price;

    // 계산된 총액을 숨겨진 input과 화면 text에 각각 업데이트
    document.getElementById('totalAmount').value = total;
    document.getElementById('totalAmountText').innerText = total.toLocaleString(); // 천단위 콤마
}

/**
 * [주문 데이터 전송 로직]
 * 결제하기 버튼 클릭 시 실행되며, 폼에 입력된 데이터를 모아 백엔드(서버)로 전송합니다.
 */
function submitOrder() {
    const qty = document.getElementById('quantity').value;
    const price = document.getElementById('orderPrice').value;
    const total = qty * price;

    // 백엔드로 보낼 JSON 데이터 객체 생성
    const orderData = {
        // [연동 포인트: 회원가입/로그인 파트]
        // 현재는 더미데이터 2번(user1)으로 고정. 추후 Session에서 로그인한 회원의 PK값을 가져와야 함.
        memberId: 2,

        totalPrice: total,
        discountPrice: 0,
        finalPrice: total,
        memberMembership: 'BRONZE', // 추후 회원 정보에서 가져올 등급

        // [연동 포인트: 배송지 파트] 추후 배송지 입력 폼에서 값을 가져오도록 수정 필요
        receiverName: '테스트유저',
        receiverPhone: '010-2222-2222',
        zipcode: '06236',
        address: '서울특별시 강남구 테헤란로',
        detailAddress: '101호',

        // [연동 포인트: 상품/장바구니 파트] 장바구니에 담긴 여러 상품을 배열 형태로 담아야 함
        orderItems: [{
            productId: document.getElementById('productId').value,
            productName: document.getElementById('productName').value,
            orderPrice: price,
            quantity: qty,
            itemTotalPrice: total
        }]
    };

    // fetch API를 사용해 POST 방식으로 '/api/order/create' 주소로 데이터 전송
    fetch('/api/order/create', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(orderData)
    }).then(response => response.text())
        .then(data => {
            alert(data); // "주문이 완료되었습니다" 알림창
            window.location.href = '/order/list'; // 성공 시 주문 내역 화면으로 이동
        })
        .catch(error => alert('주문 처리 중 에러가 발생했습니다.'));
}