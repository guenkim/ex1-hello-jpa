package jpaBasic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/*******************************************************
 * 필드와 컬럼 매핑
  ******************************************************/
public class JpaMain4 {
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

            MemberBean memberBean1 = new MemberBean();
            MemberBean memberBean2 = new MemberBean();
            MemberBean memberBean3 = new MemberBean();
            /***
             * GeneratedValue 옵션 사용 시 해당 코드를 주석 처리 해야함
             */
            memberBean1.setName("A");
            memberBean2.setName("B");
            memberBean3.setName("C");

            em.persist(memberBean1);
            em.persist(memberBean2);
            em.persist(memberBean3);


            System.out.println("member.id = " + memberBean1.getId());
            System.out.println("member.id = " + memberBean2.getId());
            System.out.println("member.id = " + memberBean3.getId());


            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();


    }
}
