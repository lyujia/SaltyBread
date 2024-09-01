package com.orange.saltybread.domain.ports.repositories;

import com.orange.saltybread.domain.aggregates.users.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByEmail(String username);

  @Query(value = "SELECT u FROM User u WHERE u.email in (:emails)")
  List<User> findByEmailIn(@Param("emails") List<String> emails);
}
