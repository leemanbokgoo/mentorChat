package com.example.metoChat.domain.mentorTime;

import com.example.metoChat.domain.mentor.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorTimeRepository extends JpaRepository<MentorTime, Long> {
}
