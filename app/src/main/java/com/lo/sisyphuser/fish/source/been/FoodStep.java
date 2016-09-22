package com.lo.sisyphuser.fish.source.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xx on 2016/9/13.
 */
public class FoodStep implements Parcelable{
    private String img;
    private String step;

    public String getImg() {
        return img;
    }

    public String getStep() {
        return step;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public FoodStep(){}

    public FoodStep(Parcel in){
        img=in.readString();
        step=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(img);
        parcel.writeString(step);
    }

    public static final Parcelable.Creator<FoodStep> CREATOR=new Creator<FoodStep>() {
        @Override
        public FoodStep createFromParcel(Parcel parcel) {
            return new FoodStep(parcel);
        }

        @Override
        public FoodStep[] newArray(int i) {
            return new FoodStep[i];
        }
    };
}
