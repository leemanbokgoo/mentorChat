package com.example.metoChat.domain.Reviews;

import com.example.metoChat.domain.metoring.Mentoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewsRepository  extends JpaRepository<Reviews, Long> {
}
