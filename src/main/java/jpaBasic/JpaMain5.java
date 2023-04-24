package jpaBasic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/*******************************************************
 * 연관관계 매핑 기초 (단방향 연관관계)
  ******************************************************/
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
            /**
             객체를 테이블에 맞추어 모델링
             (외래 키 식별자를 직접 다룸)
             */

            Team1 team = new Team1();
            team.setName("teamA");
            em.persist(team);

            Member1 member = new Member1();
            member.setName("member1");

            // 객체를 참조하지 않고 db 외래키로 관리 하도록 설계
            member.setTeamId(team.getId());
            em.persist(member);

            //팀 조회 시 아래 로직을 사용한다.
            //두번의 조회를 통해서 많이 team을 조회 할 수 있다.
            //이부분의 번거로움을 해결하고자 객체참조 모델을 설계 하려고 한다.
            Member1 sMember = em.find(Member1.class,member.getId());
            Team1 sTeam = em.find(Team1.class,sMember.getTeamId());

            /**
            객체 지향 모델링 (연관관계 저장)
            **/
            //팀 저장
            Team2 team2 = new Team2();
            team2.setName("TeamA");
            em.persist(team2);
            //회원 저장
            Member2 member2 = new Member2();
            member2.setName("member1");
            member2.setTeam(team2); //단방향 연관관계 설정, 참조 저장
            em.persist(member2);

            //영속성 컨텍스트에 위 저장한 객체가 존재하여 아래 조회 쿼리를 DB에 쿼리 하지 않는다.
            //조회 쿼리가 보고 싶을 때는 영속성 컨텍스트를 비워주면 된다.
            em.flush();
            em.clear();

            /**
             객체 지향 모델링 (참조로 연관관계 조회 - 객체 그래프 탐색)
             **/
            //조회
            Member2 findMember = em.find(Member2.class, member2.getId());
            //참조를 사용해서 연관관계 조회
            Team2 findTeam = findMember.getTeam();

            /**
             객체 지향 모델링 (연관관계 수정)
             **/
            //새로운 팀B
            Team2 team3 = new Team2();
            team3.setName("TeamB");
            em.persist(team3);

            // 회원1에 새로운 팀B 설정
            Member2 findMember2 = em.find(Member2.class, member2.getId());
            findMember2.setTeam(team3);

            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();


    }
}
