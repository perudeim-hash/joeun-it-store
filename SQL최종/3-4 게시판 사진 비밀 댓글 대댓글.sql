-- 1. 게시판 사진 3장 지원을 위한 컬럼 추가
ALTER TABLE BOARD ADD image_name_2 VARCHAR2(255);
ALTER TABLE BOARD ADD image_path_2 VARCHAR2(255);
ALTER TABLE BOARD ADD image_name_3 VARCHAR2(255);
ALTER TABLE BOARD ADD image_path_3 VARCHAR2(255);

-- 2. 비밀 댓글 지원을 위한 컬럼 추가 (기본값 N: 일반댓글)
ALTER TABLE board_comment ADD is_secret CHAR(1) DEFAULT 'N';

-- 3. 대댓글 지원을 위한 부모 댓글 ID 컬럼 및 외래키 설정
ALTER TABLE board_comment ADD parent_id NUMBER;
ALTER TABLE board_comment ADD CONSTRAINT fk_comment_parent FOREIGN KEY (parent_id) REFERENCES board_comment(comment_id) ON DELETE CASCADE;

COMMIT;

