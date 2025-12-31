# Entity 생성 규칙
1. @Entity 어노테이션 선언
2. @Table(name = 해당클래스.TABLE_NAME)
3. @Getter, @Setter 선언
4. @NoArgsConstructor, @AllArgsConstructor 선언
5. public static final String TABLE_NAME = "{테이블명}"; 선언
6. 모든 컬럼에 @Column 선언
7. @JoinColumn 어노테이션에 name, referencedColumnName, nullable 선언
8. 연관관계 어노테이션에 LAZY 페치타입 선언

# Controller 생성 규칙
1. interface 클래스로 생성
2. Endpoint 지정과 Swagger 설정
3. 200, 201, 204, 400, 401, 403, 404, 500 상태에 대한 처리 필요
4. Swagger 문서화의 경우 한국어로 작성할것
5. Pagenation 필요한 경우, @PageDefault Pageable pageable 로 파라미터 전달하여 사용할것
6. 파라미터는 메소드명 선언과 별도의 줄로 내려서 작성할 것.

# ControllerImpl 생성 규칙
1. service 메소드 실행 후 얻어지는 결과를 result 혹은 results 변수로 받기
2. result(results)를 매퍼를 통해 Response 객체로 변환한 결과를 response 변수로 받기
3. 적절한 ResponseEntity 메소드를 사용해 응답처리하기
4. service 레이어에서 User currentUser가 필요한 경우 CurrentUserProvider.resolveUser() 메소드 활용하여 service 메소드에 전달할것.
5. spring validation이 적용되도록 작성할것.
