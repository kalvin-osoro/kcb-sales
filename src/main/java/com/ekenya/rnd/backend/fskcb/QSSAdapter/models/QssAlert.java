package com.ekenya.rnd.backend.fskcb.QSSAdapter.models;


import com.ekenya.rnd.backend.fskcb.QSSAdapter.qss.SsNotification;

import java.util.Date;

public class QssAlert extends SsNotification {
    private String id;
    private String origin;
    private String title;
    private String collapsedId;
    private String content;
    private Date date;
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getSenderId() {
        return origin;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getCollapseId() {
        return collapsedId;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public Date getTime() {
        return date;
    }
}
