package com.ticketing.member.service;


import com.ticketing.global.pagination.PageResponse;
import com.ticketing.member.domain.Member;
import com.ticketing.member.dto.MemberResponse;
import com.ticketing.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public PageResponse<MemberResponse> getMemberList(int page, int size, String keyword) {

        if(page < 1) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(
                page-1,
                size,
                Sort.by(Sort.Direction.ASC, "memberPk")
        );
        Page<Member> memberPage;

        if (keyword == null || keyword.trim().isEmpty()) {
            memberPage = memberRepository.findAll(pageable);
        } else {
            memberPage = memberRepository.findByLoginIdContainingIgnoreCase(
                    keyword.trim(),
                    pageable
            );
        }

        Page<MemberResponse> responsePage = memberPage.map(MemberResponse::from);

        return PageResponse.from(responsePage);
    }

}
