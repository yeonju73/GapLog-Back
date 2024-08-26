package com.gaplog.server.domain.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "introduce")
    private String introduce;

    @Column(name = "profile_image")
    private String profileImg;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Setter
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Seriousness seriousness;

    // Category Test를 위해 임시로 추가, Oauth로그인 구현 시 변경
    public User(Long l, String user) {
        this.id = l;
        this.nickName = user;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void updateNickName(String nickName) {
        this.nickName = nickName;
        this.onUpdate();
    }

    public void updateIntroduce(String introduce) {
        this.introduce = introduce;
        this.onUpdate();
    }

    public void updateProfileImg(String profileImg) {
        this.profileImg = profileImg;
        this.onUpdate();
    }
}
