/**
 * [주문 취소 처리 로직]
 */
function cancelOrder(orderId) {
    if(confirm('정말로 이 주문을 취소하시겠습니까?')) {
        fetch('/api/order/cancel/' + orderId, {
            method: 'POST' // 내용물(body)이 없으므로 headers 속성을 아예 제거해야 안전합니다.
        })
            .then(response => response.text())
            .then(data => {
                if(data === "success") {
                    alert("주문이 취소되었습니다.");
                    window.location.reload();
                } else {
                    // 에러가 나면 여기에 정확한 원인이 뜹니다.
                    alert("취소 실패 상세 원인: " + data);
                }
            })
            .catch(error => {
                console.error(error);
                alert('통신 에러가 발생했습니다. 서버가 꺼져있는지 확인하세요.');
            });
    }
}

/**
 * [주문 내역 완전 삭제 처리 로직]
 */
function deleteOrderHistory(orderId) {
    if(confirm('이 주문 내역을 목록에서 완전히 삭제하시겠습니까?\n(주의: 이 작업은 되돌릴 수 없습니다!)')) {
        fetch('/api/order/delete/' + orderId, {
            method: 'POST' // headers 속성 제거
        })
            .then(response => response.text())
            .then(data => {
                if(data === "success") {
                    alert("주문 내역이 삭제되었습니다.");
                    window.location.reload();
                } else {
                    alert("삭제 실패 상세 원인: " + data);
                }
            })
            .catch(error => {
                console.error(error);
                alert('통신 에러가 발생했습니다. 서버가 꺼져있는지 확인하세요.');
            });
    }
}