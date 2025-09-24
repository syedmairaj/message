package com.message.Repository;

import com.message.Entity.SMSEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SMSEntityRepository extends JpaRepository<SMSEntity, Long> {
}