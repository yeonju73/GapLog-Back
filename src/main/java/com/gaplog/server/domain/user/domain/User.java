package com.gaplog.server.domain.user.domain;

import com.gaplog.server.domain.auth.domain.oauth.OauthProvider;
import com.gaplog.server.domain.category.domain.Category;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "nickname")
    private String nickName;

    private String name;

    private String email;

    @Column(name = "o_auth_provider")
    private OauthProvider oauthProvider;

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

    @Builder
    public User(String name, String email, OauthProvider oauthProvider, String introduce, String profileImg) {
        this.name = name;
        this.email = email;
        this.oauthProvider = oauthProvider;
        this.introduce = introduce;
        this.profileImg = profileImg;
    }

    public static User of(String name, String email, OauthProvider oauthProvider, String profileImg) {
        return User.builder()
                .name(name)
                .email(email)
                .oauthProvider(oauthProvider)
                .profileImg(profileImg)
                .build();
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
