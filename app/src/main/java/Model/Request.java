package Model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.List;

public class Request {

    @SerializedName("requestID")
    public int RequestID;
    @SerializedName("requestStatus")
    public Status.EOrderStatus RequestStatus;
    @SerializedName("requestDate")
    public String RequestDate;
    @SerializedName("requestBy")
    public int RequestBy;
    @SerializedName("modifiedBy")
    public int ModifiedBy;
    @SerializedName("comment")
    public String Comment;
    @SerializedName("requestType")
    public Model.RequestType.ERequestType RequestType;
    @SerializedName("parentRequestID")
    public int ParentRequestID;
    @SerializedName("collectionTime")
    public String CollectionTime;
    @SerializedName("retrievalID")
    public int RetrievalID;

    @SerializedName("modifiedByUser")
    public String ModifiedByUser;
    @SerializedName("requestByUser")
    public String RequestByUser;
    @SerializedName("requestDetails")
    public String RequestDetails;
    @SerializedName("retrieval")
    public String Retrieval;

    //Getter and setter


    public String getRequestDate() {
        return RequestDate;
    }

    public void setRequestDate(String requestDate) {
        RequestDate = requestDate;
    }

    public void setRequestID(int requestID) {
        RequestID = requestID;
    }

    public Status.EOrderStatus getRequestStatus() {
        return RequestStatus;
    }

    public void setRequestStatus(Status.EOrderStatus requestStatus) {
        RequestStatus = requestStatus;
    }
    public int getRequestID() {return RequestID; }

    public int getRequestBy() {
        return RequestBy;
    }

    public void setRequestBy(int requestBy) {
        RequestBy = requestBy;
    }

    public int getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(int modifiedBy) {
        ModifiedBy = modifiedBy;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public Model.RequestType.ERequestType getRequestType() {
        return RequestType;
    }

    public void setRequestType(Model.RequestType.ERequestType requestType) {
        RequestType = requestType;
    }

    public int getParentRequestID() {
        return ParentRequestID;
    }

    public void setParentRequestID(int parentRequestID) {
        ParentRequestID = parentRequestID;
    }


    public int getRetrievalID() {
        return RetrievalID;
    }

    public void setRetrievalID(int retrievalID) {
        RetrievalID = retrievalID;
    }
}
