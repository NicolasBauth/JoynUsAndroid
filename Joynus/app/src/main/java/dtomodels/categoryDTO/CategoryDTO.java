package dtomodels.categoryDTO;



public class CategoryDTO
{
    public long Id;
    public String Title;

    public long getId() {
        return Id;
    }

    public String getTitle() {
        return Title;
    }

    public void setId(long id) {
        Id = id;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
