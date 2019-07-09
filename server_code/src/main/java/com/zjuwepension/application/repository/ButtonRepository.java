package com.zjuwepension.application.repository;

import com.zjuwepension.application.entity.Button;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ButtonRepository extends JpaRepository<Button, Long> {
}