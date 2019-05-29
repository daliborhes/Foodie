package com.daliborhes.foodie.Model;

/**
 * Created by Dalibor J. Stanković on 21.05.2019.
 */

public class Category {

    private String Name;
    private String Image;
    private String CategoryId;

    public Category() {
    }

    public Category(String name, String image, String categoryId) {
        Name = name;
        Image = image;
        CategoryId = categoryId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }
}
