CREATE TABLE shop_member (
    member_id       NUMBER PRIMARY KEY,
    login_id        VARCHAR2(50) UNIQUE NOT NULL,
    password        VARCHAR2(100) NOT NULL,
    nickname        VARCHAR2(50) NOT NULL,

    email           VARCHAR2(100) UNIQUE,
    phone           VARCHAR2(30),

    zipcode         VARCHAR2(10),
    address         VARCHAR2(200),
    detail_address  VARCHAR2(200),

    role            VARCHAR2(20) DEFAULT 'USER' NOT NULL,
    membership      VARCHAR2(20) DEFAULT 'BRONZE' NOT NULL,

    total_purchase  NUMBER DEFAULT 0 NOT NULL,
    discount_rate   NUMBER DEFAULT 1 NOT NULL,

    created_at      DATE DEFAULT SYSDATE,
    updated_at      DATE,

    CONSTRAINT ck_member_role
        CHECK (role IN ('USER', 'ADMIN')),

    CONSTRAINT ck_member_membership
        CHECK (membership IN ('BRONZE', 'SILVER', 'GOLD', 'VIP'))
);

CREATE TABLE category (
    category_id     NUMBER PRIMARY KEY,
    category_name   VARCHAR2(50) NOT NULL UNIQUE,
    display_order   NUMBER DEFAULT 0,
    created_at      DATE DEFAULT SYSDATE
);

CREATE TABLE product (
    product_id          NUMBER PRIMARY KEY,
    category_id         NUMBER NOT NULL,

    product_name        VARCHAR2(100) NOT NULL,
    brand               VARCHAR2(50),
    model_name          VARCHAR2(100),

    price               NUMBER NOT NULL,
    stock               NUMBER DEFAULT 0 NOT NULL,

    cpu                 VARCHAR2(100),
    ram                 VARCHAR2(50),
    storage_capacity    VARCHAR2(50),
    screen_size         VARCHAR2(50),
    os                  VARCHAR2(50),
    color               VARCHAR2(50),

    description         VARCHAR2(1000),

    image_name          VARCHAR2(255),
    image_path          VARCHAR2(500),

    status              VARCHAR2(20) DEFAULT 'SALE' NOT NULL,

    created_at          DATE DEFAULT SYSDATE,
    updated_at          DATE,

    CONSTRAINT fk_product_category
        FOREIGN KEY (category_id)
        REFERENCES category(category_id),

    CONSTRAINT ck_product_price
        CHECK (price >= 0),

    CONSTRAINT ck_product_stock
        CHECK (stock >= 0),

    CONSTRAINT ck_product_status
        CHECK (status IN ('SALE', 'SOLD_OUT', 'STOP'))
);

CREATE TABLE cart_item (
    cart_item_id    NUMBER PRIMARY KEY,

    member_id       NUMBER NOT NULL,
    product_id      NUMBER NOT NULL,

    quantity        NUMBER DEFAULT 1 NOT NULL,

    created_at      DATE DEFAULT SYSDATE,
    updated_at      DATE,

    CONSTRAINT fk_cart_member
        FOREIGN KEY (member_id)
        REFERENCES shop_member(member_id),

    CONSTRAINT fk_cart_product
        FOREIGN KEY (product_id)
        REFERENCES product(product_id),

    CONSTRAINT ck_cart_quantity
        CHECK (quantity >= 1),

    CONSTRAINT uk_cart_member_product
        UNIQUE (member_id, product_id)
);

CREATE TABLE orders (
    order_id            NUMBER PRIMARY KEY,

    member_id           NUMBER NOT NULL,

    total_price         NUMBER NOT NULL,
    discount_price      NUMBER DEFAULT 0 NOT NULL,
    final_price         NUMBER NOT NULL,

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
        REFERENCES orders(order_id),

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

CREATE TABLE board (
    board_id        NUMBER PRIMARY KEY,

    member_id       NUMBER NOT NULL,
    product_id      NUMBER,

    board_type      VARCHAR2(30) DEFAULT 'QNA' NOT NULL,

    title           VARCHAR2(200) NOT NULL,
    content         VARCHAR2(4000) NOT NULL,

    hit             NUMBER DEFAULT 0 NOT NULL,
    status          VARCHAR2(20) DEFAULT 'ACTIVE' NOT NULL,

    created_at      DATE DEFAULT SYSDATE,
    updated_at      DATE,

    CONSTRAINT fk_board_member
        FOREIGN KEY (member_id)
        REFERENCES shop_member(member_id),

    CONSTRAINT fk_board_product
        FOREIGN KEY (product_id)
        REFERENCES product(product_id),

    CONSTRAINT ck_board_type
        CHECK (board_type IN ('NOTICE', 'QNA', 'FREE')),

    CONSTRAINT ck_board_status
        CHECK (status IN ('ACTIVE', 'DELETED'))
);

CREATE TABLE board_comment (
    comment_id      NUMBER PRIMARY KEY,

    board_id        NUMBER NOT NULL,
    member_id       NUMBER NOT NULL,

    content         VARCHAR2(1000) NOT NULL,
    status          VARCHAR2(20) DEFAULT 'ACTIVE' NOT NULL,

    created_at      DATE DEFAULT SYSDATE,
    updated_at      DATE,

    CONSTRAINT fk_comment_board
        FOREIGN KEY (board_id)
        REFERENCES board(board_id),

    CONSTRAINT fk_comment_member
        FOREIGN KEY (member_id)
        REFERENCES shop_member(member_id),

    CONSTRAINT ck_comment_status
        CHECK (status IN ('ACTIVE', 'DELETED'))
);

CREATE SEQUENCE seq_member
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE seq_category
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE seq_product
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE seq_cart_item
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE seq_orders
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE seq_order_item
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE seq_board
START WITH 1
INCREMENT BY 1;

CREATE SEQUENCE seq_board_comment
START WITH 1
INCREMENT BY 1;