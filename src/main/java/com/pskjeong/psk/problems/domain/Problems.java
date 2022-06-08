package com.pskjeong.psk.problems.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Problems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int platform;
    @Column(nullable = false)
    private int problemNum;
    @Column(nullable = false)
    private String title;

    private String rank;

    @Builder
    public Problems(Long id, int platform, int problemNum, String title, String rank) {
        this.id = id;
        this.platform = platform;
        this.problemNum = problemNum;
        this.title = title;
        this.rank = rank;
    }

}
