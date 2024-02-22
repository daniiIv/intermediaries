package com.example.intermediaries.repositories;

import com.example.intermediaries.entities.registrations.ParentIntermediaries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentIntermediariesRepository extends JpaRepository<ParentIntermediaries, Integer> {
}
