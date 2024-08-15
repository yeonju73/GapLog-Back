package com.gaplog.server.domain.user.application;

import com.gaplog.server.domain.user.dao.UserRepository;
import com.gaplog.server.domain.user.domain.User;
import com.gaplog.server.domain.user.dto.request.UserUpdateRequest;
import com.gaplog.server.domain.user.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly=true)
    public UserResponse getUserInfo(Long userId){
        User getUserInfo = userRepository.findById(userId).orElseThrow(()
                -> new IllegalArgumentException("User not found with id: " + userId));
        return UserResponse.of(getUserInfo);
    }

    @Transactional
    public UserResponse updateUser(Long userId, UserUpdateRequest userUpdateRequestDTO) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new IllegalArgumentException("Invalid user ID: " + userId));

        user.updateNickName(userUpdateRequestDTO.getNickName());
        user.updateIntroduce(userUpdateRequestDTO.getIntroduce());
        user.updateProfileImg(userUpdateRequestDTO.getProfileImg());

        User updateUser = userRepository.save(user);

        return UserResponse.of(updateUser);
    }

}
