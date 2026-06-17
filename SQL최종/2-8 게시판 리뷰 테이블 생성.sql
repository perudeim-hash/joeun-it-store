CREATE TABLE product_review (
                                review_id   NUMBER PRIMARY KEY,
                                product_id  NUMBER NOT NULL,
                                member_id   NUMBER NOT NULL,
                                rating      NUMBER NOT NULL,
                                content     VARCHAR2(1000) NOT NULL,
                                created_at  DATE DEFAULT SYSDATE,
                                updated_at  DATE,

                                CONSTRAINT fk_product_review_product
                                    FOREIGN KEY (product_id)
                                        REFERENCES product(product_id)
                                        ON DELETE CASCADE,

                                CONSTRAINT fk_product_review_member
                                    FOREIGN KEY (member_id)
                                        REFERENCES shop_member(member_id)
                                        ON DELETE CASCADE,

                                CONSTRAINT ck_product_review_rating
                                    CHECK (rating BETWEEN 1 AND 5)
);

CREATE SEQUENCE seq_product_review;

COMMIT;