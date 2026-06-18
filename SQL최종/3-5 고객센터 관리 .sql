-- 1. 고객센터 전화번호 등을 관리할 테이블 생성 (Oracle 버전)
CREATE TABLE site_settings (
                               setting_id NUMBER PRIMARY KEY,
                               cs_phone VARCHAR2(50) NOT NULL,
                               cs_time VARCHAR2(100) NOT NULL,
                               cs_holiday VARCHAR2(100) NOT NULL
);

-- 2. 기본 데이터 1줄 미리 넣어두기
INSERT INTO site_settings (setting_id, cs_phone, cs_time, cs_holiday)
VALUES (1, '1588-0000', '평일 09:00 ~ 18:00 (점심시간 12:00 ~ 13:00)', '주말 및 공휴일 휴무');

-- 3. 저장 완료
COMMIT;

-- 1. 게시판 분류 단어가 넉넉하게 들어갈 수 있도록 길이 확장
ALTER TABLE board MODIFY board_type VARCHAR2(50);

-- 2. 1:1 문의나 FAQ는 '답변대기' 상태가 없을 수도 있으므로 빈 값(NULL)을 허용하도록 변경
ALTER TABLE board MODIFY reply_status VARCHAR2(20) NULL;

COMMIT;

-- 기존에 걸려있던 깐깐한 단어 제한 규칙(제약조건) 삭제
ALTER TABLE board DROP CONSTRAINT CK_BOARD_TYPE;

COMMIT;