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
                imagePath = "Assets/study.jpg";
                dbId = 1;
                break;
            case "Jeux vidéo":
                imagePath = "Assets/VideoGame.jpg";
                dbId = 2;
                break;
            case "Langues":
                imagePath = "Assets/languages.jpg";
                dbId = 3;
                break;
            case "Sport":
                imagePath = "Assets/sports.jpg";
                dbId = 4;
                break;
            case "Dîner":
                imagePath = "Assets/Dinner.png";
                dbId = 5;
                break;
            case "Soirée":
                imagePath = "Assets/Party.jpg";
                dbId = 6;
                break;
            case "Culture":
                imagePath = "Assets/Culture.jpg";
                dbId = 7;
                break;
            case "Musique":
                imagePath = "Assets/music.jpg";
                dbId = 8;
                break;
            case "Jeux de société":
                imagePath = "Assets/BoardGame.jpg";
                dbId = 9;
                break;
            default:
                imagePath = "Assets/questionMark.jpg";
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
