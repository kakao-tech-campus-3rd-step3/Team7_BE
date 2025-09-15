package com.careerfit.application.repository;

import com.careerfit.application.domain.Application;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationJpaRepository extends JpaRepository<Application, Long> {

    List<Application> findAllByMemberId(Long memberId);

}
