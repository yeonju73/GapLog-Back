package com.gaplog.server.domain.post.domain;

import com.gaplog.server.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post {

    //pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //fk
    @ManyToOne // Many = Post, User = One 한명의 유저는 여러개의 게시글을 쓸 수 있다.
    @JoinColumn(name="user_id") // foreign key (user_id) references User (id)
    private User user;  //참조할 테이블


    //content
    @Column(length = 100)
    private String title;

    @Column(length = 100)
    private String content;

    @Column(length = 45)
    private String category;

    @Column(length = 100)
    private String thumbnailUrl;


    //time
//    @Column(length = 100)
//    @CreationTimestamp
//    private String created_at;
//
//    @Column(length = 100)
//    @UpdateTimestamp
//    private String updated_at;


    //time
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    //count
    @Column
    private int likeCount;

    @Column
    private int jinjiCount;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    public void updateTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateContent(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

//    public void updateCategory(String category) {
//        this.category = category;
//        this.updatedAt = LocalDateTime.now();
//    }
//
//    public void updateThumbnail(String thumbnailUrl) {
//        this.thumbnailUrl = thumbnailUrl;
//        this.updatedAt = LocalDateTime.now();
//    }

    public void updateLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void updateJinjiCount(int jinjiCount) {
        this.jinjiCount = jinjiCount;
    }
}
