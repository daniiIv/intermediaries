package com.example.intermediaries.intermediaries.parentIntermediaries;

import com.example.intermediaries.intermediaries.parentIntermediaries.ParentIntermediaries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentIntermediariesRepository extends JpaRepository<ParentIntermediaries, Integer> {
}
