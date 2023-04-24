package jpaBasic.query;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;

/** *** 묵시적 조인은 사용하지 않는것이 좋다, 명시적 조인으로 변경 필요 : 튜닝 및 알수 없는 쿼리 발생할수 있음
 JPQL - 경로 표현식
 .(점)을 찍어 객체 그래프를 탐색하는 것

 select m.username -> 상태 필드
 from Member m
 join m.team t -> 단일 값 연관 필드
 join m.orders o -> 컬렉션 값 연관 필드
 where t.name = '팀A'

 경로 표현식 용어 정리
     • 상태 필드(state field): 단순히 값을 저장하기 위한 필드
         (ex: m.username)
     • 연관 필드(association field): 연관관계를 위한 필드
     • 단일 값 연관 필드:
         @ManyToOne, @OneToOne, 대상이 엔티티(ex: m.team)
     • 컬렉션 값 연관 필드:
        @OneToMany, @ManyToMany, 대상이 컬렉션(ex: m.orders)

    경로 표현식 특징
        • 상태 필드(state field): 경로 탐색의 끝, 탐색X
        • 단일 값 연관 경로: 묵시적 내부 조인(inner join) 발생, 탐색O
        • 컬렉션 값 연관 경로: 묵시적 내부 조인 발생, 탐색X
        • FROM 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통
        해 탐색 가능

    상태 필드 경로 탐색
    • JPQL: select m.username, m.age from Member m
    • SQL: select m.username, m.age from Member m

    단일 값 연관 경로 탐색
        • JPQL: select o.member from Order o
        • SQL:
        select m.*
        from Orders o
        inner join Member m on o.member_id = m.id

    명시직 조인, 묵시적 조인
        • 명시적 조인: join 키워드 직접 사용
        • select m from Member m join m.team t
        • 묵시적 조인: 경로 표현식에 의해 묵시적으로 SQL 조인 발생
        (내부 조인만 가능)
        • select m.team from Member m

    경로 표현식 - 예제
        • select o.member.team
            from Order o -> 성공
        • select t.members from Team -> 성공
        • select t.members.username from Team t -> 실패
        • select m.username from Team t join t.members m -> 성공

    경로 탐색을 사용한 묵시적 조인 시 주의사항
        • 항상 내부 조인
        • 컬렉션은 경로 탐색의 끝, 명시적 조인을 통해 별칭을 얻어야함
        • 경로 탐색은 주로 SELECT, WHERE 절에서 사용하지만 묵시
        적 조인으로 인해 SQL의 FROM (JOIN) 절에 영향을 줌

    실무 조언
    • 가급적 묵시적 조인 대신에 명시적 조인 사용
    • 조인은 SQL 튜닝에 중요 포인트
    • 묵시적 조인은 조인이 일어나는 상황을 한눈에 파악하기 어려움
*/

public class JpaMain3 {
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
         ***묵시적 조인은 사용하지 않는것이 좋다, 명시적 조인으로 변경 필요 : 튜닝 및 알수 없는 쿼리 발생할수 있음
         JPQL - 경로 표현식
         .(점)을 찍어 객체 그래프를 탐색하는 것

         select m.username -> 상태 필드
         from Member m
         join m.team t -> 단일 값 연관 필드
         join m.orders o -> 컬렉션 값 연관 필드
         where t.name = '팀A'

         경로 표현식 용어 정리
         • 상태 필드(state field): 단순히 값을 저장하기 위한 필드
         (ex: m.username)
         • 연관 필드(association field): 연관관계를 위한 필드
         • 단일 값 연관 필드:
         @ManyToOne, @OneToOne, 대상이 엔티티(ex: m.team)
         • 컬렉션 값 연관 필드:
         @OneToMany, @ManyToMany, 대상이 컬렉션(ex: m.orders)

         경로 표현식 특징
         • 상태 필드(state field): 경로 탐색의 끝, 탐색X
         • 단일 값 연관 경로: 묵시적 내부 조인(inner join) 발생, 탐색O
         • 컬렉션 값 연관 경로: 묵시적 내부 조인 발생, 탐색X
         • FROM 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통
         해 탐색 가능

         상태 필드 경로 탐색
         • JPQL: select m.username, m.age from Member m
         • SQL: select m.username, m.age from Member m

         단일 값 연관 경로 탐색
         • JPQL: select o.member from Order o
         • SQL:
         select m.*
         from Orders o
         inner join Member m on o.member_id = m.id

         명시직 조인, 묵시적 조인
         • 명시적 조인: join 키워드 직접 사용
         • select m from Member m join m.team t
         • 묵시적 조인: 경로 표현식에 의해 묵시적으로 SQL 조인 발생
         (내부 조인만 가능)
         • select m.team from Member m

         경로 표현식 - 예제
         • select o.member.team
         from Order o -> 성공
         • select t.members from Team -> 성공
         • select t.members.username from Team t -> 실패
         • select m.username from Team t join t.members m -> 성공

         경로 탐색을 사용한 묵시적 조인 시 주의사항
         • 항상 내부 조인
         • 컬렉션은 경로 탐색의 끝, 명시적 조인을 통해 별칭을 얻어야함
         • 경로 탐색은 주로 SELECT, WHERE 절에서 사용하지만 묵시
         적 조인으로 인해 SQL의 FROM (JOIN) 절에 영향을 줌

         실무 조언
         • 가급적 묵시적 조인 대신에 명시적 조인 사용
         • 조인은 SQL 튜닝에 중요 포인트
         • 묵시적 조인은 조인이 일어나는 상황을 한눈에 파악하기 어려움
 */

            TeamQ teamQ = new TeamQ();
            teamQ.setName("teamA");
            em.persist(teamQ);

            TeamQ teamQ2 = new TeamQ();
            teamQ2.setName("teamB");
            em.persist(teamQ2);

            MemberQ memberQ = new MemberQ();
            memberQ.setName("memberq");
            memberQ.setTeam(teamQ);
            em.persist(memberQ);

            MemberQ memberQ2 = new MemberQ();
            memberQ2.setName("memberq2");
            memberQ2.setTeam(teamQ2);
            em.persist(memberQ2);

            em.flush();
            em.clear();

            //경로 표현식 특징
            // • 상태 필드(state field): 경로 탐색의 끝, 탐색X

            String query = "select m.name from MemberQ m";
            List<String> stringList = em.createQuery(query, String.class)
                    .getResultList();

            for (String s : stringList){
                System.out.println("string = " + s);
            }

            //경로 표현식 특징
            // ***묵시적 조인은 사용하지 않는것이 좋다, 명시적 조인으로 변경 필요 : 튜닝 및 알수 없는 쿼리 발생할수 있음
            //• 단일 값 연관 경로: 묵시적 내부 조인(inner join) 발생, 탐색O
            query = "select m.team from MemberQ m";
            List<TeamQ> teamQList = em.createQuery(query, TeamQ.class)
                    .getResultList();

            for (TeamQ q : teamQList){
                System.out.println("TeamQ = " + q.getName());
            }

            //경로 표현식 특징
            //• 컬렉션 값 연관 경로: 묵시적 내부 조인 발생, 탐색X
            // ***묵시적 조인은 사용하지 않는것이 좋다, 명시적 조인으로 변경 필요 : 튜닝 및 알수 없는 쿼리 발생할수 있음
            //• FROM 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능
            query = "select t.memberList from TeamQ t";
            Collection  memberQS = em.createQuery(query, Collection.class)
                    .getResultList();
            System.out.println("Collection = " + memberQS);








            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }

}
