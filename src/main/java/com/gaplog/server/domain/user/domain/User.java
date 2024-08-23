package com.gaplog.server.domain.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    // false 가 삭제되지 않은 상태 (기본값)
    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @Setter
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Seriousness seriousness;

    public User(String nickName, String introduce, String profileImg) {
        this.nickName = nickName;
        this.introduce = introduce;
        this.profileImg = profileImg;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        Seriousness seriousness = new Seriousness(this);
        seriousness.setUser(this);
        this.setSeriousness(seriousness);
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

    public void deleteUser() {
        this.deleted = true;
        this.onUpdate();
    }
}
