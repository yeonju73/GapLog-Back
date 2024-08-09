package com.gaplog.server.domain.user.post_scarp.domain;

import com.gaplog.server.domain.post.domain.Post;
import com.gaplog.server.domain.user.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Getter
@Entity
@NoArgsConstructor // 파라미터가 없는 디폴트 생성자를 자동으로 생성
public class PostScrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
    private Long id;


    @JoinColumn(name = "user_id", nullable = false)
    private int user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;


    @JoinColumn(name = "post_id", nullable = false)
    private int post;


}
