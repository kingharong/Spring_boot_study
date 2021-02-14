package com.example.study.repository;

import com.example.study.component.LoginUserAuditorAware;
import com.example.study.config.JpaConfig;
import com.example.study.model.entity.AdminUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({JpaConfig.class, LoginUserAuditorAware.class})
// 이거 없으면 테스트 오류 남,,,
public class AdminUserRepositoryTest {
    @Autowired
    AdminUserRepository adminUserRepository;

    @Test
    public void create(){
        AdminUser adminUser = new AdminUser();
        adminUser.setAccount("adminuser1");
        adminUser.setPassword("ad");
        adminUser.setStatus("REGISTERED");
        adminUser.setRole("admin");
      //  adminUser.setCreatedAt(LocalDateTime.now());
        //adminUser.setCreatedBy("ds");

        AdminUser adminUser1 = adminUserRepository.save(adminUser);
        Assertions.assertNotNull(adminUser1);
    }
}
