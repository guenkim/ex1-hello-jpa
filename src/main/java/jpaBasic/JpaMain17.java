package jpaBasic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 연관 소스
 * Member13
 * Address
 * AddressEntity
 *
 *
 *
 */

/******************************************
 *  컬렉션 값 타입은 가능하면 사용하지 말자 (강사가 권장) , 의도하지 않게 동작함
 *- 컬렉션 값 타입(collection value type)
 *  값 타입 컬렉션
 *      • 값 타입을 하나 이상 저장할 때 사용
 *      • @ElementCollection, @CollectionTable 사용
 *      • 데이터베이스는 컬렉션을 같은 테이블에 저장할 수 없다.
 *      • 컬렉션을 저장하기 위한 별도의 테이블이 필요함
 *
 * 값 타입 컬렉션의 제약사항
 *  * • 값 타입 컬렉션에 변경 사항이 발생하면, 주인 엔티티와 연관된
 *   모든 데이터를 삭제하고, 값 타입 컬렉션에 있는 현재 값을 모두  다시 저장한다.
 *
 *값 타입 컬렉션 대안
 * • 실무에서는 상황에 따라 값 타입 컬렉션 대신에 일대다 관계를 고려
 * • 일대다 관계를 위한 엔티티를 만들고, 여기에서 값 타입을 사용
 * • 영속성 전이(Cascade) + 고아 객체 제거를 사용해서 값 타입 컬렉션 처럼 사용
 * • EX) AddressEntity
 *****************************************/

public class JpaMain17 {
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
             * 컬렉션 값 타입은 가능하면 사용하지 말자 (강사가 권장) , 의도하지 않게 동작함
             *- 컬렉션 값 타입(collection value type)
             *  값 타입 컬렉션
             *      • 값 타입을 하나 이상 저장할 때 사용
             *      • @ElementCollection, @CollectionTable 사용
             *      • 데이터베이스는 컬렉션을 같은 테이블에 저장할 수 없다.
             *      • 컬렉션을 저장하기 위한 별도의 테이블이 필요함
             *****************************************/

            /******************************************
             * 저장 예제
             *****************************************/
            Member13 member13 = new Member13();
            member13.setName("member1");
            member13.setHomeAddress(new Address("homeCity","street","address"));
            
            member13.getFavoriteFoods().add("치킨");
            member13.getFavoriteFoods().add("소주");

            //member13 주석으로 막아 놨음 풀고 테스트 하기 바람
            //member13.getAddressHistory().add(new Address("old1" ,"street","address"));
            //member13.getAddressHistory().add(new Address("old2" ,"street","address"));

            em.persist(member13);

            em.flush();
            em.clear();

            /******************************************
             * 조회 예제
             *****************************************/
            // 컬렉션 타입은 지연로딩이다.
            Member13 findMmember = em.find(Member13.class , member13.getId());
            Set<String> favoriteFoods = findMmember.getFavoriteFoods();
            for(String favoiteFood : favoriteFoods){
                System.out.println("food :" + favoiteFood);
            }

            List<Address> addressHist = new ArrayList<>();
            //member13 주석으로 막아 놨음 풀고 테스트 하기 바람
            //addressHist = member13.getAddressHistory();

            for(String favoiteFood : favoriteFoods){
                System.out.println("food :" + favoiteFood);
            }

            for(Address address : addressHist){
                System.out.println("address :" + address.getCity());
            }

            /******************************************
             * 수정 예제
             * • 값 타입 컬렉션에 변경 사항이 발생하면, 주인 엔티티와 연관된
             *   모든 데이터를 삭제하고, 값 타입 컬렉션에 있는 현재 값을 모두  다시 저장한다.
             *****************************************/
            Member13 findMmember2 = em.find(Member13.class , member13.getId());
            //findMmember2.getHomeAddress().setCity("city"); >> 값 타입은 이런식으로 변경 하면 안됨 , 공유한 객체까지 값변경 update 발생
            findMmember2.setHomeAddress(new Address("home2 address","street","address")); //이런식으로 객체 생성하여 변경 해야 함

            //값 타입 컬렉션 변경
            //치킨 > 족발 : 삭제 후 등록
            findMmember2.getFavoriteFoods().remove("치킨");
            findMmember2.getFavoriteFoods().add("족발");

            //member13 주석으로 막아 놨음 풀고 테스트 하기 바람
            //findMmember2.getAddressHistory().remove(new Address("old1" ,"street","address"));
            //findMmember2.getAddressHistory().add(new Address("new1" ,"street","address"));

            /******************************************************
             *값 타입 컬렉션 대안
             * • 실무에서는 상황에 따라 값 타입 컬렉션 대신에 일대다 관계를 고려
             * • 일대다 관계를 위한 엔티티를 만들고, 여기에서 값 타입을 사용
             * • 영속성 전이(Cascade) + 고아 객체 제거를 사용해서 값 타입 컬렉션 처럼 사용
             * • EX) AddressEntity
             *****************************************/
            Member13 member14 = new Member13();
            member14.setName("member2");
            member14.setHomeAddress(new Address("homeCity","street","address"));
            em.persist(member14);

            em.flush();
            em.clear();


            Member13 findMmember3 = em.find(Member13.class , member14.getId());
            //findMmember2.getHomeAddress().setCity("city"); >> 값 타입은 이런식으로 변경 하면 안됨 , 공유한 객체까지 값변경 update 발생
            findMmember3.setHomeAddress(new Address("home2 address","street","address")); //이런식으로 객체 생성하여 변경 해야 함


            //member13 주석으로 막아 놨음 풀고 테스트 하기 바람
            // 컬렉션 타입 수정 방법 예제
            //member14.getAddressHistory().add(new Address("old1" ,"street","address"));
            //member14.getAddressHistory().add(new Address("old2" ,"street","address"));

            // 컬렉션 타입을 일대다 형태로 변경
            findMmember3.getAddressHist().add(new AddressEntity("old1" ,"street","address"));
            findMmember3.getAddressHist().add(new AddressEntity("old2" ,"street","address"));
            findMmember3.getAddressHist().add(new AddressEntity("old3" ,"street","address"));
            findMmember3.getAddressHist().add(new AddressEntity("old4" ,"street","address"));
            findMmember3.getAddressHist().add(new AddressEntity("old5" ,"street","address"));

            // 일대다 수정
            List<AddressEntity> entities = findMmember3.getAddressHist();
            int index = 0;
            int cIndex = 0;
            for(AddressEntity addressEntity : entities){
                if(addressEntity.getAddress().getCity().equals("old5")){
                    cIndex = index;
                }
                index++;
            }
            // 제거
            findMmember3.getAddressHist().remove(cIndex);
            //수정은 제거 후 등록
            findMmember3.getAddressHist().add(new AddressEntity("new old5" ,"street","address"));








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
