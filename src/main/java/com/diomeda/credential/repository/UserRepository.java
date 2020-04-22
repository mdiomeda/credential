package com.diomeda.credential.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.diomeda.credential.model.UserAccount;


@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long> {

    UserAccount findByName(String name);

}
