package model;


import android.os.Parcel;
import android.os.Parcelable;

import com.example.nicol.joynus.LoginActivity;
import com.example.nicol.joynus.R;



public class Category implements Parcelable
{
    private String title;
    private int dbId;
    private Integer imagePath;

    public Category(String title)
    {
        switch(title)
        {
            case "Etude":
                imagePath = R.drawable.study;
                this.title = LoginActivity.getContextOfApplication().getString(R.string.interest_study);
                dbId = 1;
                break;
            case "Jeux vidéo":
                imagePath = R.drawable.videogame;
                this.title = LoginActivity.getContextOfApplication().getString(R.string.interest_videogames);
                dbId = 2;
                break;
            case "Langues":
                imagePath = R.drawable.languages;
                this.title = LoginActivity.getContextOfApplication().getString(R.string.interest_languages);
                dbId = 3;
                break;
            case "Sport":
                imagePath = R.drawable.sports;
                this.title = LoginActivity.getContextOfApplication().getString(R.string.interest_sports);
                dbId = 4;
                break;
            case "Dîner":
                imagePath = R.drawable.dinner;
                this.title = LoginActivity.getContextOfApplication().getString(R.string.interest_dinner);
                dbId = 5;
                break;
            case "Soirée":
                imagePath = R.drawable.party;
                this.title = LoginActivity.getContextOfApplication().getString(R.string.interest_party);
                dbId = 6;
                break;
            case "Culture":
                imagePath = R.drawable.culture;
                this.title = LoginActivity.getContextOfApplication().getString(R.string.interest_culture);
                dbId = 7;
                break;
            case "Musique":
                imagePath = R.drawable.music;
                this.title = LoginActivity.getContextOfApplication().getString(R.string.interest_music);
                dbId = 8;
                break;
            case "Jeux de société":
                imagePath = R.drawable.boardgame;
                this.title = LoginActivity.getContextOfApplication().getString(R.string.interest_boardgames);
                dbId = 9;
                break;
            default:
                imagePath = R.drawable.questionmark;
                this.title = LoginActivity.getContextOfApplication().getString(R.string.interest_other);
                dbId = 10;
                break;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public Integer getImagePath() {
        return imagePath;
    }

    public void setImagePath(Integer imagePath) {
        this.imagePath = imagePath;
    }

    protected Category(Parcel in)
    {
        title = in.readString();
        dbId = in.readInt();
        imagePath = in.readInt();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(title);
        dest.writeInt(dbId);
        dest.writeInt(imagePath);
    }
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
