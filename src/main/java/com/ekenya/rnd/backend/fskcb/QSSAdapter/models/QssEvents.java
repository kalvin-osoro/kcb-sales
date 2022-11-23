package com.ekenya.rnd.backend.fskcb.QSSAdapter.models;

public class QssEvents {
    /**Events label for receiving online users list*/
    public static final String OnlineUsers = "OnlineUsers";
    /**Events label for receiving user comes online updates*/
    public static final String UserOnline = "UserOnline";
    /**Events label for receiving user goes offline updates*/
    public static final String UserOffline = "UserOffline";

    /**Events label for receiving pushed abstract data from service or other users*/
    public static final String Payload = "OnData";


    public static final String AlertRead = "alert_read";
    public static final String AlertDelivered = "alert_delivered";
    public static final String PayloadDelivered = "data_delivered";

    //Group Management Events
    public static final String UserGroups = "UserGroups";
    public static final String NewGroup = "NewGroup";
    public static final String GroupRemoved = "GroupRemoved";
    public static final String LeaveGroup = "LeaveGroup";
}
