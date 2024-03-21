package com.example.metoChat.domain.mentor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

public interface MentorRepository extends JpaRepository<Mentor, Long>,MentorRepositoryCustom {

    // 멘토 존재 여부
    Optional<Mentor> findByIdAndState(Long id, boolean State);

}