package com.ticketing.member.repository;

import com.ticketing.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Page<Member> findByLoginIdContainingIgnoreCase(
            String loginId,
            Pageable pageable
    );

    Optional<Member> findByLoginId(String loginId);
}