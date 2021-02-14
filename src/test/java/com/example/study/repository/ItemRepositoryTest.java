package com.example.study.repository;


import com.example.study.component.LoginUserAuditorAware;
import com.example.study.config.JpaConfig;
import com.example.study.model.entity.Item;
import com.example.study.model.enumclass.ItemStatus;
import com.example.study.model.network.Header;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest  // jpa 테스트 관련 컴포넌트만 임포트
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //실제 디비 사용
@Import({JpaConfig.class, LoginUserAuditorAware.class})
public class ItemRepositoryTest  {
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void create(){
        Item item=new Item();
        item.setName("노트북");
        item.setContent("삼성 노트북");
        item.setStatus(ItemStatus.UNREGISTERED);
        item.setContent("2019년형 노트북");
        item.setPrice(BigDecimal.valueOf(900000.0000));
        item.setBrandName("삼성");
        item.setRegisteredAt(LocalDateTime.now());
        item.setCreatedAt(LocalDateTime.now());
        item.setCreatedBy("partner01");
       // item.setPartnerId(1L);
        item.setTitle("삼성 놑북");
        Item newItem = itemRepository.save(item);
        Assertions.assertNotNull(newItem);
    }
@Test
    public void read() {
        Long id=1L;
        Optional<Item> item = itemRepository.findById(id);
        Assertions.assertTrue(item.isPresent());
}

}
