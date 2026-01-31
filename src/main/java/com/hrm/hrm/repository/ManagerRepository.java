package com.hrm.hrm.repository;

import com.hrm.hrm.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Manager findByEmail(String email);
    Manager findByResetToken(String resetToken);
}


