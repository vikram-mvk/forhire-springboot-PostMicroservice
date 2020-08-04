package com.ForHire.PostMicroService.Controller;

import com.ForHire.PostMicroService.DTO.FilterSearchDTO;
import com.ForHire.PostMicroService.DTO.JwtResponseDTO;
import com.ForHire.PostMicroService.DTO.LoginDTO;
import com.ForHire.PostMicroService.Entity.Post;
import com.ForHire.PostMicroService.Service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CorePostFunctions_IntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    PostService postService;

    @Autowired
    RestTemplate restTemplate;


    @Test
    void makePost() {

        try {
            JwtResponseDTO tester = getTester(restTemplate);

            Post post = new Post();
            LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();

            requestParams.add("title", "title");
            requestParams.add("description", "description");
            requestParams.add("type", "type");
            requestParams.add("category", "category");
            requestParams.add("duration_price", "duration_price");
            requestParams.add("contactDetails", "contactDetails");

            RequestBuilder request = MockMvcRequestBuilders.post("/posts/newPost").params(requestParams).header("Authorization", "Bearer " + tester.getToken());
            MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
            assertEquals("Successful", result.getResponse().getContentAsString());
            System.out.println("Response from /makePost  " + result.getResponse().getContentAsString());

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getCause());
        }
    }

    @Test
    void viewItem() {

        try {
            JwtResponseDTO tester = getTester(restTemplate);
            RequestBuilder request = MockMvcRequestBuilders.get("/posts/viewPost/1").header("Authorization", "Bearer " + tester.getToken());
            //Lets create a dummy post such that it looks like its made by tester
            Post p = new Post();
            p.setUserId(tester.getId());
            when(postService.findById(anyLong())).thenReturn(p);
            MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
            assert (result.getResponse().getContentAsString().contains(tester.getUsername()));
            System.out.println("Response from /viewItem  " + result.getResponse().getContentAsString());

        } catch (Exception e) {
            System.out.println(e.toString());
            fail(e.getCause());
        }
    }

    @Test
    void searchPosts() {

        try {

            JwtResponseDTO tester = getTester(restTemplate);
            FilterSearchDTO search = new FilterSearchDTO();
            String jsonRequest = objectMapper.writeValueAsString((Object) search);
            RequestBuilder request = MockMvcRequestBuilders.post("/posts/searchPosts").content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + tester.getToken());

            List<Post> p = new ArrayList<>();
            when(postService.searchPosts(anyString())).thenReturn(p);
            MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
            System.out.println("Response from /searchPosts  " + result.getResponse().getContentAsString());

        } catch (Exception e) {
            System.out.println(e.toString());
            fail(e.getCause());
        }


    }

    @Test
    void getPosts() {
        try {
            JwtResponseDTO tester = getTester(restTemplate);
            FilterSearchDTO filter = new FilterSearchDTO();
            String jsonRequest = objectMapper.writeValueAsString((Object) filter);

            RequestBuilder request = MockMvcRequestBuilders.post("/posts/getPosts")
                    .content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", "Bearer " + tester.getToken());

            List<Post> p = new ArrayList<>();
            when(postService.filterPosts(anyString(), anyString())).thenReturn(p);
            MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
            System.out.println("Response from /getPosts  " + result.getResponse().getContentAsString());

        } catch (Exception e) {
            System.out.println(e.toString());
            fail(e.getCause());
        }


    }


    //Secured API, So need to login to test. Hence using Dummy Account "Junit" for testing
    public JwtResponseDTO getTester(RestTemplate restTemplate){
        LoginDTO login = new LoginDTO("Junit", "junit");
        HttpEntity<LoginDTO> loginReq = new HttpEntity<>(login);
        ResponseEntity<JwtResponseDTO> jwtResponse = restTemplate.exchange("http://GATEWAY-MICROSERVICE/auth/login", HttpMethod.POST, loginReq, JwtResponseDTO.class);
        return jwtResponse.getBody();
    }


}



