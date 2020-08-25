package Model;

import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("itemID")
    public int ItemID;
    @SerializedName("itemName")
    public String ItemName;
    @SerializedName("uom")
    public String UOM;
    @SerializedName("reStockQty")
    public int ReStockQty;
    @SerializedName("inStockQty")
    public int InStockQty;
    @SerializedName("categoryID")
    public int  CategoryID;
    @SerializedName("itemCode")
    public String ItemCode;
    @SerializedName("reStockLevel")
    public int ReStockLevel;
    @SerializedName("storeItemLocation")
    public String StoreItemLocation;

    @SerializedName("itemCategory")
    public String ItemCategory;

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public int getReStockQty() {
        return ReStockQty;
    }

    public void setReStockQty(int reStockQty) {
        ReStockQty = reStockQty;
    }

    public int getInStockQty() {
        return InStockQty;
    }

    public void setInStockQty(int inStockQty) {
        InStockQty = inStockQty;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public int getReStockLevel() {
        return ReStockLevel;
    }

    public void setReStockLevel(int reStockLevel) {
        ReStockLevel = reStockLevel;
    }

    public String getStoreItemLocation() {
        return StoreItemLocation;
    }

    public void setStoreItemLocation(String storeItemLocation) {
        StoreItemLocation = storeItemLocation;
    }
}
