package com.lo.sisyphuser.fish.source.been;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xx on 2016/9/13.
 */
public class Food implements Parcelable{
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

    public void setSteps(ArrayList<FoodStep> steps) {
        this.steps = steps;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setBurden(String burden) {
        this.burden = burden;
    }

    public void setAlbums(ArrayList<String> albums) {
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

    public Food(){}

    public Food(Parcel in){
        id=in.readString();
        title=in.readString();
        tags=in.readString();
        imtro=in.readString();
        ingredients=in.readString();
        burden=in.readString();
        albums=in.createStringArrayList();
        steps=in.createTypedArrayList(new Creator<FoodStep>() {
            @Override
            public FoodStep createFromParcel(Parcel parcel) {
                return new FoodStep(parcel);
            }

            @Override
            public FoodStep[] newArray(int i) {
                return new FoodStep[i];
            }
        });
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(tags);
        parcel.writeString(imtro);
        parcel.writeString(ingredients);
        parcel.writeString(burden);
        parcel.writeStringList(albums);
        parcel.writeTypedList(steps);
    }

    public static final Parcelable.Creator<Food> CREATOR=new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel parcel) {
            return new Food(parcel);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };
}
