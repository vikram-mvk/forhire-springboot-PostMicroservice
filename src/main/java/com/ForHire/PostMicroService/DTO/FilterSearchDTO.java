package com.ForHire.PostMicroService.DTO;


public class FilterSearchDTO {
    private String type;
    private String category;
    private String key; //for search

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public FilterSearchDTO(String type, String category,String key) {
        this.type = type;
        this.category = category;
        this.key=key;
    }

    public FilterSearchDTO() {
    }
}


