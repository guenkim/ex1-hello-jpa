package jpaBasic.query;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;

import javax.persistence.*;
import java.util.List;

/**
 프로젝션
 • SELECT 절에 조회할 대상을 지정하는 것
 • 프로젝션 대상: 엔티티, 임베디드 타입, 스칼라 타입(숫자, 문자등 기본 데이터 타
 입)
 • SELECT m FROM Member m -> 엔티티 프로젝션
 • SELECT m.team FROM Member m -> 엔티티 프로젝션
 • SELECT m.address FROM Member m -> 임베디드 타입 프로젝션
 • SELECT m.username, m.age FROM Member m -> 스칼라 타입 프로젝션
 • DISTINCT로 중복 제거

 페이징 API
     • JPA는 페이징을 다음 두 API로 추상화
     • setFirstResult(int startPosition) : 조회 시작 위치  (0부터 시작)
     • setMaxResults(int maxResult) : 조회할 데이터 수

 조인
 • 내부 조인:
        SELECT m FROM Member m [INNER] JOIN m.team t
 • 외부 조인:
        SELECT m FROM Member m LEFT [OUTER] JOIN m.team t
 • 세타 조인:
        select count(m) from Member m, Team t where m.username = t.name

 조인 - ON 절
    • ON절을 활용한 조인(JPA 2.1부터 지원)
     • 1. 조인 대상 필터링
     • 2. 연관관계 없는 엔티티 외부 조인(하이버네이트 5.1부터)

 조인 대상 필터링
    • 예) 회원과 팀을 조인하면서, 팀 이름이 A인 팀만 조인
     JPQL:
     SELECT m, t FROM Member m LEFT JOIN m.team t on t.name = 'A'
     SQL:
     SELECT m.*, t.* FROM
     Member m LEFT JOIN Team t ON m.TEAM_ID=t.id and t.name='A'

 연관관계 없는 엔티티 외부 조인
    • 예) 회원의 이름과 팀의 이름이 같은 대상 외부 조인
     JPQL:
     SELECT m, t FROM
     Member m LEFT JOIN Team t on m.username = t.name
     SQL:
     SELECT m.*, t.* FROM
     Member m LEFT JOIN Team t ON m.username = t.name

 서브 쿼리
    • 나이가 평균보다 많은 회원
        select m from Member m
        where m.age > (select avg(m2.age) from Member m2)
    • 한 건이라도 주문한 고객
         select m from Member m
         where (select count(o) from Order o where m = o.member) > 0
 서브 쿼리 지원 함수
     • [NOT] EXISTS (subquery): 서브쿼리에 결과가 존재하면 참
         • {ALL | ANY | SOME} (subquery)
         • ALL 모두 만족하면 참
         • ANY, SOME: 같은 의미, 조건을 하나라도 만족하면 참
     • [NOT] IN (subquery): 서브쿼리의 결과 중 하나라도 같은 것이 있으면 참

 JPA 서브 쿼리 한계
     • JPA는 WHERE, HAVING 절에서만 서브 쿼리 사용 가능
     • SELECT 절도 가능(하이버네이트에서 지원)
     • FROM 절의 서브 쿼리는 현재 JPQL에서 불가능 (하이버네이트6 부터는 FROM 절의 서브쿼리를 지원합니다)
     • 조인으로 풀 수 있으면 풀어서 해결

 JPQL 타입 표현
     • 문자: ‘HELLO’, ‘She’’s’
     • 숫자: 10L(Long), 10D(Double), 10F(Float)
     • Boolean: TRUE, FALSE
     • ENUM: jpabook.MemberType.Admin (패키지명 포함)
     • 엔티티 타입: TYPE(m) = Member (상속 관계에서 사용)

 JPQL 기타
     • SQL과 문법이 같은 식
     • EXISTS, IN
     • AND, OR, NOT
     • =, >, >=, <, <=, <>
     • BETWEEN, LIKE, IS NULL


-  조건식 - CASE 식
 기본 CASE 식
     select
         case when m.age <= 10 then '학생요금'
         when m.age >= 60 then '경로요금'
         else '일반요금'
         end
     from Member m
 단순 CASE 식
     select
         case t.name
         when '팀A' then '인센티브110%'
         when '팀B' then '인센티브120%'
         else '인센티브105%'
         end
     from Team t

 조건식 - CASE 식
 • COALESCE: 하나씩 조회해서 null이 아니면 반환
 • NULLIF: 두 값이 같으면 null 반환, 다르면 첫번째 값 반환
    select coalesce(m.username,'이름 없는 회원') from Member m
    select NULLIF(m.username, '관리자') from Member m


 JPQL 기본 함수
     • CONCAT
     • SUBSTRING
     • TRIM
     • LOWER, UPPER
     • LENGTH
     • LOCATE
     • ABS, SQRT, MOD
     • SIZE, INDEX(JPA 용도)

 사용자 정의 함수 호출
     • 하이버네이트는 사용전 방언에 추가해야 한다.
     • 사용하는 DB 방언을 상속받고, 사용자 정의 함수를 등록한다.
     select function('group_concat', i.name) from Item i
*/

public class JpaMain2 {
    public static void main(String[] args) {

        // persistence.xml 설정을 읽어 EntityManagerFactory 생성 
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); //persistence.xml의 unit-name
        //EntityManagerFactory로 부터 EntityManager 생성
        EntityManager em = emf.createEntityManager();
        
        //transaction 인스턴스 생성
        //jpa는 트랜잭션을 필수로 설정 해 주어야 동작함
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            /**
             프로젝션
             • SELECT 절에 조회할 대상을 지정하는 것
             • 프로젝션 대상: 엔티티, 임베디드 타입, 스칼라 타입(숫자, 문자등 기본 데이터 타
             입)
             • SELECT m FROM Member m -> 엔티티 프로젝션
             • SELECT m.team FROM Member m -> 엔티티 프로젝션
             • SELECT m.address FROM Member m -> 임베디드 타입 프로젝션
             • SELECT m.username, m.age FROM Member m -> 스칼라 타입 프로젝션
             • DISTINCT로 중복 제거
             **/

            MemberQ memberQ = new MemberQ();
            memberQ.setName("memberq");
            memberQ.setAge(13);
            em.persist(memberQ);


            //Entity 객체 반환 , 영속성 컨텍스트에서 관리
            List<MemberQ> memberQList = em.createQuery("select m from MemberQ m" , MemberQ.class)
                            .getResultList();
            MemberQ memberQ1 = memberQList.get(0);
            memberQ1.setAge(100);

            //Entity 객체 반환 , 영속성 컨텍스트에서 관리
            List<TeamQ> teamQList = em.createQuery("select m.team from MemberQ m" , TeamQ.class)
                    .getResultList();

            // 임베디드 타입 프로젝션
            List<AddressQ> addressQList = em.createQuery("select o.addressQ from OrderQ o" , AddressQ.class)
                    .getResultList();
            //AddressQ addressQ = addressQList.get(0);

            // 스칼라 타입 프로젝션
            // 타입을 명기 못할 시 object로 반환
            List<Object[]> resultList = em.createQuery("select distinct m.name, m.age from MemberQ m")
                    .getResultList();
            Object[] result = resultList.get(0);
            System.out.println("name : " + result[0]);
            System.out.println("age : " + result[1]);

            /**************************************************
              new 명령어로 조회
                • 단순 값을 DTO로 바로 조회
                    SELECT new jpabook.jpql.UserDTO(m.username, m.age) FROM Member m
                • 패키지 명을 포함한 전체 클래스 명 입력
                • 순서와 타입이 일치하는 생성자 필요
             ***************************************************/
            List<MemberDTO> resultList1 = em.createQuery("select new jpaBasic.query.MemberDTO( m.name, m.age) from MemberQ m", MemberDTO.class)
                    .getResultList();
            MemberDTO memberDTO = resultList1.get(0);
            System.out.println("name : " + memberDTO.getName());
            System.out.println("age : " + memberDTO.getAge());

            /***************************************************
            페이징 API
                 • JPA는 페이징을 다음 두 API로 추상화
                 • setFirstResult(int startPosition) : 조회 시작 위치  (0부터 시작)
                 • setMaxResults(int maxResult) : 조회할 데이터 수
             ****************************************************/
            for(int i=0 ; i < 100 ; i++){
                MemberQ memberQ2= new MemberQ();
                memberQ2.setName("memberQ2");
                memberQ2.setAge(i);
                em.persist(memberQ2);
            }


            em.flush();
            em.clear();

            /***************************************************
             페이징 API
             • JPA는 페이징을 다음 두 API로 추상화
             • setFirstResult(int startPosition) : 조회 시작 위치  (0부터 시작)
             • setMaxResults(int maxResult) : 조회할 데이터 수
             ****************************************************/
            List<MemberQ> memberQList1 = em.createQuery("select m from MemberQ m order by m.age desc")
                            .setFirstResult(0)
                            .setMaxResults(10)
                            .getResultList();

            System.out.println("result size =" + memberQList1.size());
            for(MemberQ m : memberQList1){
                System.out.println("memberq=" + m);
            }

            /***************************************************
            조인
             • 내부 조인:
                        SELECT m FROM Member m [INNER] JOIN m.team t
             • 외부 조인:
                        SELECT m FROM Member m LEFT [OUTER] JOIN m.team t
             • 세타 조인:
                        select count(m) from Member m, Team t where m.username = t.name

             조인 - ON 절
             • ON절을 활용한 조인(JPA 2.1부터 지원)
                 • 1. 조인 대상 필터링
                 • 2. 연관관계 없는 엔티티 외부 조인(하이버네이트 5.1부터)

             조인 대상 필터링
             • 예) 회원과 팀을 조인하면서, 팀 이름이 A인 팀만 조인
             JPQL:
                SELECT m, t FROM Member m LEFT JOIN m.team t on t.name = 'A'
             SQL:
                    SELECT m.*, t.* FROM
                    Member m LEFT JOIN Team t ON m.TEAM_ID=t.id and t.name='A'

             연관관계 없는 엔티티 외부 조인
             • 예) 회원의 이름과 팀의 이름이 같은 대상 외부 조인
                 JPQL:
                     SELECT m, t FROM
                     Member m LEFT JOIN Team t on m.username = t.name
                 SQL:
                     SELECT m.*, t.* FROM
                     Member m LEFT JOIN Team t ON m.username = t.name
             ***************************************************/
            TeamQ team = new TeamQ();
            team.setName("teamA");
            em.persist(team);

            MemberQ memberQ4= new MemberQ();
            memberQ4.setName("FUCKER");
            memberQ4.setAge(99);
            memberQ4.setTeam(team);
            memberQ4.setMemberType(MemberType.ADMIN);
            em.persist(memberQ4);

            //내부 조인: SELECT m FROM Member m [INNER] JOIN m.team t
            //String query = "select m from MemberQ m INNER JOIN m.team t";


            //• 외부 조인:
            //SELECT m FROM Member m LEFT [OUTER] JOIN m.team t
            //String query = "select m from MemberQ m LEFT OUTER JOIN m.team t";

            //• 세타 조인:
            // select count(m) from Member m, Team t where m.username = t.name
            //String query = "select m from MemberQ m , TeamQ t where m.name=t.name";

            //조인 대상 필터링
            // • 예) 회원과 팀을 조인하면서, 팀 이름이 A인 팀만 조인
            //JPQL:
            //SELECT m, t FROM Member m LEFT JOIN m.team t on t.name = 'A'
            //String query = "select m from MemberQ m LEFT OUTER JOIN m.team t on t.name= :teamname";

            //연관관계 없는 엔티티 외부 조인
            // • 예) 회원의 이름과 팀의 이름이 같은 대상 외부 조인
            //JPQL:
            //SELECT m, t FROM Member m LEFT JOIN Team t on m.username = t.name

            //String query = "select m from MemberQ m LEFT OUTER JOIN TeamQ t on m.name=t.name ";

            //List<MemberQ> memberQList2 = em.createQuery(query, MemberQ.class)
            //.getResultList();


            /***************************************************
             서브 쿼리
                 • 나이가 평균보다 많은 회원
                     select m from Member m
                     where m.age > (select avg(m2.age) from Member m2)
                 • 한 건이라도 주문한 고객
                     select m from Member m
                     where (select count(o) from Order o where m = o.member) > 0
             서브 쿼리 지원 함수
                 • [NOT] EXISTS (subquery): 서브쿼리에 결과가 존재하면 참
                     • {ALL | ANY | SOME} (subquery)
                     • ALL 모두 만족하면 참
                     • ANY, SOME: 같은 의미, 조건을 하나라도 만족하면 참
                 • [NOT] IN (subquery): 서브쿼리의 결과 중 하나라도 같은 것이 있으면 참

             JPA 서브 쿼리 한계
                 • JPA는 WHERE, HAVING 절에서만 서브 쿼리 사용 가능
                 • SELECT 절도 가능(하이버네이트에서 지원)
                 • FROM 절의 서브 쿼리는 현재 JPQL에서 불가능 (하이버네이트6 부터는 FROM 절의 서브쿼리를 지원합니다)
                 • 조인으로 풀 수 있으면 풀어서 해결
             **************************************************/

            /***************************************************
             JPQL 타입 표현
                 • 문자: ‘HELLO’, ‘She’’s’
                 • 숫자: 10L(Long), 10D(Double), 10F(Float)
                 • Boolean: TRUE, FALSE
                 • ENUM: jpabook.MemberType.Admin (패키지명 포함)
                 • 엔티티 타입: TYPE(m) = Member (상속 관계에서 사용)

             JPQL 기타
             • SQL과 문법이 같은 식
                 • EXISTS, IN
                 • AND, OR, NOT
                 • =, >, >=, <, <=, <>
                 • BETWEEN, LIKE, IS NULL
             **************************************************/

            //ENUM TYPE 일 경우 패키지명 모드 열거
            String query = "select m.name, 'HELLO' , TRUE from MemberQ m where m.memberType = jpaBasic.query.MemberType.ADMIN";
            List<Object[]> qList = em.createQuery(query)
                    .getResultList();

            //위 코드를 좀 더 간단하게 변경 , setParameter로 변경
            String query2 = "select m.name, 'HELLO' , TRUE from MemberQ m where m.memberType = :TYPE";
            List<Object[]> qList2 = em.createQuery(query2)
                    .setParameter("TYPE" , MemberType.ADMIN)
                    .getResultList();


            /****************************************************************
             -  조건식 - CASE 식
             기본 CASE 식
                 select
                 case when m.age <= 10 then '학생요금'
                 when m.age >= 60 then '경로요금'
                 else '일반요금'
                 end
                 from Member m
             단순 CASE 식
                 select
                 case t.name
                 when '팀A' then '인센티브110%'
                 when '팀B' then '인센티브120%'
                 else '인센티브105%'
                 end
                 from Team t

             조건식 - CASE 식
                 • COALESCE: 하나씩 조회해서 null이 아니면 반환
                 • NULLIF: 두 값이 같으면 null 반환, 다르면 첫번째 값 반환
                 select coalesce(m.username,'이름 없는 회원') from Member m
                 select NULLIF(m.username, '관리자') from Member m

             ***************************************************************/
            String query3 = "select "  +
                            " case when m.age <= 10 then '학생요금' " +
                            "    when m.age >= 60 then '경로우대요금' " +
                            " else '일반요금' " +
                            " end" +
                            " from MemberQ m";
            List<String> result2 = em.createQuery(query3 , String.class)
                    .getResultList();
                      
            for(String s : result2){
                System.out.println("pay =" + s);
            }


            /****************************************************************
            JPQL 기본 함수
                • CONCAT
                • SUBSTRING
                • TRIM
                • LOWER, UPPER
                • LENGTH
                • LOCATE
                • ABS, SQRT, MOD
                • SIZE, INDEX(JPA 용도)

             사용자 정의 함수 호출
                • 하이버네이트는 사용전 방언에 추가해야 한다.
                • 사용하는 DB 방언을 상속받고, 사용자 정의 함수를 등록한다.
                select function('group_concat', i.name) from Item i
             ****************************************************************/
            //concat
            String query4 = "select concat('a' , 'b') from MemberQ m";

            //substring
            query4 = "select substring(m.name , 0, 2) from MemberQ m";

            //trim
            query4 = "select trim(m.name) from MemberQ m";

            // • LOWER, UPPER
            query4 = "select UPPER(m.name) from MemberQ m";

            //LENGTH
            query4 = "select LENGTH(m.name) from MemberQ m";
            //LOCATE
            query4 = "select LOCATE('de' , 'abcde') from MemberQ m";
            //ABS, SQRT, MOD
            //query4 = "select MOD(m.age) from MemberQ m";
            //SIZE, INDEX(JPA 용도)
            //query4 = "select SIZE(t.members) from TeamQ t";


            List<Integer> result3 = em.createQuery(query4 , Integer.class)
                    .getResultList();

            for(Integer s : result3){
                System.out.println("Integer =" + s);
            }

            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }

}
