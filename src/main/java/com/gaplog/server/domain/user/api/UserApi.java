package com.gaplog.server.domain.user.api;

import com.gaplog.server.domain.user.application.UserService;
import com.gaplog.server.domain.user.domain.User;
import com.gaplog.server.domain.user.dto.request.UserUpdateRequestDTO;
import com.gaplog.server.domain.user.dto.response.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User API", description = "유저 정보 관리 API")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApi {
    // auth로 원래 유저 가입해야하는데 UserRelationship 확인위해 user 생성 메소드 추가
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> creatUser(@RequestBody User user){
        User createUser = userService.createUser(user);
        return ResponseEntity.ok(createUser);

    }

    @Operation(summary = "유저 정보 수정", description = "유저의 정보를 수정합니다.")
    @PutMapping("/{user_id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable ("user_id") Long userId,
                                                      @RequestBody UserUpdateRequestDTO userUpdateRequestDTO){

        UserResponseDTO userUpdateResponseDTO = userService.updateUser(userId,userUpdateRequestDTO);
        return ResponseEntity.ok(userUpdateResponseDTO);
    }


    @Operation(summary = "유저 정보 조회", description = "유저의 정보를 조회합니다.")
    @GetMapping("/{user_id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable ("user_id") Long userId){
        UserResponseDTO userResponseDTO = userService.getUserInfo(userId);
        return ResponseEntity.ok(userResponseDTO);
    }

}
