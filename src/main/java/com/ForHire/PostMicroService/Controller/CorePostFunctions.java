package com.ForHire.PostMicroService.Controller;

import com.ForHire.PostMicroService.DTO.FilterSearchDTO;
import com.ForHire.PostMicroService.DTO.PostResponseDTO;
import com.ForHire.PostMicroService.Entity.Post;

import com.ForHire.PostMicroService.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/posts")
public class CorePostFunctions {

    @Autowired
    PostService postService;

    @Autowired
    @Lazy
    RestTemplate restTemplate;

    @Value("${USER-MICROSERVICE.API.getUserInRequest}")
    private String getUserInRequest;

    @Value("${USER-MICROSERVICE.API.getUserNameFromId}")
    private String getUserNameFromId;

    @Value("${POST-MICROSERVICE.photoLocation}")
    private String photoLocation;

    @PostMapping("/newPost")
    public ResponseEntity<?> makePost(@RequestParam(value="photo",required = false) MultipartFile photo, HttpServletRequest req, @RequestHeader("Authorization") String auth) {

        //Send REST request with JWT in header and expect user object in return with status 200
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth);
        HttpEntity<String> entity = new HttpEntity<>("paramters", headers);
        ResponseEntity<Long> response = restTemplate.exchange(getUserInRequest, HttpMethod.POST, entity, Long.class);
        if(response.getStatusCode().value()!=200) return response;

        //Get userId from user and set it in post. Save the Post
        Long userId= response.getBody();
        Post p = new Post();
        p.setTitle(req.getParameter("title"));
        p.setDescription(req.getParameter("description"));
        p.setCategory(req.getParameter("category"));
        p.setType(req.getParameter("type"));
        p.setContactDetails(req.getParameter("contactDetails"));
        p.setDuration_price(req.getParameter("duration_price"));
        p.setUserId(userId);
        postService.savePost(p);

        //If a photo is present, set a unique name and save it in hardDisk with filename as post's photoName
        if (photo !=null){
        String photoName = p.getId() +"_" +new Date().toString();
        p.setPhoto(photoName);
        File loc = new File(photoLocation, photoName);
        try {    photo.transferTo(loc);     } catch (Exception e) {      System.out.println(e.toString());   return ResponseEntity.badRequest().body("Error saving photo.. Make sure Photo saving Location is valid and exists");}

        postService.savePost(p);
    }
        return ResponseEntity.ok("Successful");
}

    @GetMapping("/viewPost/{id}")
    public ResponseEntity<?> viewItem(@PathVariable("id") Long id, @RequestHeader("Authorization") String jwt){

        //Find post
        Post p = postService.findById(id);
        if(p==null) return ResponseEntity.badRequest().body("Listing not Found");

        //Get userId from post and get User name from user service using this id
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("id",Long.toString(p.getUserId()));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwt);
        HttpEntity<?> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(getUserNameFromId, HttpMethod.POST, entity, String.class);
        if(response.getStatusCode().value()!=200) return response; //Return whatever error that is

        String userName =  response.getBody();
        PostResponseDTO post = new PostResponseDTO(p.getId(),p.getTitle(),userName,p.getDescription(),p.getType(),p.getCategory(),p.getDuration_price(),p.getDatePosted(),p.getPhoto(),p.getContactDetails());

        return ResponseEntity.ok().body(post);
    }

    @PostMapping("/searchPosts")
    public ResponseEntity<?> searchPosts(@RequestBody FilterSearchDTO search){
        String key = search.getKey();
        return ResponseEntity.ok().body(postService.searchPosts(key));
    }

    @PostMapping("/getPosts")
    public ResponseEntity<?> getPosts(@RequestBody FilterSearchDTO constraints){
        String type = constraints.getType();
        String category = constraints.getCategory();
        return ResponseEntity.ok().body(postService.filterPosts(type,category));
    }
}