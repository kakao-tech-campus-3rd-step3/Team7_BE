package com.careerfit.application.repository;

import com.careerfit.application.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationJpaRepository extends JpaRepository<Application, Long> {

}
