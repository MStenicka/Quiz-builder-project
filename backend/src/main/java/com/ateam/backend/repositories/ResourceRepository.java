package com.ateam.backend.repositories;


import com.ateam.backend.models.quiz.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByDeletedFalse();
}
