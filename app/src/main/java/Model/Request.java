package Model;

import java.time.LocalDateTime;

public class Request {

    public int RequestID;
    public Status.EOrderStatus RequestStatus;
    public LocalDateTime RequestDate;
    public int RequestBy;
    public int ModifiedBy;
    public String Comment;
    public Model.RequestType.ERequestType RequestType;
    public int ParentRequestID;
    public LocalDateTime CollectionTime;
    public int RetrievalID;

    public Status.EOrderStatus getRequestStatus() {
        return RequestStatus;
    }

    public void setRequestStatus(Status.EOrderStatus requestStatus) {
        RequestStatus = requestStatus;
    }

    public LocalDateTime getRequestDate() {
        return RequestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        RequestDate = requestDate;
    }

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

    public LocalDateTime getCollectionTime() {
        return CollectionTime;
    }

    public void setCollectionTime(LocalDateTime collectionTime) {
        CollectionTime = collectionTime;
    }

    public int getRetrievalID() {
        return RetrievalID;
    }

    public void setRetrievalID(int retrievalID) {
        RetrievalID = retrievalID;
    }
}
