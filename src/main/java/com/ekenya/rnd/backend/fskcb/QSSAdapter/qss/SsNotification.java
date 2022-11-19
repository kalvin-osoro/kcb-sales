package com.ekenya.rnd.backend.fskcb.QSSAdapter.qss;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;

/**
 * Created by Bourne Koloh on 06 December,2019.
 * Eclectics International, Products and R&D
 * PROJECT: Messenger
 */
public abstract class SsNotification implements Serializable {

    public abstract String getId();

    public abstract String getTitle();

    public abstract String getCollapseId();

    public abstract String getContent();

    public abstract Date getTime();

    public Class<?> getParameterClass() {
        return (Class<?>) (((ParameterizedType) SsData.class.getGenericSuperclass()).getActualTypeArguments()[0]);
    }
}
