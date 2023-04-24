package jpaBasic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/*******************************************************
 * 일대다 단방향 (권장하지 않음) : 일(1)이 연관관계의 주인으로 함
 * 강사는 다대일 양방향으로 처리하도록 권장 함.
 * MEMBER
 *
 *
 * TEAM
 *@OneToMany
 *     @JoinColumn(name="TEAM_ID")
 *     private List<Member6> members = new ArrayList<>();
  ******************************************************/
public class JpaMain9 {
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

            Member6 member6 = new Member6();
            member6.setName("test user");
            em.persist(member6);

            Team6 team6 = new Team6();
            team6.setName("team fuck");
            /** 핵심 구문 **/
            team6.getMembers().add(member6); //이 코드가 없으면 member의 team_id가 null로 들어간다.
            em.persist(team6);


            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();


    }
}
