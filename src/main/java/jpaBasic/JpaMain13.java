package jpaBasic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

/**
 * 연관 소스
 * Member10
 * Team7
 */

/*******************************************************
 * 프록시
 * 프록시 객체는 처음 사용할 때 한 번만 초기화
 * • 프록시 객체를 초기화 할 때, 프록시 객체가 실제 엔티티로 바뀌는 것은 아님, 초기화되면 프록시 객체를 통해서 실제 엔티티에 접근 가능
 * • 프록시 객체는 원본 엔티티를 상속받음, 따라서 타입 체크시 주의해야함 (== 비교 실패, 대신 instance of 사용)
 * • 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference()를 호출해도 실제 엔티티 반환
 * • 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하면 문제 발생
 *     (하이버네이트는 org.hibernate.LazyInitializationException 예외를 터트림)
  ******************************************************/

public class JpaMain13 {
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
            Team7 tema7 = new Team7();
            tema7.setName("team1");
            em.persist(tema7);

            Member10 member10 = new Member10();
            member10.setName("memberA");
            member10.setTeam(tema7);
            em.persist(member10);

            em.flush();
            em.clear();

            //Member10 findMember = em.find(Member10.class, member10.getId());
            Member10 findMember = em.getReference(Member10.class, member10.getId());

            //프록시 인스턴스의 초기화 여부 확인
            System.out.println("proxy 초기화 여부 :" + emf.getPersistenceUnitUtil().isLoaded(findMember));
            // 프록시 클래스 확인 방법
            System.out.println("findMember :" + findMember.getClass()); //proxy
            // 프록시 강제 초기화
            org.hibernate.Hibernate.initialize(findMember);
            // 참고: JPA 표준은 강제 초기화 없음
            findMember.getName(); //강제 호출하여 초기화 시킴
            System.out.println("proxy 초기화 여부 :" + emf.getPersistenceUnitUtil().isLoaded(findMember));

            System.out.println("=======================================");
            System.out.println("findMember : " + findMember.getClass());
            System.out.println("id : " + findMember.getId());
            System.out.println("name : " + findMember.getName());
            System.out.println("=======================================");


            //printMember(member10);
            //printMemberAndTeam(member10);

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
