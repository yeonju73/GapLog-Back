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
        if (seriousnessCount >= 3) {
            this.tier = 3;
        } else if (seriousnessCount == 2) {
            this.tier = 2;
        } else {
            this.tier = 1;
        }
        this.localDate = LocalDate.now();
    }

    public Seriousness(User user) {
        this.user = user;
        this.seriousnessCount = 0;
        this.tier = 1;
    }

}
