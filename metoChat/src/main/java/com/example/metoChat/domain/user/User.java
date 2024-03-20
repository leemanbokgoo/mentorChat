package com.example.metoChat.domain.user;

import com.example.metoChat.domain.BaseTimeEntity;
import com.example.metoChat.domain.mentor.Mentor;
import com.example.metoChat.domain.Reviews.Reviews;
import com.example.metoChat.domain.metoring.Mentoring;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "users")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500, nullable = false, unique = true)
    private String email;

    @Column(length = 500, nullable = false)
    private String password;

    @Column(length = 500)
    private String profileImg;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // User <- Profiles
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Mentor mentor;

    // User <- mentorring
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mentoring> mentorrings = new ArrayList<>();

    // User <- Reviews
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reviews> reviews = new ArrayList<>();

    @Builder
    public User(String name, String email, Role role, String password ) {
        // 비밀번호 암호화
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        this.name = name;
        this.email = email;
        this.password = hashedPassword;
        this.role = role;
    }

    public User update( String name ) {
        this.name = name;
        return this;
    }

    public User updateProfileImg(String profileImg) {
        this.profileImg =profileImg;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
