package com.pskjeong.psk.members.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Members {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "member_email")
    private String memberEmail;

    @Column(name = "member_name")
    private String memberName;

    @Builder
    public Members(Long id, String memberEmail, String memberName) {
        this.id = id;
        this.memberEmail = memberEmail;
        this.memberName = memberName;
    }
}
