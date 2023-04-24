package jpaBasic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/*******************************************************
 * 양방향 연관관계와 연관관계의 주인
  ******************************************************/
public class JpaMain6 {
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
             양방향 매핑 (반대 방향으로 객체 그래프 탐색)
             */

            Team3 team = new Team3();
            team.setName("teamA");
            em.persist(team);

            Member3 member = new Member3();
            member.setName("member1");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            Member3 sMember = em.find(Member3.class,member.getId());
            List<Member3> members = sMember.getTeam().getMembers();
            System.out.println("size :" + members.size());
            for (Member3 m : members){
                System.out.println("member name :" + m.getName());
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
