package com.ForHire.PostMicroService.Service;


import com.ForHire.PostMicroService.Entity.Post;
import com.ForHire.PostMicroService.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
@Autowired
PostRepository postRepository;

public void savePost(Post post){
     postRepository.save(post);
}

public List<Post> filterPosts(String type,String category){

     //Both Filter
     if(type.length()>0 && category.length()>0) {
          return postRepository.findAllByTypeAndCategory(type, category);
     }

     //No Filter
     if(type.length()==0 && category.length()==0){
          return postRepository.findAll();
     }


     //One of the filters
     List<Post> filteredPost = new ArrayList<>();
     if(type.length()>0){
          filteredPost=postRepository.findAllByType(type);
     }
     if(category.length()>0){
          filteredPost=postRepository.findAllByCategory(category);
     }
     return filteredPost;

}

public List<Post> searchPosts(String key){
     return postRepository.findAllByTitleContainingOrDescriptionContaining(key,key);

}

public Post findById(Long id){
     Optional<Post> post = postRepository.findById(id);
     return post.isPresent()==false ?  null : post.get();
}


}
