package jpabook.jpashop.example;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired private PostRepository postRepository;

    @Test
    public void 게시글_등록() throws Exception {
        //given
        Post post = new Post();
        post.setTitle("POST1");

        //when
        Long saveId = postRepository.save(post);
        Post findOne = postRepository.findOne(saveId);

        //then
        assertThat(findOne.getTitle()).isEqualTo("POST1");
    }

    @Test
    public void 코멘트_등록() throws Exception {
        //given
        Post post = new Post();
        post.setTitle("POST1");

        Comment comment1 = new Comment();
        comment1.setContent("Comment1");

        Comment comment2 = new Comment();
        comment2.setContent("Comment2");

        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);

        //when
        post.addComment(comment1);
        post.addComment(comment2);

        Long saveId = postRepository.save(post);
        Post findOne = postRepository.findOne(saveId);

        //then
        for(int i = 0; i < comments.size(); i++){
            assertThat(findOne.getComments().get(i)).isEqualTo(comments.get(i));
        }


    }

}