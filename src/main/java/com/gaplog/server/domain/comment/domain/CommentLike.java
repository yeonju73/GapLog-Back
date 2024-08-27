package com.gaplog.server.domain.comment.domain;

import com.gaplog.server.domain.post.domain.Post;
import com.gaplog.server.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.ConnectionBuilder;

@Getter
@NoArgsConstructor
@Entity
@Table(
        name = "comment_like",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "comment_id"})
        }
)
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Builder
    public CommentLike(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }

    public static CommentLike of(User user, Comment comment) {
        return CommentLike.builder()
                .user(user)
                .comment(comment)
                .build();
    }
}
