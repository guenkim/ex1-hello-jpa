package jpaBasic;

import javax.persistence.*;
import java.util.Date;

/*************************************
 @Entity
 @Entity가 붙은 클래스는 JPA가 관리, 엔티티라 한다.
 JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 필수

 - 주의
 • 기본 생성자 필수(파라미터가 없는 public 또는 protected 생성자)
 • final 클래스, enum, interface, inner 클래스 사용X
 • 저장할 필드에 final 사용 X

   JPA에서 사용할 엔티티 이름을 지정한다.
 • 기본값: 클래스 이름을 그대로 사용(예: Member)
 • 같은 클래스 이름이 없으면 가급적 기본값을 사용한다.

 ex) @Entity(name="Member")

 @Table
 @Table은 엔티티와 매핑할 테이블 지정
 ex) @Table(name="MEMBER")
 -속성
 name : 매핑할 테이블 이름

 @Column
 @Column은 DDL을 자동 생성할 때만 사용되고  JPA의 실행 로직에는 영향을 주지 않는다
  제약조건 추가: 회원 이름은 필수, 10자 초과X
 ex) @Column(nullable = false, length = 10)

 DDL 생성 기능은 DDL을 자동 생성할 때만 사용되고
 JPA의 실행 로직에는 영향을 주지 않는다


 @Column 컬럼 매핑
 @Temporal 날짜 타입 매핑
 @Enumerated
 enum 타입 매핑
 @Lob BLOB, CLOB 매핑
 @Transient 특정 필드를 컬럼에 매핑하지 않음(매핑 무시) : db와 무관하게 연산에만 필요한 변수에 선언
 *************************************/
@Entity(name="MemberBean")
@Table(name="MEMBERBean")
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 50) //allocationSize : 시퀀스 한 번 호출에 증가하는 수(성능 최적화에 사용됨)
public class MemberBean {
    public MemberBean(){

    }

    public MemberBean(Long id , String name){
        this.id = id;
        this.name =name;
    }

    /**기본키 매핑 : primary key에 선언
     * @Id : 값 직접 할당
     * @GeneratedValue
     *      IDENTITY: 데이터베이스에 위임, MYSQL
     *      SEQUENCE: 데이터베이스 시퀀스 오브젝트 사용, ORACLE @SequenceGenerator 필요
     *      TABLE: 키 생성용 테이블 사용, 모든 DB에서 사용 @TableGenerator 필요
     * •    AUTO: 방언에 따라 자동 지정, 기본값
     */
    @Id
    //IDENTITY: 데이터베이스에 위임, MYSQL
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //SEQUENCE: 데이터베이스 시퀀스 오브젝트 사용, ORACLE @SequenceGenerator 필요
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    
    /*
     * db에서 사용하고 있는 시퀀스 사용방법
     * SequenceGenerator에 선언된 시퀀스 제너레이터 사용
     *
     **/
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "MEMBER_SEQ_GENERATOR")
    //TABLE: 키 생성용 테이블 사용, 모든 DB에서 사용 @TableGenerator 필요
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //AUTO: 방언에 따라 자동 지정, 기본값
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /** 객체필드와 db 컬럼 매핑
     * @Column
     * 속성 정리
     * - name 필드와 매핑할 테이블의 컬럼 이름
     * - insertable,* updatable 등록, 변경 가능 여부
     * - nullable(DDL) null 값의 허용 여부를 설정한다. false로 설정하면 DDL 생성 시에   not null 제약조건이 붙는다.
     * - unique(DDL) @Table의 uniqueConstraints와 같지만 한 컬럼에 간단히 유니크 제약조건을 걸 때 사용한다
     * - columnDefinition (DDL) : 데이터베이스 컬럼 정보를 직접 줄 수 있다. ex) varchar(100) default ‘EMPTY'
     * - length(DDL) : 문자 길이 제약조건, String 타입에만 사용한다.
     * - precision, scale(DDL) : BigDecimal 타입에서 사용한다(BigInteger도 사용할 수 있다).
     *   --precision은 소수점을 포함한 전체 자 릿수를, scale은 소수의 자릿수다.
     *   --참고로 double, float 타입에는 적용되지 않는다. 아주 큰 숫자나 정밀한 소수를 다루어야 할 때만 사용한다.
     */
    @Column(name = "name", insertable = true, updatable = true, nullable = true ,unique = false,length=100)
    private String name;

    private Integer age;

    /** 열거형 매핑
     * EnumType.ORDINAL: enum 순서를 데이터베이스에 저장
     * EnumType.STRING: enum 이름을 데이터베이스에 저장
     * Enumerate type의 경우 String으로 사용
     */
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    /** 날짜 매핑
     * TemporalType.DATE: 날짜, 데이터베이스 date 타입과 매핑
     * (예: 2013–10–11)
     * • TemporalType.TIME: 시간, 데이터베이스 time 타입과 매핑
     * (예: 11:11:11)
     * • TemporalType.TIMESTAMP: 날짜와 시간, 데이터베이스  timestamp 타입과 매핑
     * (예: 2013–10–11 11:11:11)
     **/
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    /**
     *  @Lob BLOB, CLOB 매핑
     */
    @Lob
    private String description;

    /**
     *  @Transient 특정 필드를 컬럼에 매핑하지 않음(매핑 무시) : db와 무관하게 연산에만 필요한 변수에 선언
     */
    @Transient
    private String temp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

}
