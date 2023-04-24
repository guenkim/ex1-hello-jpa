package jpaBasic.query;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;

/*******************************************************
 *  JPQL - 페치 조인(fetch join)
 *  실무에서 정말정말 중요함
 *  SQL 조인 종류X
 * • JPQL에서 성능 최적화를 위해 제공하는 기능
 * • 연관된 엔티티나 컬렉션을 SQL 한 번에 함께 조회하는 기능
 * • join fetch 명령어 사용
 * • 페치 조인 ::= [ LEFT [OUTER] | INNER ] JOIN FETCH 조인경로
 *
 *
 * 엔티티 직접 사용 - 기본키 값
 * • JPQL에서 엔티티를 직접 사용하면 SQL에서 해당 엔티티의 기본키 값을 사용
 * • [JPQL]
     * select count(m.id) from Member m //엔티티의 아이디를 사용
     * select count(m) from Member m //엔티티를 직접 사용
 * • [SQL](JPQL 둘다 같은 다음 SQL 실행)
 *  select count(m.id) as cnt from Member m
 *
********************************************************/

public class JpaMain4 {
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

        /*******************************************************
         *  JPQL - 페치 조인(fetch join)
         *  실무에서 정말정말 중요함
         *  SQL 조인 종류X
         * • JPQL에서 성능 최적화를 위해 제공하는 기능
         * • 연관된 엔티티나 컬렉션을 SQL 한 번에 함께 조회하는 기능
         * • join fetch 명령어 사용
         * • 페치 조인 ::= [ LEFT [OUTER] | INNER ] JOIN FETCH 조인경로
         *
         * 엔티티 페치 조인
         *      • 회원을 조회하면서 연관된 팀도 함께 조회(SQL 한 번에)
         *      • SQL을 보면 회원 뿐만 아니라 팀(T.*)도 함께 SELECT
         * • [JPQL]
         *      select m from Member m join fetch m.team
         * • [SQL]
         *      SELECT M.*, T.* FROM MEMBER M
         *      INNER JOIN TEAM T ON M.TEAM_ID=T.ID
         ********************************************************/

            TeamQ teamQ = new TeamQ();
            teamQ.setName("팀A");
            em.persist(teamQ);

            TeamQ teamQ2 = new TeamQ();
            teamQ2.setName("팀B");
            em.persist(teamQ2);

            MemberQ memberQ = new MemberQ();
            memberQ.setName("회원1");
            memberQ.setTeam(teamQ);
            em.persist(memberQ);

            MemberQ memberQ2 = new MemberQ();
            memberQ2.setName("회원2");
            memberQ2.setTeam(teamQ);
            em.persist(memberQ2);

            MemberQ memberQ3 = new MemberQ();
            memberQ3.setName("회원3");
            memberQ3.setTeam(teamQ2);
            em.persist(memberQ3);

            em.flush();
            em.clear();

            // 아래와 쿼리 작성 시  N+1 발생
            String query = "select m from MemberQ m";
            List<MemberQ> resultList = em.createQuery(query, MemberQ.class)
                    .getResultList();
            for (MemberQ s : resultList){
                System.out.println("Member = " + s.getName() +"," +s.getTeam().getName());
            }
            /**
            // N+1을 해결하기 위해서 fetch join으로 변경 (쿼리 한방으로 해결)
            **/
            query = "select m from MemberQ m join fetch m.team";
            List<MemberQ> resultList2 = em.createQuery(query, MemberQ.class)
                    .getResultList();
            for (MemberQ s : resultList2){
                System.out.println("Member = " + s.getName() +"," +s.getTeam().getName());
            }

            /**
             * 컬렉션 페치 조인
             * • 일대다 관계, 컬렉션 페치 조인
             * • [JPQL]
             *      select t
             *      from Team t join fetch t.members
             *      where t.name = ‘팀A'
             *   [SQL]
             *      SELECT T.*, M.*
             *      FROM TEAM T
             *      INNER JOIN MEMBER M ON T.ID=M.TEAM_ID
             *      WHERE T.NAME = '팀A'
            **/
            // 일반 join
            String jpql = "select distinct t from TeamQ t join t.memberList";

            // fetch join
            /*****************************************************
             fetch join (쿼리 한방으로 해결)
             *****************************************************/
            jpql = "select distinct t from TeamQ t join fetch t.memberList";

            List<TeamQ> resultList3 = em.createQuery(jpql, TeamQ.class)
                    .getResultList();
            for (TeamQ t : resultList3){
                for (MemberQ m : t.getMemberList()){
                    System.out.println("Team = " + t.getName() +"// Member ="+m.getName());
                }
            }

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            /*****************************************************
             * 페치 조인의 특징과 한계
             * • 페치 조인 대상에는 별칭을 줄 수 없다. collection에 조건을 걸 수 없음
             * • 하이버네이트는 가능, 가급적 사용X
             * • 둘 이상의 컬렉션은 페치 조인 할 수 없다.
             * • 컬렉션을 페치 조인하면 페이징 API(setFirstResult,* setMaxResults)를 사용할 수 없다.
             * • 일대일, 다대일 같은 단일 값 연관 필드들은 페치 조인해도 페이징 가능
             * • 하이버네이트는 경고 로그를 남기고 메모리에서 페이징(매우 위험)
             *
             * 연관된 엔티티들을 SQL 한 번으로 조회 - 성능 최적화
             * • 엔티티에 직접 적용하는 글로벌 로딩 전략보다 우선함
             * • @OneToMany(fetch = FetchType.LAZY) //글로벌 로딩 전략
             * • 실무에서 글로벌 로딩 전략은 모두 지연 로딩
             * • 최적화가 필요한 곳은 페치 조인 적용
             *****************************************************/
            jpql = "select distinct t from TeamQ t join fetch t.memberList";
            jpql = "select t from TeamQ t";
                    List<TeamQ> resultList4 = em.createQuery(jpql, TeamQ.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();
            for (TeamQ t : resultList4){
                for (MemberQ m : t.getMemberList()){
                    System.out.println("Team = " + t.getName() +"// Member ="+m.getName());
                }
            }


            em.flush();
            em.clear();

            /*****************************************************
                N+1문제 해결 방법
                1.Fetch Join을 사용한다.
                2.@BatchSize(size=100)를 걸어서 사용한다.
             <property name="hibernate.default_batch_fetch_size" value="100"/>
             ***************************************************/
            jpql = "select t from TeamQ t";
            List<TeamQ> resultList5 = em.createQuery(jpql, TeamQ.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();
            for (TeamQ t1 : resultList5){
                for (MemberQ m2 : t1.getMemberList()){
                    System.out.println("Teamx = " + t1.getName() +"// Memberx ="+m2.getName());
                }
            }

            /****************************************************************************
            * 엔티티 직접 사용 - 기본키 값
                    * • JPQL에서 엔티티를 직접 사용하면 SQL에서 해당 엔티티의 기본키 값을 사용
             * • [JPQL]
                 * select count(m.id) from Member m //엔티티의 아이디를 사용
                 * select count(m) from Member m //엔티티를 직접 사용
             * • [SQL](JPQL 둘다 같은 다음 SQL 실행)
             *  select count(m.id) as cnt from Member m
               **************************************************************************/
            em.flush();
            em.clear();


            MemberQ memberQ4 = new MemberQ();
            memberQ4.setName("회원4");
            memberQ4.setTeam(teamQ2);
            em.persist(memberQ4);

            jpql = "select m from MemberQ m where m = :memberType"; // entity로 조회
            //jpql = "select m from MemberQ m where m.id = :memberId";  // id로 조회
            MemberQ singleResult = em.createQuery(jpql, MemberQ.class)
                    .setParameter("memberType", memberQ4) //entity로 조회
                    //.setParameter("memberId", memberQ4.getId()) //id로 조회
                    .getSingleResult();

            System.out.println("member id : " + singleResult.getId());

            jpql = "select m from MemberQ m where m.team = :team"; // entity로 조회
            List<MemberQ> memberQList = em.createQuery(jpql, MemberQ.class)
                    .setParameter("team", teamQ) //entity로 조회
                    .getResultList();

            System.out.println("memberQList size : " + memberQList.size());



            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }

}
