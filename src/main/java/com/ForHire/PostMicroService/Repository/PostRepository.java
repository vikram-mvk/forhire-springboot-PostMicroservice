package com.ForHire.PostMicroService.Repository;

import com.ForHire.PostMicroService.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post,Long> {
        List<Post> findAllByCategory(String category);
        List<Post> findAllByType(String type);
        List<Post> findAllByTypeAndCategory(String type, String category);
        List<Post> findAllByTitleContainingOrDescriptionContaining(String title, String desc);

}
