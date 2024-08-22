package com.gaplog.server.domain.comment.domain;

import com.gaplog.server.domain.post.domain.Post;
import com.gaplog.server.domain.user.domain.User;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "like_count", nullable = false)
    private int likeCount = 0;

    @ColumnDefault("FALSE")
    @Column(nullable = false)
    private Boolean isDeleted;

    @ColumnDefault("FALSE")
    @Column(nullable = false)
    private Boolean isDeletedParent;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ElementCollection
    @CollectionTable(name = "comment_likes", joinColumns = @JoinColumn(name = "comment_id"))
    @Column(name = "user_id")
    private Set<Long> likedByUsers = new HashSet<>();

    @Version // Optimistic Locking을 위한 버전 필드
    private Long version;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public void setDeletedParent(Boolean deletedParent) {
        isDeletedParent = deletedParent;
    }

    public void toggleLike(Long userId) {
        if (likedByUsers.contains(userId)) {
            likedByUsers.remove(userId); // 좋아요 취소
            likeCount--; // 좋아요 수 감소
        } else {
            likedByUsers.add(userId); // 좋아요 추가
            likeCount++; // 좋아요 수 증가
        }
    }

    @Builder
    public Comment(Post post, User user, String text, Long parentId) {
        this.post = post;
        this.user = user;
        this.text = text;
        this.parentId = parentId;
        this.likeCount = 0;
    }

    public static Comment of(Post post, User user, String text, Long parentId) {
        return Comment.builder()
                .post(post)
                .user(user)
                .text(text)
                .parentId(parentId)
                .build();
    }

}