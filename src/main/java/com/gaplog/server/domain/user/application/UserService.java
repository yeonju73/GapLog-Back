package com.gaplog.server.domain.user.application;

import com.gaplog.server.domain.comment.dao.CommentRepository;
import com.gaplog.server.domain.comment.domain.Comment;
import com.gaplog.server.domain.comment.dto.response.CommentResponse;
import com.gaplog.server.domain.post.dao.PostRepository;
import com.gaplog.server.domain.post.domain.Post;
import com.gaplog.server.domain.post.dto.response.MainPostResponse;
import com.gaplog.server.domain.user.dao.SeriousnessRepository;
import com.gaplog.server.domain.user.dao.UserRepository;
import com.gaplog.server.domain.user.domain.Seriousness;
import com.gaplog.server.domain.user.domain.User;
import com.gaplog.server.domain.user.dto.request.UserUpdateRequest;
import com.gaplog.server.domain.user.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

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

    @Transactional(readOnly=true)
    public List<MainPostResponse> getUserPostInfo(Long userId) {
//        User user = userRepository.findById(userId).orElseThrow(()
//                -> new IllegalArgumentException("Invalid user ID: " + userId));

        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream().map(MainPostResponse::of).collect(Collectors.toList());

    }

    @Transactional(readOnly=true)
    public List<CommentResponse> getUserCommentInfo(Long userId) {
        List<Comment> comments = commentRepository.findByUserId(userId);
        return comments.stream().map(CommentResponse::of).collect(Collectors.toList());
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findActiveUserById(userId).orElseThrow(()
                -> new IllegalArgumentException("Invalid user ID: " + userId));

        user.deleteUser();
        userRepository.save(user);

    }
}
