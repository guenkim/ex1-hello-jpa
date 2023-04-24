package jpaBasic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

/**
 * 연관 소스
 * Member12
 * Address
 * Period
 *
 *
 *
 */

/******************************************
 * -- 값 타입
 * 기본값 타입
         엔티티 타입
            @Entity로 정의하는 객체
            데이터가 변해도 식별자로 지속해서 추적 가능
             예) 회원 엔티티의 키나 나이 값을 변경해도 식별자로 인식 가능
         -값 타입
            int, Integer, String처럼 단순히 값으로 사용하는 자바 기본 타입이나 객체
            식별자가 없고 값만 있으므로 변경시 추적 불가
             예) 숫자 100을 200으로 변경하면 완전히 다른 값으로 대체
            -기본값 타입
                • 자바 기본 타입(int, double)
                • 래퍼 클래스(Integer, Long)
                • String
            - 임베디드 타입(embedded type, 복합 값 타입)
                    임베디드 타입 사용법
                    • @Embeddable: 값 타입을 정의하는 곳에 표시
                    • @Embedded: 값 타입을 사용하는 곳에 표시
                    • 기본 생성자 필수
                    • 불변객체화 시켜야 됨
                        불변 객체
                        • 객체 타입을 수정할 수 없게 만들면 부작용을 원천 차단
                        • 값 타입은 불변 객체(immutable object)로 설계해야함
                        • 불변 객체: 생성 시점 이후 절대 값을 변경할 수 없는 객체
                        • 생성자로만 값을 설정하고 수정자(Setter)를 만들지 않으면 됨
                        • 참고: Integer, String은 자바가 제공하는 대표적인 불변 객체
            -값 타입 비교
 *****************************************/

public class JpaMain16 {
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
            /******************************************
             - 임베디드 타입(embedded type, 복합 값 타입)
             임베디드 타입 사용법
                 • @Embeddable: 값 타입을 정의하는 곳에 표시
                 • @Embedded: 값 타입을 사용하는 곳에 표시
                 • 기본 생성자 필수
                 • 불변객체화 시켜야 됨
                 불변 객체
                 • 객체 타입을 수정할 수 없게 만들면 부작용을 원천 차단
                 • 값 타입은 불변 객체(immutable object)로 설계해야함
                 • 불변 객체: 생성 시점 이후 절대 값을 변경할 수 없는 객체
                 • 생성자로만 값을 설정하고 수정자(Setter)를 만들지 않으면 됨
                 • 참고: Integer, String은 자바가 제공하는 대표적인 불변 객체
             *****************************************/
            //임베디드 타입(embedded type, 복합 값 타입)
            Member12 member = new Member12();
            member.setAddress(new Address("서울" , "망원로" ,"서울 망원로6길"));
            member.setPeriod(new Period(LocalDateTime.now() , LocalDateTime.now()));
            em.persist(member);

            /******************************************
             - 불변 객체
                 • 객체 타입을 수정할 수 없게 만들면 부작용을 원천 차단
                 • 값 타입은 불변 객체(immutable object)로 설계해야함
                 • 불변 객체: 생성 시점 이후 절대 값을 변경할 수 없는 객체
                 • 생성자로만 값을 설정하고 수정자(Setter)를 만들지 않으면 됨
                 • 참고: Integer, String은 자바가 제공하는 대표적인 불변 객체
             *****************************************/
            Address address = new Address("서울","망원로","서울 망원로 6리");

            Member12 member12 = new Member12() ;
            member12.setName("geun");
            member12.setAddress(address);
            em.persist(member12);


            //아래와 set을 이용하여 같이 주소 값을 변경하지 않는다. address 클래스를 다시 생성하여 주입한다.
            //address.set()
            Address address2 = new Address("서울","남가좌동","서울 남가좌동2동 225-4호 7통 2반");
            member12.setAddress(address2);

            /******************************************
             값 타입 비교
             객체에 equals , hashCode 함수 생성 (intellij에서 자동 만들어 줌, 반드시 사용해야 함)

             값 타입의 비교
             • 동일성(identity) 비교: 인스턴스의 참조 값을 비교, == 사용
             • 동등성(equivalence) 비교: 인스턴스의 값을 비교, equals() 사용
             • 값 타입은 a.equals(b)를 사용해서 동등성 비교를 해야 함
             • 값 타입의 equals() 메소드를 적절하게 재정의(주로 모든 필드 사용)
             *****************************************/
            Address address3 = new Address("서울","남가좌동","서울 남가좌동2동 225-4호 7통 2반");
            Address address4 = new Address("서울","남가좌동","서울 남가좌동2동 225-4호 7통 2반");
            System.out.println("주소객체의 값 비교: " + address3.equals(address4));




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
