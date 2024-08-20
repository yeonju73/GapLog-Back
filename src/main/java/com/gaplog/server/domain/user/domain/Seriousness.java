package com.gaplog.server.domain.user.domain;

import com.gaplog.server.domain.post.domain.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "seriousness",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "post_id"})
        }
)
public class Seriousness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "tier", nullable = false)
    private int tier;

    @Column(name = "date")
    private LocalDate localDate;

    @Column(name = "seriousness_count")
    private int seriousnessCount;

    public Seriousness(User user, Post post, int tier) {
        this.user = user;
        this.tier = tier;
        this.localDate = LocalDate.now();
    }

    public void updateTier(int seriousnessCount) {
        if (seriousnessCount >= 100) {
            this.tier = 6; // 브론즈 티어
        } else if (seriousnessCount >= 80) {
            this.tier = 5; // 실버 티어
        } else if (seriousnessCount >= 60) {
            this.tier = 4; // 골드 티어
        } else if (seriousnessCount >= 40) {
            this.tier = 3; // 플레 티어
        } else if (seriousnessCount >= 20) {
            this.tier = 2; // 다이어 티어
        } else {
            this.tier = 1; // 루비 티어
        }
        this.localDate = LocalDate.now();
    }

    public Seriousness(User user) {
        this.user = user;
        this.seriousnessCount = 0;
        this.tier = 1;
    }

}
