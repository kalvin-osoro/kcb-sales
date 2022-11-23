package com.ekenya.rnd.backend.fskcb.QSSAdapter.services;

import com.ekenya.rnd.backend.fskcb.QSSAdapter.datasource.entities.QssAlertDirection;
import com.ekenya.rnd.backend.fskcb.QSSAdapter.datasource.entities.QssAlertEntity;
import com.ekenya.rnd.backend.fskcb.QSSAdapter.datasource.entities.QssAlertStatus;
import com.ekenya.rnd.backend.fskcb.QSSAdapter.datasource.repositories.QssAlertsRepository;
import com.ekenya.rnd.backend.fskcb.QSSAdapter.qss.QssClientManager;
import com.ekenya.rnd.backend.fskcb.QSSAdapter.qss.SsData;
import com.ekenya.rnd.backend.fskcb.QSSAdapter.qss.SsNotification;
import com.ekenya.rnd.backend.fskcb.QSSAdapter.qss.models.QssEvents;
import com.ekenya.rnd.backend.fskcb.QSSAdapter.qss.models.QssUser;
import com.google.gson.JsonObject;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

@Slf4j
@Service
public class QssService implements IQssService{

    @Autowired
    QssAlertsRepository qssAlertsRepository;

    @Autowired
    DateFormat dateFormat;

    private Set<QssUser> onlineUsers = new HashSet<>();

    private QssClientManager qssClientManager;

    public QssService(){
    }

    @EventListener
    public void onApplicationReadyEvent(ApplicationReadyEvent event) {

        System.out.println("Starting QSS...");

        //
        qssClientManager = QssClientManager.getShared()
                .setQssService(this);
        //
        qssClientManager.subscribeForData()
                .subscribeOn(Schedulers.single())
                .subscribe(new Consumer<SsData>() {
                    @Override
                    public void accept(SsData ssData) throws Throwable {
                        //
                        if(ssData.data.isJsonObject()){
                            JsonObject data = ssData.data.getAsJsonObject();
                            updateAlertStatus(data);
                        }
                    }
                });
        //
        qssClientManager.subscribeForNotifications().subscribe(new Consumer<SsNotification>() {
            @Override
            public void accept(SsNotification ssNotification) throws Throwable {
                //
                QssAlertEntity alertEntity = new QssAlertEntity();
                alertEntity.setContent(ssNotification.getContent());
                alertEntity.setTitle(ssNotification.getTitle());
                alertEntity.setCategory(ssNotification.getCollapseId());
                alertEntity.setType(QssAlertDirection.INCOMING);
                alertEntity.setSenderId(ssNotification.getSenderId());
                alertEntity.setRefCode(ssNotification.getId());
                alertEntity.setTimeDelivered(Calendar.getInstance().getTime());
                //
                qssAlertsRepository.save(alertEntity);
            }
        });
        //
        qssClientManager.init();
        //
        System.out.println("QSS Started...");
    }

    @Override
    public Set<QssUser> getOnlineUsers() {
        return onlineUsers;
    }

    @Override
    public void updateOnlineUsers(Set<QssUser> newList) {

        //
        onlineUsers = newList;

        for (QssUser user: onlineUsers) {
            //
            checkAndSendPendingAlerts(user.getId());
        }
    }

    @Override
    public void addOrUpdateOnlineUser(QssUser user) {

        for (QssUser u: getOnlineUsers()) {
            if(u.getId().equalsIgnoreCase(user.getId())){
                return;
            }
        }
        //
        getOnlineUsers().add(user);
    }

    @Override
    public boolean removeOnlineUser(QssUser user) {

        for (QssUser u: getOnlineUsers()) {
            if(u.getId().equalsIgnoreCase(user.getId())){
                getOnlineUsers().remove(user);
                return true;
            }
        }
        return false;
    }

    public boolean sendAlert(String receiverId, String title, String content,String category) {

        try{

            QssAlertEntity entity = new QssAlertEntity();
            entity.setTitle(title);
            entity.setReceiverId(receiverId);
            if(category != null) {
                entity.setCategory(category);
            }else{
                entity.setCategory("system");
            }
            entity.setContent(content);
            //
            qssAlertsRepository.save(entity);

            boolean online = false;
            for (QssUser user: getOnlineUsers()) {
                if(user.getId().equalsIgnoreCase(receiverId)){
                    online = true;
                    break;
                }
            }
            if(online) {
                //Post it to the user
                qssClientManager.pushAlert(receiverId, title, content, "system")
                        .subscribe(new BiConsumer<String, Throwable>() {
                            @Override
                            public void accept(String s, Throwable throwable) throws Throwable {
                                if (throwable == null) {
                                    entity.setRefCode(s);
                                    qssAlertsRepository.save(entity);
                                } else {
                                    log.error(throwable.getMessage(), throwable);
                                }
                            }
                        });
            }
            //Sent..
            return true;
            //
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return false;
    }


    private void checkAndSendPendingAlerts(String userId){
        try{

            for (QssAlertEntity entity: qssAlertsRepository.findAllByReceiverIdAndStatus(userId, QssAlertStatus.PENDING)) {

                //Post it to the user
                qssClientManager.pushAlert(userId,entity.getTitle(),entity.getContent(),entity.getCategory())
                        .subscribe(new BiConsumer<String, Throwable>() {
                    @Override
                    public void accept(String s, Throwable throwable) throws Throwable {
                        if(throwable == null){
                            entity.setRefCode(s);
                            qssAlertsRepository.save(entity);
                        }else{
                            log.error(throwable.getMessage(),throwable);
                        }
                    }
                });
                //
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
    }

    private void updateAlertStatus(JsonObject data){

        if(data.get("event").getAsString().equalsIgnoreCase(QssEvents.AlertRead)){
            String refCode = data.get("nid").getAsString();
            //
            Optional<QssAlertEntity> alert = qssAlertsRepository.findByRefCode(refCode);
            //
            if(alert.isPresent()){
                alert.get().setStatus(QssAlertStatus.READ);
                try{
                    Date date = dateFormat.parse(data.get("time").getAsString());
                    alert.get().setTimeRead(date);
                } catch (ParseException e) {
                    log.error(e.getMessage(),e);
                    alert.get().setTimeRead(Calendar.getInstance().getTime());
                }
                qssAlertsRepository.save(alert.get());
            }
        }else if(data.get("event").getAsString().equalsIgnoreCase(QssEvents.AlertDelivered)){
            String refCode = data.get("nid").getAsString();
            //
            for (QssAlertEntity e:qssAlertsRepository.findAll()
                 ) {
                log.info(e.getTitle(),e.getRefCode());
            }
            Optional<QssAlertEntity> alert = qssAlertsRepository.findByRefCode(refCode);
            //
            if(alert.isPresent()){
                alert.get().setStatus(QssAlertStatus.DELIVERED);
                try{
                    Date date = dateFormat.parse(data.get("time").getAsString());
                    alert.get().setTimeDelivered(date);
                } catch (ParseException e) {
                    log.error(e.getMessage(),e);
                    alert.get().setTimeDelivered(Calendar.getInstance().getTime());
                }
                qssAlertsRepository.save(alert.get());
            }
        }else{
            //
            log.warn("Unknown payload "+ data.getAsString());
        }
    }
}
