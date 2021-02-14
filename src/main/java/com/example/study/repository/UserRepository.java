package com.example.study.repository;

import com.example.study.model.entity.User;
import org.graalvm.compiler.nodes.calc.IntegerDivRemNode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityListeners;
import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User,Long> {

    /*
    Optional<User> findByAccount(String account);

    Optional<User> findByEmail(String email);

    Optional<User> findByAccountAndEmail(String account, String email);

     */

    User findFirstByPhoneNumberOrderByIdDesc(String phoneNumber);
}
