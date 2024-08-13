package com.gaplog.server.domain.comment.domain;

import com.gaplog.server.domain.post.domain.Post;
import com.gaplog.server.domain.user.domain.User;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "parent_id", nullable = false)
    private Comment parent;

    @Column(name = "like_count", nullable = false)
    private int like_count = 0;


    //like_count 는 초기값이 명확하게 설정되어 있기 때문에 생성자에서 별도로 지정X
    @Builder
    public Comment(Long id, Post post, User user, String text, Comment parent) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.text = text;
        this.parent = parent;
    }
}
