package jpaBasic;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * 연관 소스
 * Member11
 * Team8
 */

/*******************************************************
 * 즉시로딩과 지연로딩
 * -지연로딩 선언 : @ManyToOne, @OneToOne은 기본이 즉시 로딩  -> LAZY로 설정 해야 함
 *     팀에게는 proxy 객체를 줌, 사용 할 때만 db에게 쿼리 함
 *     @ManyToOne(fetch = FetchType.LAZY)
 * - 즉시로딩 선언 (권장하지 않음) : 예상외의 쿼리가 발생
 *     주인, 참조 객체 모두 entity로 돌려줌 , join 쿼리 실행
       @ManyToOne(fetch = FetchType.EAGER)
  ******************************************************/

/*******************************************************
 *• 가급적 지연 로딩만 사용(특히 실무에서) , 즉시로딩이 필요한 경우에만 별도로 설정
 * • 즉시 로딩을 적용하면 예상하지 못한 SQL이 발생
 * • 즉시 로딩은 JPQL에서 N+1 문제를 일으킨다.
 * • @ManyToOne, @OneToOne은 기본이 즉시 로딩  -> LAZY로 설정
 * • @OneToMany, @ManyToMany는 기본이 지연 로딩
 *******************************************************/

/*******************************************************
지연 로딩 활용 - 실무
 모든 연관관계에 지연 로딩을 사용해라!
 • 실무에서 즉시 로딩을 사용하지 마라!
 • JPQL fetch 조인이나, 엔티티 그래프 기능을 사용해라!
 (뒤에서 설명)
 • 즉시 로딩은 상상하지 못한 쿼리가 나간다.
 *******************************************************/


public class JpaMain14 {
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
            Team8 team8 = new Team8();
            team8.setName("teamA");
            em.persist(team8);

            Member11 member11 = new Member11();
            member11.setName("member11");
            member11.setTeam(team8);
            em.persist(member11);
            
            em.flush();
            em.clear();
            
            Member11 findMember = em.find(Member11.class , member11.getId());
            System.out.println("team:" + findMember.getTeam().getClass()); // proxy 객체

            //실제 team 객체의 변수나 메소드를 참조 할 때에 db에 쿼리한다. , proxy 객체 초기화
            System.out.println("team name:" + findMember.getTeam().getName()); 
            System.out.println("team:" + findMember.getTeam().getClass());


            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }

    private static void printMember(Member10 member10){
        System.out.print("username =" + member10.getName()) ;
    }
    private static void printMemberAndTeam(Member10 member){
        String username = member.getName();
        System.out.print("username =" + username) ;
        Team7 team7 = member.getTeam();
        System.out.println(team7.getName());

    }
}
