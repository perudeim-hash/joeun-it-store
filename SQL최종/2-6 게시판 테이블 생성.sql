CREATE TABLE board (
    board_id        NUMBER PRIMARY KEY,

    member_id       NUMBER NOT NULL,
    product_id      NUMBER,

    board_type      VARCHAR2(30) DEFAULT 'QNA' NOT NULL,

    title           VARCHAR2(200) NOT NULL,
    content         VARCHAR2(4000) NOT NULL,

    hit             NUMBER DEFAULT 0 NOT NULL,

    created_at      DATE DEFAULT SYSDATE,
    updated_at      DATE,

    CONSTRAINT fk_board_member
        FOREIGN KEY (member_id)
        REFERENCES shop_member(member_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_board_product
        FOREIGN KEY (product_id)
        REFERENCES product(product_id),

    CONSTRAINT ck_board_type
        CHECK (board_type IN ('NOTICE', 'QNA', 'REVIEW')),

    CONSTRAINT ck_board_hit
        CHECK (hit >= 0)
);



CREATE TABLE board_comment (
    comment_id      NUMBER PRIMARY KEY,

    board_id        NUMBER NOT NULL,
    member_id       NUMBER NOT NULL,

    content         VARCHAR2(1000) NOT NULL,

    created_at      DATE DEFAULT SYSDATE,
    updated_at      DATE,

    CONSTRAINT fk_comment_board
        FOREIGN KEY (board_id)
        REFERENCES board(board_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_comment_member
        FOREIGN KEY (member_id)
        REFERENCES shop_member(member_id)
        ON DELETE CASCADE
);