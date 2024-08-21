package com.gaplog.server.domain.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nickname", nullable = false)
    private String nickName;

    @Column(name = "introduce")
    private String introduce;

    @Column(name = "profile_image")
    private String profileImg;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Seriousness seriousness;

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
