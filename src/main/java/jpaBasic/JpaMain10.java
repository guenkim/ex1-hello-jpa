package jpaBasic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/*******************************************************
 * 다대다 양방향
 * 실무에서 사용할수 없음 :  중간 테이블을 두어 다대일 관계로풀어야 함
 * MEMBER
 *@ManyToMany
 *     @JoinColumn(name="member8_product")
 * private List<Product> products = new ArrayList<>();
 *
 * Team
 *@ManyToMany(mappedBy = "products")
 *     private List<Member8> members = new ArrayList<>();
 *
 *
  ******************************************************/
public class JpaMain10 {
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
