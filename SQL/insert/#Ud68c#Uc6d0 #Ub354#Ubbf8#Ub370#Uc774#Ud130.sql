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
    created_at
) VALUES (
    seq_member.NEXTVAL,
    'admin',
    '1234',
    '관리자',
    'admin@test.com',
    '010-1111-1111',
    '04524',
    '서울특별시 중구 세종대로',
    '관리자 사무실',
    'ADMIN',
    'VIP',
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
    created_at
) VALUES (
    seq_member.NEXTVAL,
    'user1',
    '1234',
    '테스트회원',
    'user1@test.com',
    '010-2222-2222',
    '06236',
    '서울특별시 강남구 테헤란로',
    '101호',
    'USER',
    'BRONZE',
    SYSDATE
);