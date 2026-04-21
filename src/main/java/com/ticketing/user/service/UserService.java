package com.ticketing.user.service;

import com.ticketing.global.error.ApiException;
import com.ticketing.global.error.ErrorCode;
import com.ticketing.user.domain.User;
import com.ticketing.user.domain.UserRepository;
import com.ticketing.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse me(String principalUserId) {
        Long userId = Long.valueOf(principalUserId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
        return new UserResponse(user.getId(), user.getEmail(), user.getRole());
    }
}
