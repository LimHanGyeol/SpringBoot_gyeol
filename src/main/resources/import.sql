INSERT INTO USER (ID, NICKNAME, PASSWORD, USER_ID) VALUES ('1', '임한결' , '1111', 'dlagksruf19@naver.com');
INSERT INTO USER (ID, NICKNAME, PASSWORD, USER_ID) VALUES ('2', '이자은' , '1111', 'jjaeun2@naver.com');

INSERT INTO QUESTION (ID, WRITER_ID, TITLE, CONTENTS, COUNT_OF_ANSWER, CREATE_DATE) VALUES (1, 1, '한결과 자은의 프로젝트를 위한 스프링부트 예제를 공부하는 중입니다.', '내용은 없습니다. 없어요', 0, CURRENT_TIMESTAMP());
INSERT INTO QUESTION (ID, WRITER_ID, TITLE, CONTENTS, COUNT_OF_ANSWER, CREATE_DATE) VALUES (2, 2, '두번째 테스트 게시물은 자은 계정으로 합니다.', '내용은 스프링부트 어려워요.', 0, CURRENT_TIMESTAMP());