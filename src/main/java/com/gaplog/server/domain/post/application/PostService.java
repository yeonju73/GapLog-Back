package com.gaplog.server.domain.post.application;

import com.gaplog.server.domain.caterory.dao.CategoryRepository;
import com.gaplog.server.domain.caterory.domain.Category;
import com.gaplog.server.domain.post.dao.PostRepository;
import com.gaplog.server.domain.post.domain.Post;
import com.gaplog.server.domain.post.dto.request.PostJinjiUpdateRequest;
import com.gaplog.server.domain.post.dto.request.PostLikeUpdateRequest;
import com.gaplog.server.domain.post.dto.request.PostScrapUpdateRequest;
import com.gaplog.server.domain.post.dto.request.PostUpdateRequest;
import com.gaplog.server.domain.post.dto.response.*;
import com.gaplog.server.domain.user.dao.UserRepository;
import com.gaplog.server.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    //기본 기능
    //post 작성
    /*To-Do
     * 카테고리 string -> entity로 변경
     * */
    public Post createPost(Long userId, String title, String content, String thumbnailUrl, Long categoryId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        User user = userOpt.get();

        //category 확인 추가,, 확인 필수,,
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        if (categoryOpt.isEmpty()) {
            throw new RuntimeException("Category not found with id: " + categoryId);
        }
        Category category = categoryOpt.get();

        Post newPost = Post.of(title, content, category, thumbnailUrl, user);
        return postRepository.save(newPost);
    }

    //post 수정
    @Transactional
    public PostUpdateResponse updatePost(Long postId, PostUpdateRequest postUpdateRequestDTO) {
        Post post = postRepository.findById(postId).orElseThrow(()
                ->new IllegalArgumentException("Post not found: " + postId));

        post.updateTitle(postUpdateRequestDTO.getTitle());
        post.updateContent(postUpdateRequestDTO.getContent());

        Post updatedPost = postRepository.save(post);
        return PostUpdateResponse.of(updatedPost);
    }

    //post 삭제
    public void deletePost(Long postId) {

    }


    //조회 기능
    //메인화면 게시글 조회
//    @Transactional
//    public MainPostResponse getMainPostInfo() {
//        Post getMainPostInfo = postRepository.setMainPage().orElseThrow(()
//                ->new IllegalArgumentException("Wrong Access"));
//        return List<MainPostResponse>.of(getMainPostInfo);
//    }

    //특정 게시물(선택된 게시물) 조회
    @Transactional
    public SelectedPostResponse getSelectedPostInfo(Long postId) {
        Post getSelectedPostInfo = postRepository.findById(postId).orElseThrow(()
                ->new IllegalArgumentException("Post not found: " + postId));
        return SelectedPostResponse.of(getSelectedPostInfo);
    }

    //카테고리별 조회
    @Transactional
    public FindByCategoryPostResponse getFindByCategoryPostInfo(Long postId) {
        Post getFindByCategoryPostInfo = postRepository.findById(postId).orElseThrow(()
                ->new IllegalArgumentException("Post not found: " + postId));
        return FindByCategoryPostResponse.of(getFindByCategoryPostInfo);
    }

    //키워드 조회
    @Transactional
    public FindByKeywordPostResponse getFindByKeywordPostInfo(Long postId) {
        Post getFindByKeywordPostInfo = postRepository.findById(postId).orElseThrow(()
                ->new IllegalArgumentException("Post not found: " + postId));
        return FindByKeywordPostResponse.of(getFindByKeywordPostInfo);
    }


    //반응 수정 기능
    //좋아요 수정
    @Transactional
    public PostLikeUpdateResponse PostLikeUpdate(Long postId, PostLikeUpdateRequest postLikeUpdateRequestDTO) {
        Post post = postRepository.findById(postId).orElseThrow(()
                ->new IllegalArgumentException("Post not found: " + postId));
        if(postLikeUpdateRequestDTO.isLike()){
            post.increaseLikeCount();
        }
        else{
            post.decreaseLikeCount();
        }

        Post updatedPost = postRepository.save(post);
        return PostLikeUpdateResponse.of(updatedPost);
    }

    //진지 수정
    @Transactional
    public PostJinjiUpdateResponse PostJinjiUpdate(Long postId, PostJinjiUpdateRequest postJinjiUpdateRequestDTO) {
        Post post = postRepository.findById(postId).orElseThrow(()
                ->new IllegalArgumentException("Post not found: " + postId));
        if(postJinjiUpdateRequestDTO.isJinji()){
            post.increaseJinjiCount();
        }
        else{
            post.decreaseJinjiCount();
        }

        Post updatedPost = postRepository.save(post);
        return PostJinjiUpdateResponse.of(updatedPost);
    }

    //스크랩 수정
    @Transactional
    public PostScrapUpdateResponse PostScrapUpdate(Long postId, PostScrapUpdateRequest postScrapUpdateRequestDTO) {
        Post post = postRepository.findById(postId).orElseThrow(()
                ->new IllegalArgumentException("Post not found: " + postId));
        if(postScrapUpdateRequestDTO.isScrap()){
            post.increaseScrapCount();
        }
        else{
            post.decreaseScrapCount();
        }

        Post updatedPost = postRepository.save(post);
        return PostScrapUpdateResponse.of(updatedPost);
    }

}
