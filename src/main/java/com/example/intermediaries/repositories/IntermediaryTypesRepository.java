package com.example.intermediaries.repositories;

import com.example.intermediaries.entities.intermediariesinfo.IntermediaryTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntermediaryTypesRepository extends JpaRepository<IntermediaryTypes, Integer> {
}
