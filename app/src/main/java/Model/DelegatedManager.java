package Model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.Locale;

public class DelegatedManager {
    @SerializedName("delegateManagerID")
    public int DelegatedManagerID;
    @SerializedName("fromDate")
    public LocalDateTime FromDate;
    @SerializedName("toDate")
    public LocalDateTime ToDate;
    @SerializedName("userID")
    public int UserID;

    public LocalDateTime getFromDate() {
        return FromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        FromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        return ToDate;
    }

    public void setToDate(LocalDateTime toDate) {
        ToDate = toDate;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }
}
