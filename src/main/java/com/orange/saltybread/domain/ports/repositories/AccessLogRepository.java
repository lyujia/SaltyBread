package com.orange.saltybread.domain.ports.repositories;

import com.orange.saltybread.domain.aggregates.accessLogs.AccessLog;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessLogRepository extends JpaRepository<AccessLog, UUID> {

}
