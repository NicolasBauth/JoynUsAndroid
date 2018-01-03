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
    public static ArrayList<Category> getAllCategoriesArrayList()
    {
        ArrayList<Category> allCategoriesArrayList = new ArrayList<Category>();
        allCategoriesArrayList.add(new Category("Etude"));
        allCategoriesArrayList.add(new Category("Jeux vidéo"));
        allCategoriesArrayList.add(new Category("Langues"));
        allCategoriesArrayList.add(new Category("Sport"));
        allCategoriesArrayList.add(new Category("Dîner"));
        allCategoriesArrayList.add(new Category("Soirée"));
        allCategoriesArrayList.add(new Category("Culture"));
        allCategoriesArrayList.add(new Category("Musique"));
        allCategoriesArrayList.add(new Category("Jeux de société"));
        return allCategoriesArrayList;
    }
}
