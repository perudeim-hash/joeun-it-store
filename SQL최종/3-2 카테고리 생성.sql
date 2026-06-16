INSERT INTO category (
    category_id,
    category_name,
    display_order,
    created_at
) VALUES (
    seq_category.NEXTVAL,
    '스마트폰',
    1,
    SYSDATE
);

INSERT INTO category (
    category_id,
    category_name,
    display_order,
    created_at
) VALUES (
    seq_category.NEXTVAL,
    '노트북',
    2,
    SYSDATE
);

INSERT INTO category (
    category_id,
    category_name,
    display_order,
    created_at
) VALUES (
    seq_category.NEXTVAL,
    '태블릿',
    3,
    SYSDATE
);

INSERT INTO category (
    category_id,
    category_name,
    display_order,
    created_at
) VALUES (
    seq_category.NEXTVAL,
    '기타주변기기',
    4,
    SYSDATE
);

COMMIT;