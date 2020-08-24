package Model.ViewModel;

import com.google.gson.annotations.SerializedName;

public class CustomRetrieval {
    @SerializedName("retrievalID")
    public int RetrievalID;
    @SerializedName("reStockLevel")
    public int ReStockLevel;
    @SerializedName("itemID")
    public int ItemID;
    @SerializedName("itemCode")
    public String ItemCode;
    @SerializedName("itemName")
    public String ItemName;
    @SerializedName("uom")
    public String UOM;
    @SerializedName("itemPrice")
    public int ItemPrice;
    @SerializedName("location")
    public String Location;
    @SerializedName("inStockQty")
    public int InStockQty;
    @SerializedName("itemCategory")
    public String ItemCategory;
    @SerializedName("categoryName")
    public String CategoryName;
    @SerializedName("totalQty")
    public int TotalQty;

    public int getRetrievalID() {
        return RetrievalID;
    }

    public void setRetrievalID(int retrievalID) {
        RetrievalID = retrievalID;
    }

    public int getReStockLevel() {
        return ReStockLevel;
    }

    public void setReStockLevel(int reStockLevel) {
        ReStockLevel = reStockLevel;
    }

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

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public int getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(int itemPrice) {
        ItemPrice = itemPrice;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getInStockQty() {
        return InStockQty;
    }

    public void setInStockQty(int inStockQty) {
        InStockQty = inStockQty;
    }

    public String getItemCategory() {
        return ItemCategory;
    }

    public void setItemCategory(String itemCategory) {
        ItemCategory = itemCategory;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public int getTotalQty() {
        return TotalQty;
    }

    public void setTotalQty(int totalQty) {
        TotalQty = totalQty;
    }
}
