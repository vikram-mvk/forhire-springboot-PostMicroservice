package com.ForHire.PostMicroService.DTO;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class PostResponseDTO {
    @NotBlank
    private Long id;
    private String title;
    private String description;
    private String type;
    private String category;
    private String duration_price;
    private Date datePosted;
    private String photo;
    private String contactDetails;
    private  String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDuration_price() {
        return duration_price;
    }

    public void setDuration_price(String duration_price) {
        this.duration_price = duration_price;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public PostResponseDTO() {
    }

    public PostResponseDTO(Long id, String title, String userName,String description, String type, String category, String duration_price, Date datePosted, String photo, String contactDetails) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.category = category;
        this.duration_price = duration_price;
        this.datePosted = datePosted;
        this.photo = photo;
        this.userName=userName;
        this.contactDetails = contactDetails;
    }
}
