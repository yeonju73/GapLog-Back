package com.gaplog.server.domain.user.domain;

import com.gaplog.server.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor
@Table(
        name = "user_relationships",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"follower_id", "followee_id"})
        }
)

@Entity
public class UserRelationships {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followee_id", nullable = false)
    private User followee;

    // 낙관적 lock
    @Version
    private Integer version;


    public UserRelationships(User follower, User followee) {
        this.follower = follower;
        this.followee = followee;
    }

}
