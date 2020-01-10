# kimchisoup

제출기간 임박하여 README.md를 실수로 지워서 웹상에서 테스트 해주시면 감사하겠습니다.

소스코드를 받은 후 스프링부트로 프로젝트 실행:  http://localhost:8080

1. 회원가입
   . id/emai/password 로 진행
   . id 중복시 회원가입 버튼 비활성화
   <img width="427" alt="Screen Shot 2020-01-10 at 8 38 18 PM" src="https://user-images.githubusercontent.com/59615218/72161206-03b59700-3403-11ea-92ab-d856a98c63f4.png">
   
   
   . pw 암호화
   . h2 사용 (http://localhost:8080/h2-console, jdbc-url :jdbc:h2:mem:testdb)
     - 회원정보 : member 테이블
     - 검색정보 : SEARCH_HISTORY 테이블
   .
 
2. 로그인
   . 회원가입한 id로 로그인
 
 
3. 검색
   . 검색창에 원하는 책 검색
   . 카카오 책검색 api 사용
   . 카카오 api 서버장애시 네이버 책 검색 api 호출
   . 검색한 키워드 저장 : @Async("threadPoolTaskExecutor") 어노테이션 사용으로 대용량 트래픽 대비 --> 페이지 이동 때는 저장하지 않음
   . 검색로직은 캐시처리하여 대용량 트래픽에 대비 @Cacheable("bookSearch") --> CacheConfig.java 로 스레드 관리
     - 한시간마다 캐시 삭제.
     - SpringAsyncConfig.java 에서 캐시 만료 설정
   . 페이징 처리
   
   <img width="1606" alt="Screen Shot 2020-01-10 at 10 36 02 PM" src="https://user-images.githubusercontent.com/59615218/72161024-ac172b80-3402-11ea-98ca-32737459c812.png">
     
4. 차트
   . 나의검색기록 : 최신순으로 보여줌, 검색어 클릭 시 검색결과물로 이동 
   <img width="1064" alt="Screen Shot 2020-01-10 at 11 08 48 PM" src="https://user-images.githubusercontent.com/59615218/72161095-d1a43500-3402-11ea-8ee2-b67b10e5f437.png">
   
   . 인기검색어 : SEARCH_HISTORY 테이블에서 keyword로 group by 하여 상위 10개 항목 조회
   
<img width="1259" alt="Screen Shot 2020-01-10 at 11 10 45 PM" src="https://user-images.githubusercontent.com/59615218/72161151-eb457c80-3402-11ea-8ef6-f4f7994d1e2a.png">


5. 로그아웃
   . 스프링부트 시큐리티 기본기능으로 로그아웃 진행
   <img width="1057" alt="Screen Shot 2020-01-10 at 10 54 48 PM" src="https://user-images.githubusercontent.com/59615218/72161062-bcc7a180-3402-11ea-8f2d-856e7176ce84.png">

6. 기타기능
   . 권한에 따른 동적 페이지 이동
     case1) 로그인 하지 않은 유저가 대시보드나 기타화면에 url로 접근 시도 시 로그인 페이지 이동
     case2) 이미 로그인한 사용자가 회원가입, 로그인 화면에 url로 접근 시도 시 대시보드로 이동
     
   . 에러페이지 매핑
      404, 401, 505 에러 시 해당 에러페이지 이동
      
   . 오픈소스 롬복 사용 : 이클립스의 경우, 설치가 되어있어야 프로젝트 동작
