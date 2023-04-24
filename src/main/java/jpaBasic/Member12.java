package jpaBasic;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Member12 {

    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;
    private String name;

    //embedded type 적용
    //• @Embeddable: 값 타입을 정의하는 곳에 표시
    //• @Embedded: 값 타입을 사용하는 곳에 표시
    @Embedded
    private Period period;

    //embedded type 적용
    @Embedded
    //@Embedded: 값 타입을 사용하는 곳에 표시
    private Address address;

    //Address Ebedded Type을 다시 사용하려고 할 때는 아래와 같이 활용
    /**
     * @AttributeOverride: 속성 재정의
     * 한 엔티티에서 같은 값 타입을 사용하면?
     *      컬럼 명이 중복됨
     *  @AttributeOverrides, @AttributeOverride를 사용해서
     *      컬러 명 속성을 재정의
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="city",column = @Column(name="work_city")),
            @AttributeOverride(name="street",column = @Column(name="work_street")),
            @AttributeOverride(name="address",column = @Column(name="work_address"))
    })
    private Address workAddress;



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

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
