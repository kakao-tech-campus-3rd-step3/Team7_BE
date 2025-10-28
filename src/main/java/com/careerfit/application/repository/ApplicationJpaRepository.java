package com.careerfit.application.repository;

import com.careerfit.application.domain.Application;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ApplicationJpaRepository extends JpaRepository<Application, Long> {

    List<Application> findByMemberIdOrderByUpdatedDateDesc(Long memberId, Pageable pageable);

    List<Application> findByMemberIdAndUpdatedDateBeforeOrderByUpdatedDateDesc(Long memberId,
        LocalDateTime updatedDate, Pageable pageable);
}
