package com.example.metoChat.domain.metoring;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MentoringRepository extends JpaRepository<Mentoring, Long> ,MentoringRepositoryCustom{
}
