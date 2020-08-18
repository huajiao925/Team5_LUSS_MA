package Model;

public class item {

    public int ItemID;
    public String ItemName;
    public String UOM;
    public int ReStockQty;
    public int InStockQty;
    public int  CategoryID;
    public String ItemCode;
    public int ReStockLevel;
    public String StoreItemLocation;

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
