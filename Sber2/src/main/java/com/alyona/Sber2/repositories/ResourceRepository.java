package com.alyona.Sber2.repositories;

import com.alyona.Sber2.models.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Optional<Resource> findById(Long id);
}