CREATE TABLE category (
    category_id     NUMBER PRIMARY KEY,
    category_name   VARCHAR2(50) NOT NULL UNIQUE,
    display_order   NUMBER DEFAULT 0 NOT NULL,
    created_at      DATE DEFAULT SYSDATE
);