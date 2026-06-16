-- 관리자 계정
INSERT INTO shop_member (
    member_id,
    login_id,
    password,
    nickname,
    email,
    phone,
    zipcode,
    address,
    detail_address,
    role,
    membership,
    total_purchase,
    created_at
) VALUES (
    seq_member.NEXTVAL,
    'admin',
    '1234',
    '관리자',
    'admin@joeunit.com',
    '010-0000-0000',
    '00000',
    '서울시 강남구',
    '관리자 주소',
    'ADMIN',
    'VIP',
    0,
    SYSDATE
);

-- 일반 테스트 회원
INSERT INTO shop_member (
    member_id,
    login_id,
    password,
    nickname,
    email,
    phone,
    zipcode,
    address,
    detail_address,
    role,
    membership,
    total_purchase,
    created_at
) VALUES (
    seq_member.NEXTVAL,
    'user1',
    '1234',
    '테스트회원1',
    'user1@test.com',
    '010-1111-1111',
    '12345',
    '서울시 강남구',
    '101호',
    'USER',
    'BRONZE',
    0,
    SYSDATE
);

INSERT INTO shop_member (
    member_id,
    login_id,
    password,
    nickname,
    email,
    phone,
    zipcode,
    address,
    detail_address,
    role,
    membership,
    total_purchase,
    created_at
) VALUES (
    seq_member.NEXTVAL,
    'user2',
    '1234',
    '테스트회원2',
    'user2@test.com',
    '010-2222-2222',
    '12345',
    '서울시 서초구',
    '202호',
    'USER',
    'SILVER',
    150000,
    SYSDATE
);

INSERT INTO shop_member (
    member_id,
    login_id,
    password,
    nickname,
    email,
    phone,
    zipcode,
    address,
    detail_address,
    role,
    membership,
    total_purchase,
    created_at
) VALUES (
    seq_member.NEXTVAL,
    'user3',
    '1234',
    '테스트회원3',
    'user3@test.com',
    '010-3333-3333',
    '12345',
    '서울시 송파구',
    '303호',
    'USER',
    'GOLD',
    500000,
    SYSDATE
);

INSERT INTO shop_member (
    member_id,
    login_id,
    password,
    nickname,
    email,
    phone,
    zipcode,
    address,
    detail_address,
    role,
    membership,
    total_purchase,
    created_at
) VALUES (
    seq_member.NEXTVAL,
    'user4',
    '1234',
    '테스트회원4',
    'user4@test.com',
    '010-4444-4444',
    '12345',
    '서울시 마포구',
    '404호',
    'USER',
    'VIP',
    1000000,
    SYSDATE
);

COMMIT;