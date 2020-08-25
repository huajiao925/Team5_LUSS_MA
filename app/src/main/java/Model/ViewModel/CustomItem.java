package Model.ViewModel;

import com.google.gson.annotations.SerializedName;

public class CustomItem {
    @SerializedName("itemID")
    public int ItemID;
    @SerializedName("itemCode")
    public String ItemCode;
    @SerializedName("itemName")
    public String ItemName;
    @SerializedName("location")
    public String Location;
    @SerializedName("uom")
    public String UOM;
    @SerializedName("inStockQty")
    public int InStockQty;
    @SerializedName("reOrderLevel")
    public int ReOrderLevel;
    @SerializedName("reOrderQty")
    public int ReOrderQty;
    @SerializedName("categoryName")
    public String CategoryName;

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public int getInStockQty() {
        return InStockQty;
    }

    public void setInStockQty(int inStockQty) {
        InStockQty = inStockQty;
    }

    public int getReOrderLevel() {
        return ReOrderLevel;
    }

    public void setReOrderLevel(int reOrderLevel) {
        ReOrderLevel = reOrderLevel;
    }

    public int getReOrderQty() {
        return ReOrderQty;
    }

    public void setReOrderQty(int reOrderQty) {
        ReOrderQty = reOrderQty;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
}
