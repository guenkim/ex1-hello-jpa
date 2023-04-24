package jpaBasic;

import javax.persistence.Embeddable;
import java.util.Objects;


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
// • @Embeddable: 값 타입을 정의하는 곳에 표시
// • @Embedded: 값 타입을 사용하는 곳에 표시
@Embeddable
public class Address {
    public Address(){

    }

    public Address(String city, String street, String address) {
        this.city = city;
        this.street = street;
        this.address = address;
    }

    // 아래 속성을 address 주소로 묶는다.
    private String city;
    private String street;
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return Objects.equals(city, address1.city) && Objects.equals(street, address1.street) && Objects.equals(address, address1.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, address);
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getAddress() {
        return address;
    }
}
