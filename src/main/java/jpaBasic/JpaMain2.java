package jpaBasic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/*******************************************************
 * 02.jpa 시작
 * 03.영속성 컨텍스트  <<<<<<<<
 * - 1. 1차 캐시에 저장하여 필요시 DB를 조회 한다던가 캐시에 존재하면 DB를 조회 하지 않음
 *   2. 트랜잭션을 지원하는 쓰기 지연 : INSERT,UPDATE문을 SQL쓰기 지연 저장소에  쿼리를 저장 했다가 일괄 COMMIT
 *   3.엔티티 수정 변경 감지
 *   4.엔티티 삭제
  ******************************************************/
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

            /*************************************************************************************************
             *  2. 트랜잭션을 지원하는 쓰기 지연 : INSERT,UPDATE문을 SQL쓰기 지연 저장소에  쿼리를 저장 했다가 일괄 COMMIT
             *  persistence.xml 에 하기 문장 추가
             *  <property name="hibernate.jdbc.batch.size" value="10"/> <!-- db write시에 일괄 처리 개수 -->
             *************************************************************************************************/
            //비영속
            /*
            Member member1 = new Member(150L,"A");
            Member member2 = new Member(160L,"B");

            //영속 : 영속성 컨텍스트에 저장 되지만 DB에는 저장되지 않음
            em.persist(member1);
            em.persist(member2);
            */
            /*************************************************************************************************
             *  3.엔티티 수정 변경 감지
             *  조회 후 객체에 접근하여 값을 변경하면 업데이트 됨
             *************************************************************************************************/
            MemberBean memberBean = em.find(MemberBean.class,2L);
            memberBean.setName("zzzz");

            /*************************************************************************************************
             *  4.엔티티 삭제
             *  조회 후 객체를 제거하면 db에서 삭제 됨
             *************************************************************************************************/
            //Member member = em.find(Member.class,1L);
            //em.remove(member);

            //영속성 컨텍스트에서 DB로 저장
            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();


    }
}
