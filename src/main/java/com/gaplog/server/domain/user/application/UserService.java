package com.gaplog.server.domain.user.application;

import com.gaplog.server.domain.user.dao.UserRepository;
import com.gaplog.server.domain.user.domain.User;
import com.gaplog.server.domain.user.dto.request.UserUpdateRequestDTO;
import com.gaplog.server.domain.user.dto.response.UserResponseDTO;
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

    @Transactional
    public  UserResponseDTO getUserInfo(Long userId){
        User getUserInfo = userRepository.findById(userId).orElseThrow(()
                -> new IllegalArgumentException("Invalid user ID: " + userId));
        return UserResponseDTO.of(getUserInfo);
    }

    @Transactional
    public UserResponseDTO updateUser(Long userId, UserUpdateRequestDTO userUpdateRequestDTO) {
            User user = userRepository.findById(userId).orElseThrow(()
            -> new IllegalArgumentException("Invalid user ID: " + userId));

        user.updateNickName(userUpdateRequestDTO.getNickName());
        user.updateIntroduce(userUpdateRequestDTO.getIntroduce());
        user.updateProfileImg(userUpdateRequestDTO.getProfileImg());

        User updateUser = userRepository.save(user);
        return UserResponseDTO.of(updateUser);
    }
}
