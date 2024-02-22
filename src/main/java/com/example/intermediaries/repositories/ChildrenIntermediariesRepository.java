package com.example.intermediaries.repositories;

import com.example.intermediaries.entities.registrations.ChildrenIntermediaries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildrenIntermediariesRepository extends JpaRepository<ChildrenIntermediaries, Integer> {
}
