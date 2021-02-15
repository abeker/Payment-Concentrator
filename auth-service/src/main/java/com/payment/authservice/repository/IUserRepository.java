package com.payment.authservice.repository;

import com.payment.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {

    User findOneById(UUID id);

    User findBySecret(String secret);

    List<User> findAllByDeleted(boolean deleted);

}