package com.example.intermediaries.intermediaries;

import com.example.intermediaries.intermediaries.IntermediaryTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntermediaryTypesRepository extends JpaRepository<IntermediaryTypes, Integer> {
}
