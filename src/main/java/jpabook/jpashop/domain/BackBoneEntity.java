package jpabook.jpashop.domain;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;




/*******************************************************
 @MappedSuperclass : 예제 BaseEntity class
 • 공통 매핑 정보가 필요할 때 사용(id, name)
 상속관계 매핑X
 • 엔티티X, 테이블과 매핑X
 • 부모 클래스를 상속 받는 자식 클래스에 매핑 정보만 제공
 • 조회, 검색 불가(em.find(BaseEntity) 불가)
 • 직접 생성해서 사용할 일이 없으므로 추상 클래스 권장
 ******************************************************/
@MappedSuperclass
public abstract class BackBoneEntity {
    private String createBy;
    private LocalDateTime createdDate;
    private String modifiedBy;
    private LocalDateTime modifiedDate;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
