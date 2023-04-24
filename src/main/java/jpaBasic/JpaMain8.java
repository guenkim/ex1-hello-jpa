package jpaBasic;

import javax.persistence.*;
import java.util.List;

/*******************************************************
 * 다대일 양방향
 * MEMBER
 *  @ManyToOne
 *     @JoinColumn(name = "TEAM_ID")
 *     private Team5 team;  >>>>> 연관관계 주인
 *
 *  TEAM
 *  @OneToMany(mappedBy = "team")
*   private List<Member5> members = new ArrayList<>();
  ******************************************************/
public class JpaMain8 {
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


            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();


    }
}
