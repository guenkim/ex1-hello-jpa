package jpaBasic.query;

import javax.persistence.*;
import java.util.List;

/*******************************************************
 JPQL - Named 쿼리
 Named 쿼리 - 정적 쿼리
     • 미리 정의해서 이름을 부여해두고 사용하는 JPQL
     • 정적 쿼리
     • 어노테이션, XML에 정의
     • 애플리케이션 로딩 시점에 초기화 후 재사용
     • 애플리케이션 로딩 시점에 쿼리를 검증

 JPQL - 벌크 연산
 벌크 연산
     • 재고가 10개 미만인 모든 상품의 가격을 10% 상승하려면?
     • JPA 변경 감지 기능으로 실행하려면 너무 많은 SQL 실행
     • 1. 재고가 10개 미만인 상품을 리스트로 조회한다.
     • 2. 상품 엔티티의 가격을 10% 증가한다.
     • 3. 트랜잭션 커밋 시점에 변경감지가 동작한다.
     • 변경된 데이터가 100건이라면 100번의 UPDATE SQL 실행


     • 쿼리 한 번으로 여러 테이블 로우 변경(엔티티)
     • executeUpdate()의 결과는 영향받은 엔티티 수 반환
     • UPDATE, DELETE 지원
     • INSERT(insert into .. select, 하이버네이트 지원)

 벌크 연산 주의
     • 벌크 연산은 영속성 컨텍스트를 무시하고 데이터베이스에 직접
     쿼리
     • 벌크 연산을 먼저 실행
     • 벌크 연산 수행 후 영속성 컨텍스트 초기화

 ********************************************************/

public class JpaMain5 {
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
              /********************************************************
             JPQL - Named 쿼리
             Named 쿼리 - 정적 쿼리
                 • 미리 정의해서 이름을 부여해두고 사용하는 JPQL
                 • 정적 쿼리
                 • 어노테이션, XML에 정의
                 • 애플리케이션 로딩 시점에 초기화 후 재사용
                 • 애플리케이션 로딩 시점에 쿼리를 검증
             ********************************************************/
            TeamQ teamQ = new TeamQ();
            teamQ.setName("teamC");
            em.persist(teamQ);

            MemberQ memberQ4 = new MemberQ();
            memberQ4.setName("회원1");
            memberQ4.setAge(0);
            memberQ4.setTeam(teamQ);
            em.persist(memberQ4);

            MemberQ memberQ5 = new MemberQ();
            memberQ5.setName("회원2");
            memberQ5.setAge(0);
            memberQ5.setTeam(teamQ);
            em.persist(memberQ5);

            MemberQ memberQ6 = new MemberQ();
            memberQ6.setName("회원3");
            memberQ6.setAge(0);
            memberQ6.setTeam(teamQ);
            em.persist(memberQ6);

            int resultCnt  =  em.createQuery("update MemberQ m set m.age=20")
                    .executeUpdate();

            /*********************************************************
             * 벌크 연산 주의
             * • 벌크 연산은 영속성 컨텍스트를 무시하고 데이터베이스에 직접 쿼리
             * • 벌크 연산을 먼저 실행
             * • 벌크 연산 수행 후 영속성 컨텍스트 초기화
             ********************************************************/
            em.clear();  // >>>>>>>>>>>>>>> 영속성 컨텍스트 초기화 옵션 (반드시 초기화 해야 함)

            MemberQ findMember6 = em.find(MemberQ.class , memberQ6.getId());
            MemberQ findMember5 = em.find(MemberQ.class , memberQ5.getId());
            MemberQ findMember4 = em.find(MemberQ.class , memberQ4.getId());

            System.out.println("age : " + findMember6.getAge());
            System.out.println("age : " + findMember5.getAge());
            System.out.println("age : " + findMember4.getAge());

            /**************************************************************
            JPQL - 벌크 연산
            벌크 연산
                 • 재고가 10개 미만인 모든 상품의 가격을 10% 상승하려면?
                 • JPA 변경 감지 기능으로 실행하려면 너무 많은 SQL 실행
                 • 1. 재고가 10개 미만인 상품을 리스트로 조회한다.
                 • 2. 상품 엔티티의 가격을 10% 증가한다.
                 • 3. 트랜잭션 커밋 시점에 변경감지가 동작한다.
                 • 변경된 데이터가 100건이라면 100번의 UPDATE SQL 실행


                 • 쿼리 한 번으로 여러 테이블 로우 변경(엔티티)
                 • executeUpdate()의 결과는 영향받은 엔티티 수 반환
                 • UPDATE, DELETE 지원
                 • INSERT(insert into .. select, 하이버네이트 지원)

            벌크 연산 주의
             • 벌크 연산은 영속성 컨텍스트를 무시하고 데이터베이스에 직접 쿼리
             • 벌크 연산을 먼저 실행
             • 벌크 연산 수행 후 영속성 컨텍스트 초기화
        **************************************************************/



            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }

}
