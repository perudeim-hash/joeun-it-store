/**
 * [주문 취소 처리 로직]
 * 리스트에서 '주문취소' 버튼을 클릭하면 해당 주문의 PK(orderId)를 받아와 서버에 취소를 요청합니다.
 */
function cancelOrder(orderId) {
    if(confirm('정말로 이 주문을 취소하시겠습니까?')) {
        fetch('/api/order/cancel/' + orderId, {
            method: 'POST'
        }).then(response => response.text())
            .then(data => {
                alert(data);
                window.location.reload(); // 성공 시 페이지 새로고침
            })
            .catch(error => alert('취소 처리 중 에러가 발생했습니다.'));
    }
}

/**
 * [주문 내역 완전 삭제 처리 로직]
 * 취소된 주문의 '내역삭제' 버튼 클릭 시, DB에서 완전히 데이터를 날려버립니다.
 */
function deleteOrderHistory(orderId) {
    if(confirm('이 주문 내역을 목록에서 완전히 삭제하시겠습니까?\n(주의: 이 작업은 되돌릴 수 없습니다!)')) {
        fetch('/api/order/delete/' + orderId, {
            method: 'POST'
        }).then(response => response.text())
            .then(data => {
                alert(data);
                window.location.reload(); // 삭제 성공 시 화면 새로고침하여 목록에서 지움
            })
            .catch(error => alert('삭제 처리 중 에러가 발생했습니다.'));
    }
}