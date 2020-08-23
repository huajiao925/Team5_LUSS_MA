package Model;

import com.google.gson.annotations.SerializedName;

public class ItemCategory {

    @SerializedName("categoryID")
    public int CategoryID;

    @SerializedName("categoryName")
    public String CategoryName;

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
}
