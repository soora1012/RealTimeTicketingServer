package com.ticketing.member.controller;

import com.ticketing.global.api.ApiResponse;
import com.ticketing.global.pagination.PageResponse;
import com.ticketing.member.dto.MemberResponse;
import com.ticketing.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/list")
    public ApiResponse<PageResponse<MemberResponse>> getMemberList(@RequestParam(defaultValue = "1") int page,
                                                                   @RequestParam(defaultValue = "10") int size,
                                                                   @RequestParam(required = false) String keyword) {

        PageResponse<MemberResponse> response = memberService.getMemberList(page, size, keyword);
        return ApiResponse.ok(response);
    }
}
