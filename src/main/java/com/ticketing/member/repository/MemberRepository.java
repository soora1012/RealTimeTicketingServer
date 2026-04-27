package com.ticketing.member.repository;

import com.ticketing.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Page<Member> findByMemberIdContainingIgnoreCase(
            String memberId,
            Pageable pageable
    );

}