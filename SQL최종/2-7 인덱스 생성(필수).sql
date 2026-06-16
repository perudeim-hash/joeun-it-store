CREATE INDEX idx_product_category
    ON product(category_id);

CREATE INDEX idx_cart_member
    ON cart_item(member_id);

CREATE INDEX idx_cart_product
    ON cart_item(product_id);

CREATE INDEX idx_orders_member
    ON orders(member_id);

CREATE INDEX idx_order_item_order
    ON order_item(order_id);

CREATE INDEX idx_order_item_product
    ON order_item(product_id);

CREATE INDEX idx_board_member
    ON board(member_id);

CREATE INDEX idx_board_product
    ON board(product_id);

CREATE INDEX idx_comment_board
    ON board_comment(board_id);

COMMIT;