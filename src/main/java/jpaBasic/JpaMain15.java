package jpaBasic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * 연관 소스
 * Parent
 * Child
 */

/******************************************
 * -- 영속성 전이
 *      • 특정 엔티티를 영속 상태로 만들 때 연관된 엔티티도 함께 영속
 *          상태로 만들도 싶을 때
 *      • 예: 부모 엔티티를 저장할 때 자식 엔티티도 함께 저장.
 *
 *      Parent객체에 아래와 같이 선언
 *      @OneToMany(mappedBy = "parent" , cascade = CascadeType.ALL)
 *
 *      CASCADE의 종류
 *      • ALL: 모두 적용 > 사용 해 볼만함
 *      • PERSIST: 영속 > 사용 해 볼만함
 *      • REMOVE: 삭제
 *      • MERGE: 병합
 *      • REFRESH: REFRESH
 *      • DETACH: DETACH
 *
 *  -- 고아 객체 제거: 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제 (가능하면 쓰지말자)
 *  * 가능하면 쓰지말자
 *     orphanRemoval = true
 *****************************************/

public class JpaMain15 {
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
            Child child1 = new Child();
            Child child2 = new Child();


            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            //자동으로 child도 db에 등록된다. >> @OneToMany(mappedBy = "parent" , cascade = CascadeType.ALL,orphanRemoval = true)
            //cascade = CascadeType.ALL 이 옵션 때문
            em.persist(parent);

            
            Parent findParent = em.find(Parent.class,parent.getId());
            //list에서 child를 제거하면 db child 테이블에 delete 쿼리를 던짐
            //orphanRemoval = true >> 고아객체관리 옵션
            findParent.getChildList().remove(0);



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
