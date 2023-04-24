package jpaBasic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

/**
 * 연관 소스
 * BaseEntity
 * Item1
 * Book
 * Album
 * Movie
 */

/*******************************************************
 * 상속관계 매핑 방법 (db 모델에 맞추어 사용)
 * 1.조인 전략
 * 2.단일 테이블 전략 (한 테이블로 처리)
 * 4.구현 클래스 마다 테이블화 전략
 * *** 부모 클래스에서 (ITEM1) 아래 옵션을 선언
 * @Inheritance(strategy=InheritanceType.XXX)
 *      • JOINED: 조인 전략
 *      • SINGLE_TABLE: 단일 테이블 전략
 *      • TABLE_PER_CLASS: 구현 클래스마다 테이블 전략
 * • @DiscriminatorColumn(name=“DTYPE”) : db 필드 구분자 처리
 * • @DiscriminatorValue(“XXX”) : 구분자 값 명시 (기본은 엔티티명)
  ******************************************************/

/*******************************************************
 @MappedSuperclass : 예제 BaseEntity class
 • 공통 매핑 정보가 필요할 때 사용(id, name)
 상속관계 매핑X
 • 엔티티X, 테이블과 매핑X
 • 부모 클래스를 상속 받는 자식 클래스에 매핑 정보만 제공
 • 조회, 검색 불가(em.find(BaseEntity) 불가)
 • 직접 생성해서 사용할 일이 없으므로 추상 클래스 권장
  ******************************************************/
public class JpaMain12 {
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
            Movie movie = new Movie();
            movie.setDirector("a");
            movie.setActor("bbb");
            movie.setName("바람과 함꼐");
            movie.setPrice(10000);
            movie.setCreateBy("mother fucker");
            movie.setCreatedDate(LocalDateTime.now());
            movie.setModifiedBy("father fucker");
            movie.setModifiedDate(LocalDateTime.now());
            em.persist(movie);

            em.flush();
            em.clear();

            Movie findMovie = em.find(Movie.class , movie.getId());
            System.out.print(findMovie.getName());



            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();


    }
}
