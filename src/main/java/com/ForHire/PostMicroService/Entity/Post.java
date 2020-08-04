package com.ForHire.PostMicroService.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(min = 3,max = 25)
    private String title;

    @NotBlank
    @Size(min = 10,max = 300)
    private String description;

    @NotBlank
    private String type;

    @NotBlank
    private String category;

    private String photo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date datePosted=new Date();

    private Long userId;

    @NotBlank
    @Size(max = 120)
    private String duration_price;

    @NotBlank
    @Size(max = 120)
    private String contactDetails;

    public Post(String title,String description, String type, String category, String photo, Long userId,String duration_price,String contactDetails) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.category = category;
        this.photo = photo;
        this.duration_price = duration_price;
        this.contactDetails = contactDetails;
        this.userId = userId;

    }

    public Post() {    }



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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public String getDuration_price() {
        return duration_price;
    }

    public void setDuration_price(String duration_price) {
        this.duration_price = duration_price;
    }


    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", datePosted=" + datePosted +
                ", price='" + duration_price + '\'' +
                ", contactDetails='" + contactDetails + '\'' +
                '}';
    }
}
