package model;



public class Category
{
    private String title;
    private int dbId;
    private String imagePath;

    public Category(String title)
    {
        this.title = title;
        switch(title)
        {
            case "Etude":
                imagePath = "@drawable/study.jpg";
                dbId = 1;
                break;
            case "Jeux vidéo":
                imagePath = "@drawable/VideoGame.jpg";
                dbId = 2;
                break;
            case "Langues":
                imagePath = "@drawable/languages.jpg";
                dbId = 3;
                break;
            case "Sport":
                imagePath = "@drawable/sports.jpg";
                dbId = 4;
                break;
            case "Dîner":
                imagePath = "@drawable/Dinner.png";
                dbId = 5;
                break;
            case "Soirée":
                imagePath = "@drawable/Party.jpg";
                dbId = 6;
                break;
            case "Culture":
                imagePath = "@drawable/Culture.jpg";
                dbId = 7;
                break;
            case "Musique":
                imagePath = "@drawable/music.jpg";
                dbId = 8;
                break;
            case "Jeux de société":
                imagePath = "@drawable/BoardGame.jpg";
                dbId = 9;
                break;
            default:
                imagePath = "@drawable/questionMark.jpg";
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
