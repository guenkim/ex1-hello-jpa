package jpaBasic.query;

import jpaBasic.*;
import jpabook.jpashop.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 객체지향 쿼리 언어 소개
 강사권장 spec
 JPQL + QUERYDSL + JDBCTemplate(통계성 쿼리나 정말 어렵고 복잡한 쿼리일때 사용)
 */

/******************************************
JPQL
        • JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어 제공
        • SQL과 문법 유사, SELECT, FROM, WHERE, GROUP BY,
        HAVING, JOIN 지원
        • JPQL은 엔티티 객체를 대상으로 쿼리
        • SQL은 데이터베이스 테이블을 대상으로 쿼리
 *******************************************/

/******************************************
 *  Criteria 소개
 * • 문자가 아닌 자바코드로 JPQL을 작성할 수 있음
 * • JPQL 빌더 역할
 * • JPA 공식 기능
 * • 단점: 너무 복잡하고 실용성이 없다.
 * • Criteria 대신에 QueryDSL 사용 권장
 *****************************************/

/******************************************
 QueryDSL 소개
 • 문자가 아닌 자바코드로 JPQL을 작성할 수 있음
 • JPQL 빌더 역할
 • 컴파일 시점에 문법 오류를 찾을 수 있음
 • 동적쿼리 작성 편리함
 • 단순하고 쉬움
 • 실무 사용 권장
 ******************************************/

/******************************************
네이티브 SQL 소개
        • JPA가 제공하는 SQL을 직접 사용하는 기능
        • JPQL로 해결할 수 없는 특정 데이터베이스에 의존적인 기능
        • 예) 오라클 CONNECT BY, 특정 DB만 사용하는 SQL 힌트
 ******************************************/

/******************************************
JDBC 직접 사용, SpringJdbcTemplate 등
        • JPA를 사용하면서 JDBC 커넥션을 직접 사용하거나, 스프링
        JdbcTemplate, 마이바티스등을 함께 사용 가능
        • 단 영속성 컨텍스트를 적절한 시점에 강제로 플러시 필요
        • 예) JPA를 우회해서 SQL을 실행하기 직전에 영속성 컨텍스트
        수동 플러시
 ******************************************/
public class JpaMain {
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
            /******************************************
             JPQL
             • JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어 제공
             • SQL과 문법 유사, SELECT, FROM, WHERE, GROUP BY,
             HAVING, JOIN 지원
             • JPQL은 엔티티 객체를 대상으로 쿼리
             • SQL은 데이터베이스 테이블을 대상으로 쿼리
             *******************************************/
            String sql = "select m from MemberQ m where m.name like '%kim%'";
            List<MemberQ> memberQList = em.createQuery(sql, MemberQ.class).getResultList();

            /******************************************
             *  Criteria 소개
             * • 문자가 아닌 자바코드로 JPQL을 작성할 수 있음
             * • JPQL 빌더 역할
             * • JPA 공식 기능
             * • 단점: 너무 복잡하고 실용성이 없다.
             * • Criteria 대신에 QueryDSL 사용 권장
             *****************************************/
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<MemberQ> query = cb.createQuery(MemberQ.class);
            Root<MemberQ> m = query.from(MemberQ.class);

            //CriteriaQuery<MemberQ> cq = query.select(m).where(cb.equal(m.get("name"),"kim"));
            CriteriaQuery<MemberQ> cq = query.select(m);
            String name="xxx";
            if(name !=null){
                cq = cq.where(cb.equal(m.get("name"),"kim"));
            }
            List<MemberQ> resultList = em.createQuery(cq).getResultList();

            /******************************************
            QueryDSL 소개
            • 문자가 아닌 자바코드로 JPQL을 작성할 수 있음
            • JPQL 빌더 역할
            • 컴파일 시점에 문법 오류를 찾을 수 있음
            • 동적쿼리 작성 편리함
            • 단순하고 쉬움
            • 실무 사용 권장
             ******************************************/

            /******************************************
             네이티브 SQL 소개
             • JPA가 제공하는 SQL을 직접 사용하는 기능
             • JPQL로 해결할 수 없는 특정 데이터베이스에 의존적인 기능
             • 예) 오라클 CONNECT BY, 특정 DB만 사용하는 SQL 힌트
             ******************************************/
            em.createNativeQuery("select MEMBER_ID, name from MEMBER").getResultList();

            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }

}
