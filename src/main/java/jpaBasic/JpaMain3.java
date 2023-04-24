package jpaBasic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/*******************************************************
 * 02.jpa 시작
 * 03.영속성 컨텍스트
 * 04 flush
 * 05 준영속 상태
  ******************************************************/
public class JpaMain3 {
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

            /*******************************************************
             * 04 flush : 영속성 컨텍스트의 변경내용을 데이터베이스에 동기화
             * jpql 실행 시 flush 실행
             ******************************************************/
            //비영속
            //Member member1 = new Member(200L,"member200");

            //영속 : 영속성 컨텍스트에 저장 되지만 DB에는 저장되지 않음
            //em.persist(member1);
            //em.flush();

            /*** commit 이전에 insert문이 실행 된다. ***/
            //System.out.println("========================================");
            //영속성 컨텍스트에서 DB로 저장


             /*******************************************************
             *05 준영속 상태
             ******************************************************/
            //비영속
            MemberBean memberBean2 = em.find(MemberBean.class , 2L);
            memberBean2.setName("xxxxxxxxxxx");
            //영속 : 영속성 컨텍스트에서 제외 되어 위 데이터값이 변경 되지 않음
            em.detach(memberBean2);


            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();


    }
}
