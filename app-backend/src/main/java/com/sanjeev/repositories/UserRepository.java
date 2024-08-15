package com.sanjeev.repositories;

import com.sanjeev.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);

    @Query("SELECT u FROM AppUser u WHERE u.isDeleted = false")
    List<AppUser> findAllActiveUsers();

    @Query("SELECT u FROM AppUser u WHERE " +
            "(LOWER(u.first_name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(u.last_name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
            "u.isDeleted = false")
    List<AppUser> searchByQuery(@Param("query") String query);
}
