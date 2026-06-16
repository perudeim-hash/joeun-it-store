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
    sales_count         NUMBER DEFAULT 0 NOT NULL,

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
        CHECK (status IN ('SALE', 'SOLD_OUT', 'STOP')),

    CONSTRAINT ck_product_sales_count
        CHECK (sales_count >= 0)
);