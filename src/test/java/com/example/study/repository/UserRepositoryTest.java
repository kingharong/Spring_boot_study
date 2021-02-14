package com.example.study.repository;


import com.example.study.component.LoginUserAuditorAware;
import com.example.study.config.JpaConfig;
import com.example.study.model.entity.User;

import com.example.study.model.enumclass.UserStatus;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({JpaConfig.class, LoginUserAuditorAware.class})

public class UserRepositoryTest  {


    @Autowired
    private  UserRepository userRepository;
    @Test
    public void create(){
        /*
    User user = new User();
    user.setAccount("testuser03");
    user.setEmail("testuser03@email.com");
    user.setPhoneNumber("010-3333-1111");
    user.setCreatedAt(LocalDateTime.now());
    user.setCreatedBy("testuser3");
    User newUser =userRepository.save(user);
    System.out.println(newUser);

         */
        User user=new User();
        String account="test01";
        String password="test01";
        UserStatus status = UserStatus.REGISTERED;
        String email="32";
        String phoneNumber="010-2222-2222";
        LocalDateTime registeredAt=LocalDateTime.now();
  //      LocalDateTime createdAt =LocalDateTime.now();
       // String createdBy="AdminServer";

        user.setAccount(account);
        user.setPassword(password);
        user.setStatus(status);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRegisteredAt(registeredAt);
      //  user.setCreatedAt(createdAt);
        //user.setCreatedBy(createdBy);
        User newUser = userRepository.save(user);
        Assertions.assertNotNull(newUser);
        Assertions.assertEquals(newUser.getAccount(),account);

        User u=User.builder().account(account).email(email).password(password).build();
    }
    @Test
    @Transactional
    //public User read(@RequestParam Long id)
    public void read(){

        User user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-2222-2222");
        Assertions.assertNotNull(user);

        user.getOrderGroup().stream().forEach(orderGroup -> {
            System.out.println(orderGroup.getOrderAt());
            orderGroup.getOrderDetailList().forEach(orderDetail -> {
                System.out.println(orderDetail.getArrivalDate());
                System.out.println(orderDetail.getItem().getName());
                System.out.println(orderDetail.getItem().getPartner().getAddress());
                System.out.println(orderDetail.getItem().getPartner().getCategory().getTitle());
            });
        });
        // select * from user where id=?
    //Optional<User> user =userRepository.findByAccount("testuser03");
/*
    user.ifPresent(selectUser-> {
        selectUser.getOrderDetailList().stream().forEach(detail-> {
            Item item = detail.getItem();  // item_id
            System.out.println(item);
        });

    });

 */
    //return user.get()
    }
    @Test
    public void update(){
        Optional<User> user =userRepository.findById(2L);
        user.ifPresent(selectUser-> {
            selectUser.setAccount("pppp");
            selectUser.setUpdatedAt(LocalDateTime.now());
            selectUser.setUpdatedBy("update method()");
            userRepository.save(selectUser);
        });

    }
    @Test
    @Transactional
    //@DeleteMapping("/api/user")
    public void delete(){ //@RequestParam Long id
        Optional<User> user =userRepository.findById(3L);
        Assert.assertTrue(user.isPresent());

        user.ifPresent(selectUser-> {
            userRepository.delete(selectUser);
        });
        Optional<User> deleteuser =userRepository.findById(3L);
        Assert.assertFalse(deleteuser.isPresent());
    }
}
