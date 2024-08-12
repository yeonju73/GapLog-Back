package com.gaplog.server.domain.caterory.domain;

import com.gaplog.server.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(nullable = false)
    private String name;

    @Builder
    public Category(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public static Category createCategory(String name, User user) {
        return Category.builder()
                .name(name)
                .user(user)
                .build();
    }
}
