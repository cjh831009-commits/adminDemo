package com.admin.iam.adapter.out.persistence.repository;

import com.admin.iam.adapter.out.persistence.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminUserJpaRepository extends JpaRepository<AdminUser, Long> {
    Optional<AdminUser> findByAdminId(String adminId);
}
