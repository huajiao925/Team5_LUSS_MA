package Model;

import com.google.gson.annotations.SerializedName;

public class Cart {

    @SerializedName("cartID")
    public int CartID;
    @SerializedName("userID")
    public int UserID;
    @SerializedName("itemID")
    public int ItemID;
    @SerializedName("qty")
    public int Qty;

    public int getCartID() {
        return CartID;
    }

    public void setCartID(int cartID) {
        CartID = cartID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }
}
