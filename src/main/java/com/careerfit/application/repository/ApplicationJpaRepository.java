package com.careerfit.application.repository;

import com.careerfit.application.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationJpaRepository extends JpaRepository<Application, Long> {

    List<Application> findAllByMemberId(Long memberId);

}
