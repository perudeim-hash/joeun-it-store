CREATE TABLE cart_item (
    cart_item_id    NUMBER PRIMARY KEY,

    member_id       NUMBER NOT NULL,
    product_id      NUMBER NOT NULL,

    quantity        NUMBER DEFAULT 1 NOT NULL,

    created_at      DATE DEFAULT SYSDATE,
    updated_at      DATE,

    CONSTRAINT fk_cart_member
        FOREIGN KEY (member_id)
        REFERENCES shop_member(member_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_cart_product
        FOREIGN KEY (product_id)
        REFERENCES product(product_id),

    CONSTRAINT ck_cart_quantity
        CHECK (quantity >= 1),

    CONSTRAINT uk_cart_member_product
        UNIQUE (member_id, product_id)
);