package jpaBasic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/*******************************************************
 * 양방향 연관관계 주의 사항 , 실수
  ******************************************************/
public class JpaMain7 {
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
             양방향 매핑시 가장 많이 하는 실수
             1.(연관관계의 주인에 값을 입력하지 않음)
             */
            Member4 member = new Member4();
            member.setName("member2");
            //member.setTeam(team);  /** (연관관계의 주인에 값을 입력하지 않음) **/
            em.persist(member);


            Team4 team = new Team4();
            team.setName("teamA");
            team.getMembers().add(member);
            em.persist(team);

            
            /**  정상적인 소스 **/
            /**
             1.순수 객체 상태를 고려해서 항상 양쪽에 값을 설정하자
             member4.setTeam(team4);
             team4.getMembers().add(member4);
             */
            Team4 team4 = new Team4();
            team4.setName("teamA");
            em.persist(team4);

            Member4 member4 = new Member4();
            member4.setName("member2");
            member4.setTeam(team4);  // 양쪽값에 값 세팅 team4.getMembers().add(member4); >>> 이코드를 setTeam 안에 포함
            em.persist(member4);
            


            Team4 findTeam =  em.find(Team4.class,team4.getId());
            List<Member4> result = findTeam.getMembers();

            System.out.println("=========================================");
            for(Member4 m4 : result){
                System.out.println("m= " + m4.getName());
            }
            System.out.println("=========================================");

            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();


    }
}
