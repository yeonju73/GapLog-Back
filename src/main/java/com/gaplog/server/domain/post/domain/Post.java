package com.gaplog.server.domain.post.domain;

import com.gaplog.server.domain.caterory.domain.Category;
import com.gaplog.server.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
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

    @ManyToOne // Many = Post, Category = One 한개의 카테고리는 여러개의 게시글을 포함할 수 있다.
    @JoinColumn(name="category_id") // foreign key (category_id) references Category (id)
    private Category category;

    //content
    @Column
    private String title;

    @Column(length = 10000)
    private String content;

//    @Column(length = 45)
//    private String category;

    @Column
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

    @Column
    private int scrapCount;


    @Column
    private boolean isPrivate;

    @Column
    private boolean isDeleted;


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

    public void updateCategory(Category category) {
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }

//    public void updateThumbnail(String thumbnailUrl) {
//        this.thumbnailUrl = thumbnailUrl;
//        this.updatedAt = LocalDateTime.now();
//    }


    //반응 수 증가
    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void increaseJinjiCount() {this.jinjiCount++;}

    public void increaseScrapCount() {
        this.scrapCount++;
    }

    //반응 수 증가
    public void decreaseLikeCount() {
        this.likeCount--;
    }

    public void decreaseJinjiCount() {
        this.jinjiCount--;
    }

    public void decreaseScrapCount() {
        this.scrapCount--;
    }


    public void updateIsPrivate() {
        this.isPrivate = true;
    }

    public void updateIsDeleted() {
        this.isDeleted = true;
    }

    @Builder
    public Post(String title, String content, Category category, String thumbnailUrl, User user) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.thumbnailUrl = thumbnailUrl;
        onCreate();
        this.likeCount = 0;
        this.jinjiCount = 0;
        this.scrapCount = 0;
        this.isPrivate = false;
        this.isDeleted = false;
        this.user = user;
    }

    public static Post of(String title, String content, Category category, String thumbnailUrl, User user) {
        return Post.builder()
                .title(title)
                .content(content)
                .category(category)
                .thumbnailUrl(thumbnailUrl)
                .user(user)
                .build();
    }
}
