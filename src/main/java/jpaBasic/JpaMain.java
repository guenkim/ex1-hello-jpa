package jpaBasic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/*******************************************************
 * 02.jpa 시작
 ******************************************************/
public class JpaMain {
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
            
            // 저장
            /*
            Member member = new Member();
            member.setId(2L);
            member.setName("helloB");
            em.persist(member);
            */

            //조회
            MemberBean searchMemberBean = em.find(MemberBean.class,2L);
            System.out.println("xx " + searchMemberBean.getName());
            
            //JPQL 조회 대상을 DB가 아닌 객체로 선정
            //통계 및 다양한 쿼리를 ANSI 문법과 유사하게 사용 가능

            /*
            List<Member> result = em.createQuery("select m from Member m" , Member.class)
                    .getResultList();

            for (Member member2 : result){
                System.out.println(member2.getName());
            }
            */


            //수정  직접 수정하는 방법은 없는지 .... 조회 후 수정 한다는 것이 불필요 해 보임
            //searchMember.setName("HelloC");
            //System.out.println("xx " + searchMember.getName());

            //삭제
            //객체를 조회 후 삭제 해야 함 , 직접 삭제하는 방법은 없는지 .... 조회 후 삭제 한다는 것이 불필요 해 보임
            em.remove(searchMemberBean);

            //db 커밋
            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();


    }
}
