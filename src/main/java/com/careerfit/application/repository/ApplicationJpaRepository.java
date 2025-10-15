package com.careerfit.application.repository;

import com.careerfit.application.domain.Application;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationJpaRepository extends JpaRepository<Application, Long> {

    List<Application> findByMemberIdOrderByUpdatedDateDesc(Long memberId, Pageable pageable);

    List<Application> findByMemberIdAndUpdatedDateBeforeOrderByUpdatedDateDesc(Long memberId,
        LocalDateTime updatedDate, Pageable pageable);
}
