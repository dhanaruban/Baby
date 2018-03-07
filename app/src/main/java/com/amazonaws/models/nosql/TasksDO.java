package com.amazonaws.models.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "babycasketprod-mobilehub-399668420-tasks")

public class TasksDO {
    private String _userId;
    private String _cOLUMNIMAGE;
    private String _cOLUMNRELATIONSHIP;
    private String _uPLOADSTATUS;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBAttribute(attributeName = "COLUMN_IMAGE")
    public String getCOLUMNIMAGE() {
        return _cOLUMNIMAGE;
    }

    public void setCOLUMNIMAGE(final String _cOLUMNIMAGE) {
        this._cOLUMNIMAGE = _cOLUMNIMAGE;
    }
    @DynamoDBAttribute(attributeName = "COLUMN_RELATIONSHIP")
    public String getCOLUMNRELATIONSHIP() {
        return _cOLUMNRELATIONSHIP;
    }

    public void setCOLUMNRELATIONSHIP(final String _cOLUMNRELATIONSHIP) {
        this._cOLUMNRELATIONSHIP = _cOLUMNRELATIONSHIP;
    }
    @DynamoDBAttribute(attributeName = "UPLOAD_STATUS")
    public String getUPLOADSTATUS() {
        return _uPLOADSTATUS;
    }

    public void setUPLOADSTATUS(final String _uPLOADSTATUS) {
        this._uPLOADSTATUS = _uPLOADSTATUS;
    }

}
