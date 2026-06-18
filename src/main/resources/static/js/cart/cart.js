document.addEventListener("DOMContentLoaded", () => {
    const minusButtons = document.querySelectorAll(".quantity-minus");
    const plusButtons = document.querySelectorAll(".quantity-plus");

    minusButtons.forEach((button) => {
        button.addEventListener("click", () => {
            const cartItemId = button.dataset.cartItemId;
            const quantityText = document.getElementById(`quantity-${cartItemId}`);

            const currentQuantity = Number(quantityText.textContent);
            const nextQuantity = currentQuantity - 1;

            if (nextQuantity < 1) {
                alert("수량은 1개 이상이어야 합니다.");
                return;
            }

            updateQuantity(cartItemId, nextQuantity);
        });
    });

    plusButtons.forEach((button) => {
        button.addEventListener("click", () => {
            const cartItemId = button.dataset.cartItemId;
            const quantityBox = document.querySelector(`.quantity-box[data-cart-item-id="${cartItemId}"]`);
            const quantityText = document.getElementById(`quantity-${cartItemId}`);

            const currentQuantity = Number(quantityText.textContent);
            const remainingStock = Number(quantityBox.dataset.stock);

            if (remainingStock <= 0) {
                alert("남은 재고가 없습니다.");
                return;
            }

            const nextQuantity = currentQuantity + 1;

            updateQuantity(cartItemId, nextQuantity);
        });
    });
});

function updateQuantity(cartItemId, quantity) {
    const quantityBox = document.querySelector(`.quantity-box[data-cart-item-id="${cartItemId}"]`);
    const quantityText = document.getElementById(`quantity-${cartItemId}`);

    const price = Number(quantityBox.dataset.price);
    const oldQuantity = Number(quantityText.textContent);
    const oldRemainingStock = Number(quantityBox.dataset.stock);

    fetch("/cart/update-ajax", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: new URLSearchParams({
            cartItemId: cartItemId,
            quantity: quantity
        })
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error("수량 변경 요청 실패");
            }

            return response.json();
        })
        .then((data) => {
            if (!data.success) {
                alert(data.message || "수량 변경에 실패했습니다.");
                return;
            }

            const itemTotalElement = document.getElementById(`item-total-${cartItemId}`);
            const cartTotalElement = document.getElementById("cart-total-price");
            const cartFinalElement = document.getElementById("cart-final-price");
            const stockText = document.getElementById(`stock-${cartItemId}`);

            const newQuantity = Number(data.quantity);
            const diffQuantity = newQuantity - oldQuantity;

            const newRemainingStock = oldRemainingStock - diffQuantity;

            quantityText.textContent = newQuantity;
            quantityBox.dataset.stock = newRemainingStock;

            if (stockText) {
                stockText.textContent = newRemainingStock;
            }

            const itemTotalPrice = price * newQuantity;

            itemTotalElement.textContent = formatPrice(itemTotalPrice);
            cartTotalElement.textContent = formatPrice(data.cartTotalPrice);
            cartFinalElement.textContent = formatPrice(data.cartTotalPrice);
        })
        .catch((error) => {
            console.error(error);
            alert("수량 변경 중 오류가 발생했습니다.");
        });
}

function formatPrice(price) {
    return Number(price).toLocaleString("ko-KR");
}