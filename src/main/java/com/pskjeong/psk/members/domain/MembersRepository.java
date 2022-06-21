package com.pskjeong.psk.members.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MembersRepository extends JpaRepository<Members, Long> {
    boolean existsByMemberEmail(String email);
}
