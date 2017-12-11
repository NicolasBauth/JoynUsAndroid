package service;


import java.util.ArrayList;

import model.Category;

public class CategoryService
{
    public static ArrayList<Category> categoryNamesListToCategoryList(ArrayList<String> categoryNames)
    {
        ArrayList<Category> createdCategoryList = new ArrayList<Category>();
        Category currentCategory;
        for(String name : categoryNames)
        {
            currentCategory = new Category(name);
            createdCategoryList.add(currentCategory);
        }
        return createdCategoryList;
    }
}
