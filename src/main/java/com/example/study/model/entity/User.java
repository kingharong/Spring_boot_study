package com.example.study.model.entity;

import com.example.study.model.enumclass.UserStatus;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity //==table
//@Table(name="user") 이름 같으면 선언 생략해도 됨
@ToString(exclude = {"orderGroup"})
@EntityListeners(AuditingEntityListener.class)
@Builder   // 생성자
@Accessors(chain = true) // setter
public class User {

   // @Column(name="account") 이름 같으면 선언 생략
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String account;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private String email;
    private String phoneNumber; //디비는 스네이크 케이스 - 자바는 카멜 케이스 매칭
    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "user")
    private List<OrderGroup> orderGroup;

    /*
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user") //오더디테일 변수 이름과 동일
    // lazy= 지연 로딩, eager= 즉시 로딩
    // lazy (따로 get 메서드를 호출하지 않는 이상 아래와 같은 조인이 미리 일어나지 않는다) 추천
    // select * from user where id =?
    // eager 1:1일 때 추천
    // user_id =order_detail.user_id 조인
    // item_id= order_datail.item_id 조인
    // where user_id=?
    private List<OrderDetail> orderDetailList;

     */
}
