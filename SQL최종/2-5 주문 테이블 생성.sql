CREATE TABLE orders (
    order_id            NUMBER PRIMARY KEY,

    member_id           NUMBER NOT NULL,

    total_price         NUMBER DEFAULT 0 NOT NULL,
    discount_price      NUMBER DEFAULT 0 NOT NULL,
    final_price         NUMBER DEFAULT 0 NOT NULL,

    member_membership   VARCHAR2(20),

    order_status        VARCHAR2(30) DEFAULT 'ORDER_COMPLETE' NOT NULL,

    receiver_name       VARCHAR2(50),
    receiver_phone      VARCHAR2(30),
    zipcode             VARCHAR2(10),
    address             VARCHAR2(200),
    detail_address      VARCHAR2(200),

    created_at          DATE DEFAULT SYSDATE,
    updated_at          DATE,

    CONSTRAINT fk_orders_member
        FOREIGN KEY (member_id)
        REFERENCES shop_member(member_id),

    CONSTRAINT ck_orders_status
        CHECK (order_status IN (
            'ORDER_COMPLETE',
            'DELIVERY_READY',
            'DELIVERING',
            'DELIVERY_DONE',
            'ORDER_CANCEL'
        )),

    CONSTRAINT ck_orders_price
        CHECK (
            total_price >= 0
            AND discount_price >= 0
            AND final_price >= 0
        ),

    CONSTRAINT ck_orders_membership
        CHECK (
            member_membership IN ('BRONZE', 'SILVER', 'GOLD', 'VIP')
            OR member_membership IS NULL
        )
);



CREATE TABLE order_item (
    order_item_id      NUMBER PRIMARY KEY,

    order_id           NUMBER NOT NULL,
    product_id         NUMBER NOT NULL,

    product_name       VARCHAR2(100) NOT NULL,
    order_price        NUMBER NOT NULL,
    quantity           NUMBER NOT NULL,
    item_total_price   NUMBER NOT NULL,

    created_at         DATE DEFAULT SYSDATE,

    CONSTRAINT fk_order_item_orders
        FOREIGN KEY (order_id)
        REFERENCES orders(order_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_order_item_product
        FOREIGN KEY (product_id)
        REFERENCES product(product_id),

    CONSTRAINT ck_order_item_quantity
        CHECK (quantity >= 1),

    CONSTRAINT ck_order_item_price
        CHECK (
            order_price >= 0
            AND item_total_price >= 0
        )
);