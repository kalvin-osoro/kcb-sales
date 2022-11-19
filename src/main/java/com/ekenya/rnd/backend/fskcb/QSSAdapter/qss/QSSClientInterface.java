package com.ekenya.rnd.backend.fskcb.QSSAdapter.qss;


import com.ekenya.rnd.backend.fskcb.QSSAdapter.qss.models.QssUser;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

/**
 * Created by Bourne Koloh on 05 December,2019.
 * Eclectics International, Products and R&D
 * PROJECT: Qss Client Library
 */
public interface QSSClientInterface<D extends SsData> {

    /**
     * Implement and return the current user connection details
     * @return
     */
    Single<QssUser> getCurrentUser();
    /**
     *
     * @param data
     * @return
     */
    Single<Boolean> sendDirectData(D data, String receiverId);

    /**
     *
     * @param data
     * @param groupId
     * @return
     */
    Single<String> sendGroupData(D data, String groupId);

    Single<String> pushAlert(String recId, String title,String content,String category);

    Single<String> pushAlertToAll(D content);

    Observable<SsData> subscribeForData();

    Observable<SsNotification> subscribeForNotifications();
}
