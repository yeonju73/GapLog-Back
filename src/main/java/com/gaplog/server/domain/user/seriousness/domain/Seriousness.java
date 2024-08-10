package com.gaplog.server.domain.user.seriousness.domain;

import com.gaplog.server.domain.user.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Seriousness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @JoinColumn(name = "user_id", nullable = false)
    private int user;

    @Column(name = "tier", nullable = false)
    private int tier;

    // 진지 기록

}
