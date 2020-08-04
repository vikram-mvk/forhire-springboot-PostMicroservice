package com.ForHire.PostMicroService.Service;

import com.ForHire.PostMicroService.Entity.Post;
import com.ForHire.PostMicroService.Repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PostService_UnitTest {
    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;


    @Test
    void savePost() {
        Post p = new Post();
        postService.savePost(p);
        Mockito.verify(postRepository,Mockito.times(1)).save(p);
        }

    @Test
    void filterPosts() {
        List<Post> p = new ArrayList<>();
        Mockito.when(postRepository.findAllByCategory(anyString())).thenReturn(p);
        Mockito.when(postRepository.findAllByType(anyString())).thenReturn(p);
        Mockito.when(postRepository.findAllByTypeAndCategory(anyString(),anyString())).thenReturn(p);
        assertEquals(p,postService.filterPosts("test","test"));
    }

    @Test
    void searchPosts() {
        List<Post> p = new ArrayList<>();
        Mockito.when(postRepository.findAllByTitleContainingOrDescriptionContaining(anyString(),anyString())).thenReturn(p);
        assertEquals(p,postService.filterPosts("test","test"));
    }

    @Test
    void findById() {
        Post p = new Post();
        Optional<Post> op = Optional.of(p);
        Mockito.when(postRepository.findById(anyLong())).thenReturn(op);
        assertEquals(op.get(),postService.findById(Long.parseLong("1")));
    }
}