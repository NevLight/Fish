package com.lo.sisyphuser.fish.been;

import java.util.List;

/**
 * Created by xx on 2016/9/13.
 */
public class Food {
    private String id;
    private String title;
    private String tags;
    private String imtro;
    private String ingredients;
    private String burden;
    private List<String> albums;
    private List<FoodStep> steps;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTags() {
        return tags;
    }

    public String getImtro() {
        return imtro;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getBurden() {
        return burden;
    }

    public List<FoodStep> getSteps() {
        return steps;
    }

    public List<String> getAlbums() {
        return albums;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSteps(List<FoodStep> steps) {
        this.steps = steps;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setBurden(String burden) {
        this.burden = burden;
    }

    public void setAlbums(List<String> albums) {
        this.albums = albums;
    }

    public void setImtro(String imtro) {
        this.imtro = imtro;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
