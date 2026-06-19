//스마트폰
BEGIN
FOR i IN 1..100 LOOP

        INSERT INTO PRODUCT (
            PRODUCT_ID,CATEGORY_ID,PRODUCT_NAME,BRAND,MODEL_NAME,
            PRICE,STOCK,CPU,RAM,STORAGE_CAPACITY,
            SCREEN_SIZE,OS,COLOR,DESCRIPTION,
            IMAGE_NAME,IMAGE_PATH,
            STATUS,SALES_COUNT,CREATED_AT
        )
        VALUES (
            SEQ_PRODUCT.NEXTVAL,
            1,
            CASE MOD(i,5)
                WHEN 0 THEN '갤럭시 S24 Ultra'
                WHEN 1 THEN '갤럭시 Z Fold6'
                WHEN 2 THEN '아이폰 15 Pro'
                WHEN 3 THEN 'Pixel 8 Pro'
                ELSE '샤오미 14 Ultra'
            END || ' ' || i,
            CASE MOD(i,5)
                WHEN 0 THEN 'Samsung'
                WHEN 1 THEN 'Samsung'
                WHEN 2 THEN 'Apple'
                WHEN 3 THEN 'Google'
                ELSE 'Xiaomi'
            END,
            'PHONE-'||LPAD(i,3,'0'),
            TRUNC(DBMS_RANDOM.VALUE(900000,2200000)),
            TRUNC(DBMS_RANDOM.VALUE(10,100)),
            'Snapdragon 8 Gen 3',
            CASE MOD(i,3)
                WHEN 0 THEN '8GB'
                WHEN 1 THEN '12GB'
                ELSE '16GB'
            END,
            CASE MOD(i,3)
                WHEN 0 THEN '128GB'
                WHEN 1 THEN '256GB'
                ELSE '512GB'
            END,
            '6.7인치',
            'Android',
            '블랙',
            '스마트폰 더미데이터',
            'phone.jpg',
            '/uploads/phone.jpg',
            'SALE',
            TRUNC(DBMS_RANDOM.VALUE(0,500)),
            SYSDATE
        );

END LOOP;
END;

//노트북

BEGIN
FOR i IN 1..100 LOOP

        INSERT INTO PRODUCT (
            PRODUCT_ID,CATEGORY_ID,PRODUCT_NAME,BRAND,MODEL_NAME,
            PRICE,STOCK,CPU,RAM,STORAGE_CAPACITY,
            SCREEN_SIZE,OS,COLOR,DESCRIPTION,
            IMAGE_NAME,IMAGE_PATH,
            STATUS,SALES_COUNT,CREATED_AT
        )
        VALUES (
            SEQ_PRODUCT.NEXTVAL,
            2,
            CASE MOD(i,5)
                WHEN 0 THEN 'LG그램 Pro'
                WHEN 1 THEN '맥북에어 M4'
                WHEN 2 THEN '갤럭시북5 Pro'
                WHEN 3 THEN 'ThinkPad X1'
                ELSE 'ASUS ZenBook'
            END || ' ' || i,
            CASE MOD(i,5)
                WHEN 0 THEN 'LG'
                WHEN 1 THEN 'Apple'
                WHEN 2 THEN 'Samsung'
                WHEN 3 THEN 'Lenovo'
                ELSE 'ASUS'
            END,
            'NOTE-'||LPAD(i,3,'0'),
            TRUNC(DBMS_RANDOM.VALUE(1000000,4000000)),
            TRUNC(DBMS_RANDOM.VALUE(5,50)),
            'Intel Ultra7',
            '16GB',
            '1TB SSD',
            '16인치',
            'Windows11',
            '그레이',
            '노트북 더미데이터',
            'notebook.jpg',
            '/uploads/notebook.jpg',
            'SALE',
            TRUNC(DBMS_RANDOM.VALUE(0,500)),
            SYSDATE
        );

END LOOP;
END;


//테블릿


BEGIN
FOR i IN 1..100 LOOP

        INSERT INTO PRODUCT (
            PRODUCT_ID,CATEGORY_ID,PRODUCT_NAME,BRAND,MODEL_NAME,
            PRICE,STOCK,CPU,RAM,STORAGE_CAPACITY,
            SCREEN_SIZE,OS,COLOR,DESCRIPTION,
            IMAGE_NAME,IMAGE_PATH,
            STATUS,SALES_COUNT,CREATED_AT
        )
        VALUES (
            SEQ_PRODUCT.NEXTVAL,
            3,
            CASE MOD(i,5)
                WHEN 0 THEN '아이패드 에어'
                WHEN 1 THEN '아이패드 프로'
                WHEN 2 THEN '갤럭시탭 S10'
                WHEN 3 THEN '레노버 Tab'
                ELSE '샤오미 Pad'
            END || ' ' || i,
            'TabletBrand',
            'TAB-'||LPAD(i,3,'0'),
            TRUNC(DBMS_RANDOM.VALUE(400000,1800000)),
            TRUNC(DBMS_RANDOM.VALUE(10,80)),
            'Tablet CPU',
            '8GB',
            '256GB',
            '11인치',
            'Android',
            '실버',
            '태블릿 더미데이터',
            'tablet.jpg',
            '/uploads/tablet.jpg',
            'SALE',
            TRUNC(DBMS_RANDOM.VALUE(0,500)),
            SYSDATE
        );

END LOOP;
END;


//기타주변기기

BEGIN
FOR i IN 1..100 LOOP

        INSERT INTO PRODUCT (
            PRODUCT_ID,
            CATEGORY_ID,
            PRODUCT_NAME,
            BRAND,
            MODEL_NAME,
            PRICE,
            STOCK,
            CPU,
            RAM,
            STORAGE_CAPACITY,
            SCREEN_SIZE,
            OS,
            COLOR,
            DESCRIPTION,
            IMAGE_NAME,
            IMAGE_PATH,
            STATUS,
            SALES_COUNT,
            CREATED_AT
        )
        VALUES (
            SEQ_PRODUCT.NEXTVAL,
            4,
            CASE MOD(i,10)
                WHEN 0 THEN '무선마우스'
                WHEN 1 THEN '기계식키보드'
                WHEN 2 THEN 'USB-C 허브'
                WHEN 3 THEN '노트북거치대'
                WHEN 4 THEN '무선이어폰'
                WHEN 5 THEN '웹캠'
                WHEN 6 THEN '모니터암'
                WHEN 7 THEN '블루투스스피커'
                WHEN 8 THEN '외장SSD'
                ELSE '게이밍헤드셋'
            END || ' ' || i,

            CASE MOD(i,5)
                WHEN 0 THEN 'Logitech'
                WHEN 1 THEN 'Keychron'
                WHEN 2 THEN 'Anker'
                WHEN 3 THEN 'Samsung'
                ELSE 'LG'
            END,

            'ACC-' || LPAD(i,3,'0'),

            TRUNC(DBMS_RANDOM.VALUE(10000,300000)),
            TRUNC(DBMS_RANDOM.VALUE(10,300)),

            NULL,
            NULL,
            NULL,
            NULL,
            NULL,

            CASE MOD(i,4)
                WHEN 0 THEN '블랙'
                WHEN 1 THEN '화이트'
                WHEN 2 THEN '그레이'
                ELSE '실버'
            END,

            '기타주변기기 더미데이터',

            'accessory.jpg',
            '/uploads/accessory.jpg',

            'SALE',

            TRUNC(DBMS_RANDOM.VALUE(0,500)),

            SYSDATE
        );

END LOOP;
END;
/

COMMIT;


