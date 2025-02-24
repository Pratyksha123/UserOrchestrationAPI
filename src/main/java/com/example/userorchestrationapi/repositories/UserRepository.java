package com.example.userorchestrationapi.repositories;

import com.example.userorchestrationapi.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    //@Query("SELECT e FROM User e WHERE e.firstName LIKE CONCAT(:prefix, '%') OR e.lastName LIKE CONCAT(:prefix, '%') OR e.ssn LIKE CONCAT(:prefix, '%')")
    @Query("SELECT e FROM User e WHERE LOWER(e.firstName) LIKE LOWER(CONCAT(:prefix, '%')) OR LOWER(e.lastName) LIKE LOWER(CONCAT(:prefix, '%')) OR e.ssn LIKE CONCAT(:prefix, '%')")
    List<User> searchByNameOrSsnPrefix(@Param("prefix") String prefix);


}
