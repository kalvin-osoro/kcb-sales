package com.ekenya.rnd.backend.fskcb.QSSAdapter.services;

import com.ekenya.rnd.backend.fskcb.QSSAdapter.qss.models.QssUser;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.List;
import java.util.Set;

public interface IQssService {

    Set<QssUser> getOnlineUsers();

    void updateOnlineUsers(Set<QssUser> newList);

    void addOrUpdateOnlineUser(QssUser user);

    boolean removeOnlineUser(QssUser user);

    boolean sendAlert(String receiver,String title,String content,String category);

    ArrayNode loadAllStoredAlerts();
}
