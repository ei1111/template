package com.app.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(value = {AuditingEntityListener.class})
//부모클래스는 테이블이랑 매핑하지 않고 부모 클래스를 상속받는 자식 클래스에 매핑 정보를 제공할때 사용한다.
//자식 클래스에 createTime, updateTime을 매핑 정보로 제공한다는것이다.
@MappedSuperclass
public abstract class BaseTimeEntity {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createTime;

    @LastModifiedDate
    private LocalDateTime updateTime;

}
