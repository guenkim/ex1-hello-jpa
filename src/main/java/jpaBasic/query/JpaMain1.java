package jpaBasic.query;

import jpaBasic.Member10;
import jpaBasic.Team7;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 JPQL(Java Persistence Query Language)
 JPQL 소개
 • JPQL은 객체지향 쿼리 언어다.따라서 테이블을 대상으로 쿼리 하는 것이 아니라 엔티티 객체를 대상으로 쿼리한다.
 • JPQL은 SQL을 추상화해서 특정데이터베이스 SQL에 의존하지 않는다.
 • JPQL은 결국 SQL로 변환된다.

 JPQL 문법
 • select m from Member as m where m.age > 18
 • 엔티티와 속성은 대소문자 구분O (Member, age)
 • JPQL 키워드는 대소문자 구분X (SELECT, FROM, where)
 • 엔티티 이름 사용, 테이블 이름이 아님(Member)
 • 별칭은 필수(m) (as는 생략가능
*/

public class JpaMain1 {
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
             JPQL(Java Persistence Query Language)
             JPQL 소개
             • JPQL은 객체지향 쿼리 언어다.따라서 테이블을 대상으로 쿼리 하는 것이 아니라 엔티티 객체를 대상으로 쿼리한다.
             • JPQL은 SQL을 추상화해서 특정데이터베이스 SQL에 의존하지 않는다.
             • JPQL은 결국 SQL로 변환된다.

             JPQL 문법
             • select m from Member as m where m.age > 18
             • 엔티티와 속성은 대소문자 구분O (Member, age)
             • JPQL 키워드는 대소문자 구분X (SELECT, FROM, where)
             • 엔티티 이름 사용, 테이블 이름이 아님(Member)
             • 별칭은 필수(m) (as는 생략가능
             **/

            MemberQ memberQ = new MemberQ();
            memberQ.setName("memberq");
            memberQ.setAge(13);
            em.persist(memberQ);

            /**********************************************************
            • TypeQuery: 반환 타입이 명확할 때 사용
            • Query: 반환 타입이 명확하지 않을 때 사용
             • query.getResultList(): 결과가 하나 이상일 때, 리스트 반환,  결과가 없으면 빈 리스트 반환
             • query.getSingleResult(): 결과가 정확히 하나, 단일 객체 반환
             • 결과가 없으면: javax.persistence.NoResultException
             • 둘 이상이면: javax.persistence.NonUniqueResultException
             ******************************************************/
            TypedQuery<MemberQ> query = em.createQuery("SELECT m FROM MemberQ m", MemberQ.class);
            query.getResultList();
            TypedQuery<String> query2 = em.createQuery("SELECT m.name FROM MemberQ m", String.class);
            query2.getResultList();

            Query query3 = em.createQuery("SELECT m.name, m.age from MemberQ m");
            query3.getResultList();

            /**********************************************************
             * 파라미터 바인딩 - 이름 기준, 위치 기준
             * SELECT m FROM Member m where m.username=:username
                  query.setParameter("username", usernameParam);
               SELECT m FROM Member m where m.username=?1
                  query.setParameter(1, usernameParam);
             **********************************************************/
            TypedQuery<MemberQ> query4 = em.createQuery("SELECT m FROM MemberQ m where m.name= :name", MemberQ.class);
            query4.setParameter("name" ,"memberq");
            List<MemberQ> memberQList =  query4.getResultList();

            // 함수 chaining
            List<MemberQ> memberQList2 = em.createQuery("SELECT m FROM MemberQ m where m.name= :name", MemberQ.class)
                    .setParameter("name" ,"memberq")
                    .getResultList();


            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }

}
