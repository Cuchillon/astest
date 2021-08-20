package com.ferick.model.pet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pet {

    public Integer id;
    public Category category;
    public String name;
    public List<String> photoUrls;
    public List<Tag> tags;
    public String status;

    public Integer getId() {
        return id;
    }

    public Pet setId(Integer id) {
        this.id = id;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Pet setCategory(Category category) {
        this.category = category;
        return this;
    }

    public String getName() {
        return name;
    }

    public Pet setName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public Pet setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
        return this;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Pet setTags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Pet setStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pet pet = (Pet) o;

        if (id != null ? !id.equals(pet.id) : pet.id != null) return false;
        if (category != null ? !category.equals(pet.category) : pet.category != null) return false;
        if (name != null ? !name.equals(pet.name) : pet.name != null) return false;
        if (photoUrls != null ? !photoUrls.equals(pet.photoUrls) : pet.photoUrls != null) return false;
        if (tags != null ? !tags.equals(pet.tags) : pet.tags != null) return false;
        return status != null ? status.equals(pet.status) : pet.status == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (photoUrls != null ? photoUrls.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
