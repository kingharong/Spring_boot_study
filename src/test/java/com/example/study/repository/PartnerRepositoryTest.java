package com.example.study.repository;

import com.example.study.component.LoginUserAuditorAware;
import com.example.study.config.JpaConfig;
import com.example.study.model.entity.Partner;
import com.example.study.model.enumclass.PartnerStatus;
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
public class PartnerRepositoryTest {
    @Autowired
    private PartnerRepository partnerRepository;

    @Test
    public void create(){
        String name="Partner01";
        PartnerStatus status = PartnerStatus.REGISTERED;
        String address = "서울시 강남구";
        String callCenter = "070-2222-2222";
        String partnerNumber = "010-2222-2222";
        String businessNumber ="132423";
        String ceoName="홍";
        LocalDateTime registeredAt=LocalDateTime.now();
        LocalDateTime createdAt =LocalDateTime.now();
        String createdBy="AdminServer";

        Partner partner = new Partner();
        partner.setName(name);
        partner.setStatus(status);
        partner.setAddress(address);
        partner.setCallCenter(callCenter);
        partner.setPartnerNumber(partnerNumber);
        partner.setBusinessNumber(businessNumber);
        partner.setCeoName(ceoName);
        partner.setRegisteredAt(registeredAt);
        partner.setCreatedAt(createdAt);
        partner.setCreatedBy(createdBy);
       // partner.setCategoryId(1L);

        Partner newPartner = partnerRepository.save(partner);
        Assertions.assertNotNull(newPartner);
        Assertions.assertEquals(newPartner.getAddress(),address);

    }
}
