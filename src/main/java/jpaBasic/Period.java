package jpaBasic;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

//@Embedded: 값 타입을 사용하는 곳에 표시
@Embeddable
public class Period {

    public Period(){

    }

    public Period(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // 아래 속성을 period 기간으로 묶는다.
    private LocalDateTime startDate;
    private LocalDateTime endDate;


}
