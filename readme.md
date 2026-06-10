# JOEUN IT STORE 프로젝트 공통 정리

## 프로젝트 개요

### 프로젝트명

JOEUN IT STORE

### 프로젝트 주제

스마트폰, 노트북, 태블릿, 기타기기를 판매하는 IT 쇼핑몰 미니프로젝트

### 주요 기능

1. 회원관리
2. 상품관리
3. 장바구니
4. 주문관리
5. 게시판

### 사용 기술

* Java
* Spring Boot
* Thymeleaf
* MyBatis
* Oracle DB
* HTML / CSS / JavaScript
* DaisyUI 또는 Bootstrap
* GitHub

---

# 1. 공통 작업 규칙

## 1-1. 서버 / DB 관리 방식

이번 프로젝트에서는 공용 서버 DB를 하나로 공유하지 않는다.

각자 자기 PC에서 Spring Boot 서버를 실행하고, 각자 자기 Oracle DB를 사용한다.

대신 공통 SQL 파일을 공유해서 모든 팀원이 같은 테이블 구조를 사용한다.

```text
각자 로컬 서버 실행
각자 로컬 Oracle DB 사용
공통 SQL 파일 공유
각자 자기 DB에 SQL 실행
```

---

## 1-2. 공통 SQL 파일 관리 규칙

DB 테이블 구조는 공통 SQL 파일을 기준으로 한다.

테이블명, 컬럼명, PK, FK, 시퀀스명은 팀 전체가 동일하게 사용한다.

DB 구조가 변경되면 반드시 팀원에게 공유하고, 공통 SQL 파일도 같이 수정한다.

```text
테이블 구조 변경
→ 팀원에게 공유
→ 공통 SQL 파일 수정
→ GitHub에 push
→ 팀원들은 pull 후 자기 DB에 반영
```

---

## 1-3. GitHub 작업 규칙

작업 시작 전에는 항상 pull을 먼저 한다.

```bash
git pull origin master
```

작업 후에는 add, commit, push를 진행한다.

```bash
git add -A
git commit -m "작업 내용"
git push
```

각 담당자는 자기 도메인 폴더 안에서 작업하는 것을 원칙으로 한다.

공통 파일을 수정할 때는 팀원에게 먼저 공유한다.

공통 파일 예시:

```text
application-example.yml
공통 SQL 파일
공통 header / footer
common.css
build.gradle
```

---

## 1-4. application.yml 관리 규칙

실제 DB 아이디와 비밀번호가 들어가는 `application.yml` 또는 `application.yaml`은 GitHub에 올리지 않는다.

GitHub에는 예시 파일인 `application-example.yml`만 올린다.

각 팀원은 `application-example.yml`을 복사해서 본인 환경에 맞는 `application.yml`을 직접 만든다.

`.gitignore`에 아래 내용을 추가한다.

```gitignore
src/main/resources/application.yml
src/main/resources/application.yaml
uploads/
```

---

# 2. application-example.yml

아래 파일은 팀원들이 참고할 공통 예시 파일이다.

파일 위치:

```text
src/main/resources/application-example.yml
```

팀원들은 이 파일을 복사해서 아래 이름으로 변경한 뒤 사용한다.

```text
src/main/resources/application.yml
```

## application-example.yml 예시

```yaml
spring:
  application:
    name: joeun-it-store

  devtools:
    livereload:
      enabled: true
    restart:
      enabled: true

  thymeleaf:
    cache: false

  datasource:
    driver-class-name: oracle.jdbc.OracleDriver
    url: jdbc:oracle:thin:@//localhost:1521/XE
    username: 본인_DB_ID
    password: 본인_DB_PW

  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 50MB

  web:
    resources:
      static-locations:
        - classpath:/static/
        - file:uploads/

mybatis:
  mapper-locations: classpath:/mapper/**/*.xml
  configuration:
    map-underscore-to-camel-case: true

server:
  port: 8080

file:
  upload-dir: uploads/
```

---

## 2-1. datasource 설정

Oracle 접속 정보는 팀원마다 다를 수 있다.

본인 Oracle 환경에 맞게 아래 값을 수정한다.

```yaml
spring:
  datasource:
    url: jdbc:oracle:thin:@//localhost:1521/XE
    username: 본인_DB_ID
    password: 본인_DB_PW
```

Oracle URL 예시:

```text
일반 Oracle XE:
jdbc:oracle:thin:@//localhost:1521/XE

Oracle 21c XE 또는 Docker:
jdbc:oracle:thin:@//localhost:1521/XEPDB1

Docker 포트를 1522로 열었을 경우:
jdbc:oracle:thin:@//localhost:1522/XEPDB1
```

---

## 2-2. MyBatis 설정

```yaml
mybatis:
  mapper-locations: classpath:/mapper/**/*.xml
  configuration:
    map-underscore-to-camel-case: true
```

이 설정은 `resources/mapper` 아래의 모든 XML 파일을 읽는다는 뜻이다.

따라서 담당자별로 mapper 경로를 따로 바꾸지 않는다.

예시:

```text
resources/mapper/member/MemberMapper.xml
resources/mapper/product/ProductMapper.xml
resources/mapper/cart/CartMapper.xml
resources/mapper/order/OrderMapper.xml
resources/mapper/board/BoardMapper.xml
```

`cart` 담당자라고 해서 아래처럼 바꾸면 안 된다.

```yaml
mapper-locations: classpath:/mapper/cart/*.xml
```

이렇게 하면 다른 도메인의 Mapper XML이 읽히지 않을 수 있다.

---

## 2-3. 파일 업로드 설정

```yaml
spring:
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 50MB

file:
  upload-dir: uploads/
```

`max-file-size`는 파일 1개 최대 크기이다.

`max-request-size`는 한 번 요청 전체 크기이다.

`file.upload-dir`은 업로드 파일을 저장할 폴더 경로이다.

상품 이미지 업로드 기능에서 사용한다.

---

## 2-4. 서버 포트 설정

```yaml
server:
  port: 8080
```

Spring Boot 서버 실행 포트이다.

기본값이 8080이므로 생략해도 되지만, 팀 공통 기준을 맞추기 위해 작성한다.

만약 8080 포트 충돌이 나면 개인적으로 8081, 8082 등으로 변경해서 사용한다.

---

# 3. 공통 SQL 파일 관리

공통 SQL 파일은 프로젝트 루트의 `SQL` 폴더에서 관리한다.

예시 구조:

```text
SQL/
 ┣ 01.전체 테이블 Drop.sql
 ┣ 02.공통 테이블 작성.sql
 ┣ 회원 더미데이터.sql
 ┣ 카테고리 더미데이터.sql
 ┗ 상품 더미데이터.sql
```

---

## 3-1. SQL 실행 순서

각 팀원은 자기 Oracle DB에서 아래 순서대로 SQL 파일을 실행한다.

```text
1. 01.전체 테이블 Drop.sql
2. 02.공통 테이블 작성.sql
3. 회원 더미데이터.sql
4. 카테고리 더미데이터.sql
5. 상품 더미데이터.sql
```

카테고리 데이터는 상품 데이터보다 먼저 넣어야 한다.

이유는 `product.category_id`가 `category.category_id`를 참조하기 때문이다.

```text
category INSERT
→ product INSERT
```

---

## 3-2. DROP SQL 주의사항

처음 실행할 때는 테이블이나 시퀀스가 아직 없을 수 있다.

그 경우 아래와 같은 에러가 나올 수 있다.

```text
ORA-00942: table or view does not exist
ORA-02289: sequence does not exist
```

초기 실행 시에는 정상적으로 발생할 수 있는 에러이므로 무시해도 된다.

그다음 CREATE TABLE 파일부터 정상 실행되면 된다.

---

## 3-3. 공통 테이블 목록

이번 프로젝트의 공통 테이블은 아래와 같다.

```text
shop_member
category
product
cart_item
orders
order_item
board
board_comment
```

---

## 3-4. 테이블 역할

| 테이블명          | 역할                                     |
| ------------- | -------------------------------------- |
| shop_member   | 회원가입, 로그인, 회원정보, 권한, 회원등급              |
| category      | 상품 카테고리 관리                             |
| product       | 상품 등록, 수정, 삭제, 검색, 페이징, 이미지 업로드, 재고 관리 |
| cart_item     | 장바구니 담기, 조회, 수량 변경, 삭제, Ajax 처리        |
| orders        | 주문 생성, 주문 목록, 주문 상태 관리                 |
| order_item    | 주문 상세 상품 목록 저장                         |
| board         | 게시글 작성, 목록, 상세, 수정, 삭제, 조회수, 검색, 페이징   |
| board_comment | 댓글 작성, 조회, 수정, 삭제                      |

---

## 3-5. 주요 관계

```text
category 1 : N product

shop_member 1 : N cart_item
product 1 : N cart_item

shop_member 1 : N orders
orders 1 : N order_item
product 1 : N order_item

shop_member 1 : N board
product 1 : N board

board 1 : N board_comment
shop_member 1 : N board_comment
```

N:M 관계는 직접 연결하지 않고 중간 테이블로 풀어서 관리한다.

```text
회원 N:M 상품
→ shop_member 1:N cart_item N:1 product

주문 N:M 상품
→ orders 1:N order_item N:1 product
```

---

# 4. 도메인별 프로젝트 폴더 구조

Java 코드는 도메인별로 나누어 관리한다.

기본 패키지:

```text
org.store.joeunit
```

## 4-1. Java 폴더 구조

```text
src/main/java/org/store/joeunit
 ┣ member
 ┃ ┣ controller
 ┃ ┣ service
 ┃ ┣ mapper
 ┃ ┗ dto
 ┣ product
 ┃ ┣ controller
 ┃ ┣ service
 ┃ ┣ mapper
 ┃ ┗ dto
 ┣ cart
 ┃ ┣ controller
 ┃ ┣ service
 ┃ ┣ mapper
 ┃ ┗ dto
 ┣ order
 ┃ ┣ controller
 ┃ ┣ service
 ┃ ┣ mapper
 ┃ ┗ dto
 ┣ board
 ┃ ┣ controller
 ┃ ┣ service
 ┃ ┣ mapper
 ┃ ┗ dto
 ┣ common
 ┗ JoeunItStoreApplication.java
```

---

## 4-2. 각 폴더 역할

| 폴더         | 역할                    |
| ---------- | --------------------- |
| controller | 요청을 받는 Controller     |
| service    | 비즈니스 로직 처리            |
| mapper     | MyBatis Mapper 인터페이스  |
| dto        | 화면/요청/응답 데이터 전달 객체    |
| common     | 공통 코드, 공통 예외, 공통 유틸 등 |

---

## 4-3. MyBatis XML 폴더 구조

```text
src/main/resources/mapper
 ┣ member
 ┃ ┗ MemberMapper.xml
 ┣ product
 ┃ ┗ ProductMapper.xml
 ┣ cart
 ┃ ┗ CartMapper.xml
 ┣ order
 ┃ ┗ OrderMapper.xml
 ┗ board
   ┗ BoardMapper.xml
```

Mapper 인터페이스와 XML의 namespace는 반드시 일치해야 한다.

예시:

```java
package org.store.joeunit.cart.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartMapper {
}
```

```xml
<mapper namespace="org.store.joeunit.cart.mapper.CartMapper">

</mapper>
```

---

## 4-4. Thymeleaf templates 폴더 구조

```text
src/main/resources/templates
 ┣ member
 ┣ product
 ┣ cart
 ┣ order
 ┣ board
 ┗ common
```

각 도메인별 화면은 자기 폴더 안에 작성한다.

예시:

```text
templates/member/login.html
templates/member/join.html

templates/product/list.html
templates/product/detail.html
templates/product/form.html

templates/cart/cart.html

templates/order/list.html
templates/order/detail.html

templates/board/list.html
templates/board/detail.html
templates/board/write.html

templates/common/header.html
templates/common/footer.html
```

---

## 4-5. static 폴더 구조

```text
src/main/resources/static
 ┣ css
 ┣ js
 ┗ images
```

공통 CSS, JS, 이미지 파일을 관리한다.

예시:

```text
static/css/common.css
static/css/product.css
static/css/cart.css

static/js/main.js
static/js/cart.js

static/images/logo.png
```

---

# 5. 담당 도메인별 기능 정리

## 5-1. 회원관리

담당 테이블:

```text
shop_member
```

주요 기능:

```text
회원가입
로그인
로그아웃
마이페이지
회원정보 수정
회원 탈퇴
관리자 회원 목록 조회
회원등급 관리
```

---

## 5-2. 상품관리

담당 테이블:

```text
category
product
```

주요 기능:

```text
상품 등록
상품 목록 조회
상품 상세 조회
상품 수정
상품 삭제 또는 판매중지
상품 검색
상품 페이징
카테고리별 조회
상품 이미지 업로드
재고 관리
품절 처리
```

---

## 5-3. 장바구니

담당 테이블:

```text
cart_item
shop_member
product
```

주요 기능:

```text
장바구니 담기
내 장바구니 목록 조회
이미 담긴 상품이면 수량 증가
수량 변경 Ajax
상품 삭제 Ajax
상품별 합계 계산
장바구니 총금액 계산
재고 초과 방지
주문하기 연결
```

---

## 5-4. 주문관리

담당 테이블:

```text
orders
order_item
shop_member
product
cart_item
```

주요 기능:

```text
장바구니 기반 주문 생성
주문 목록 조회
주문 상세 조회
주문 취소
관리자 주문 목록 조회
주문 상태 변경
주문 시 재고 감소
주문 취소 시 재고 복구
주문 완료 후 장바구니 비우기
```

---

## 5-5. 게시판

담당 테이블:

```text
board
board_comment
shop_member
product
```

주요 기능:

```text
게시글 작성
게시글 목록 조회
게시글 상세 조회
게시글 수정
게시글 삭제
조회수 증가
게시글 검색
게시글 페이징
댓글 작성
댓글 수정
댓글 삭제
상품 문의글 작성
공지사항 작성
```

---

# 6. 브랜치 규칙

기준 브랜치:

```text
master
```

각 기능별 브랜치:

```text
feature/member
feature/product
feature/cart
feature/order
feature/board
```

브랜치 생성 예시:

```bash
git checkout master
git pull origin master
git checkout -b feature/cart
git push -u origin feature/cart
```

작업 후:

```bash
git add -A
git commit -m "장바구니 기능 구현"
git push
```

---

# 7. 주의사항

## 7-1. 빈 폴더는 GitHub에 올라가지 않음

Git은 빈 폴더를 추적하지 않는다.

빈 폴더를 유지하고 싶으면 `.gitkeep` 파일을 넣는다.

예시:

```text
member/controller/.gitkeep
member/service/.gitkeep
member/mapper/.gitkeep
member/dto/.gitkeep
```

---

## 7-2. package 경로 주의

Java 파일의 실제 위치와 package 경로는 일치해야 한다.

예시:

파일 위치:

```text
src/main/java/org/store/joeunit/cart/controller/CartController.java
```

package:

```java
package org.store.joeunit.cart.controller;
```

---

## 7-3. 공통 파일 수정 주의

아래 파일은 수정 전에 팀원에게 공유한다.

```text
build.gradle
application-example.yml
공통 SQL 파일
common.css
header.html
footer.html
```

---

# 8. 초기 세팅 순서

팀원이 처음 프로젝트를 받을 때 진행 순서:

```text
1. GitHub 초대 수락
2. 프로젝트 clone
3. application-example.yml 복사
4. application.yml 생성
5. 본인 Oracle DB 정보 입력
6. SQL 파일 순서대로 실행
7. 프로젝트 실행
```

명령어 예시:

```bash
git clone https://github.com/perudeim-hash/joeun-it-store.git
cd joeun-it-store
```

DB 세팅:

```text
1. SQL/01.전체 테이블 Drop.sql
2. SQL/02.공통 테이블 작성.sql
3. SQL/회원 더미데이터.sql
4. SQL/카테고리 더미데이터.sql
5. SQL/상품 더미데이터.sql
```

프로젝트 실행 후 접속:

```text
http://localhost:8080
```