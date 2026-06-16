CREATE TABLE shop_member (
    member_id        NUMBER PRIMARY KEY,

    login_id         VARCHAR2(50) NOT NULL UNIQUE,
    password         VARCHAR2(100) NOT NULL,
    nickname         VARCHAR2(50) NOT NULL UNIQUE,

    email            VARCHAR2(100) UNIQUE,
    phone            VARCHAR2(30),

    zipcode          VARCHAR2(10),
    address          VARCHAR2(200),
    detail_address   VARCHAR2(200),

    role             VARCHAR2(20) DEFAULT 'USER' NOT NULL,
    membership       VARCHAR2(20) DEFAULT 'BRONZE' NOT NULL,
    total_purchase   NUMBER DEFAULT 0 NOT NULL,

    created_at       DATE DEFAULT SYSDATE,
    updated_at       DATE,

    CONSTRAINT ck_shop_member_role
        CHECK (role IN ('USER', 'ADMIN')),

    CONSTRAINT ck_shop_member_membership
        CHECK (membership IN ('BRONZE', 'SILVER', 'GOLD', 'VIP')),

    CONSTRAINT ck_shop_member_total_purchase
        CHECK (total_purchase >= 0)
);