package com.example.intermediaries.intermediaries.childrenIntermediaries;

import com.example.intermediaries.intermediaries.childrenIntermediaries.ChildrenIntermediaries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildrenIntermediariesRepository extends JpaRepository<ChildrenIntermediaries, Integer> {
}
