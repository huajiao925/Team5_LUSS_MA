package Model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.Locale;

public class DelegatedManager {
    @SerializedName("delegatedManagerID")
    public int DelegatedManagerID;
    @SerializedName("fromDate")
    public String FromDate;
    @SerializedName("toDate")
    public String ToDate;
    @SerializedName("userID")
    public int UserID;
    @SerializedName("user")
    public User User;
    @SerializedName("users")
    public User[] Users;

    public Model.User getUser() {
        return User;
    }

    public void setUser(Model.User user) {
        User = user;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public int getDelegatedManagerID() {
        return DelegatedManagerID;
    }

    public void setDelegatedManagerID(int delegatedManagerID) {
        DelegatedManagerID = delegatedManagerID;
    }
    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }
}
