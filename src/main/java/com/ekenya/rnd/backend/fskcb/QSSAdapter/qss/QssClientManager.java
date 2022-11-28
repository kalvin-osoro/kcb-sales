package com.ekenya.rnd.backend.fskcb.QSSAdapter.qss;

import com.ekenya.rnd.backend.fskcb.QSSAdapter.qss.models.*;
import com.ekenya.rnd.backend.fskcb.QSSAdapter.services.IQssService;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.microsoft.signalr.*;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.UndeliverableException;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.subjects.PublishSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class QssClientManager implements QSSClientInterface {

    public enum AlertType {
        INFORMATION, ERROR, WARNING
    }

    //
    private String mServiceHost = "https://devops.ekenya.co.ke:8084";
    //private String mServiceHost = "http://10.211.55.5:5000";
    private String serviceEndpoint = "/delta";

    //Client Settings
    private String qssUserId = "KCB_SALES_BACKEND";
    private String qssUserName = "KCB Sales Backend";
    private String qssClientId = "3szgpupeqUCKJXdRQzoXQ";//"762M5SB4GPW000SDFHW";
    private String qssClientSecret = "77lsHJP2pxVJZpXBsLWDnfRFnzvmDiTdhbxf8gzD6kvo3WQCKJW3Dnn";//"1DV6SGZW0UIO02ADHE78ASDAEAWEYADU78534AG";

    IQssService qssService;
    private Logger mLogger = Logger.getLogger(QssClientManager.class.getSimpleName());

    private HubConnection hubConnection;

    private CompletableObserver connectionStartObserver;
    private static QssClientManager instance;

    private QssToken authToken;

    private QssUser mCurrentUser;

    private Gson mGson = new GsonBuilder().disableHtmlEscaping()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setPrettyPrinting().serializeNulls().create();

    //
    private PublishSubject<HubConnectionState> connectionStateObserver;
    private PublishSubject<SsNotification> qssNotificationsObserver;
    private PublishSubject<SsData> qssContentObserver;
    //
    private PublishSubject<QssUser> userOnlineObserver;
    private PublishSubject<QssUser> userOfflineObserver;

    /**
     * Single shared instance
     *
     * @return
     */
    public synchronized static QssClientManager getShared() {
        if (instance == null)
            instance = new QssClientManager();
        return instance;
    }

    private QssClientManager() {

        //
        mLogger.addHandler(prepareLogger());
        //
        connectionStateObserver = PublishSubject.create();
        qssNotificationsObserver = PublishSubject.create();
        qssContentObserver = PublishSubject.create();
        //
        userOnlineObserver = PublishSubject.create();
        userOfflineObserver = PublishSubject.create();
        //
        mCurrentUser = new QssUser(qssUserId,qssUserName,"");
        mCurrentUser.setClientId(qssClientId);
        mCurrentUser.setClientSecret(qssClientSecret);
        //
        RxJavaPlugins.setErrorHandler(e -> {
            if (e instanceof UndeliverableException) {
                mLogger.log(Level.WARNING,"Uncaught Error => "+e.getMessage(), e);
                return;
            }
            throw e;
        });

        // Default Subscription
        connectionStateObserver.doOnNext(h -> {
            //
            System.out.println("Received Connection State Event => " + h);
            if (h != HubConnectionState.CONNECTED && hubConnection != null) {
                //
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }

                // Restart
                hubConnection.start().subscribe(connectionStartObserver);
            }
            //
        }).publish().autoConnect();

        init().subscribe();
    }

    public QssClientManager setQssService(IQssService service){
        this.qssService = service;
        return this;
    }
    /**
     * Creates a relatively unique id based on the machine this app is running
     *
     * @return
     * @throws UnknownHostException
     */
    public String generateId() throws UnknownHostException {

        InetAddress inetAddress = InetAddress.getLocalHost();
        //
        byte[] bin = (inetAddress.getHostName() + " | " + inetAddress.getHostAddress()).getBytes();
        //Utils.reverse(bin);
        //
        return Base64.getEncoder().withoutPadding().encodeToString(bin).toLowerCase();
    }

    /**
     * Get the service url specified in <code>init()</code> NOTE: If called before
     * successful init, it will return null
     *
     * @return
     */
    public String getServiceHost() {
        return mServiceHost;
    }

    /**
     * Call to initialize and connect to the qss service connection
     */
    public Single<String> init() {
        //
        if (mServiceHost == null) {
            mLogger.log(Level.WARNING, "Hub URL is not defined, Skipping ..");
            return Single.just("Init Failed");
        }
        //
        return Single.create(new SingleOnSubscribe<String>() {

            @Override
            public void subscribe(SingleEmitter<String> emitter) throws Exception {
                QssUser mUser = getCurrentUser().blockingGet();
                //
                mLogger.log(Level.INFO, "QSS: Init Hub Connection Service, URL = " + mServiceHost + serviceEndpoint + "?uid=" + mUser.getId());
                //
                if (hubConnection != null && hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
                    mLogger.log(Level.INFO, "QSS: Hub Connection Is already initialized,  ");
                    emitter.onSuccess(hubConnection.getConnectionId());
                    return;
                }
                //
                HttpHubConnectionBuilder builder = HubConnectionBuilder.create(mServiceHost + serviceEndpoint + "?agent=javaapps&uid=" + mUser.getId()+"&name="+mUser.getName())
                        .withAccessTokenProvider(Single.defer(() -> {
                            // Your logic here.
                            if (authToken!= null && authToken.getValue() != null)
                                return  Single.just(authToken.getValue());
                            //
                            return loadToken();
                        }));

                try {

                    //
                    hubConnection = builder.build();

                    connectionStartObserver = new CompletableObserver() {

                        @Override
                        public void onComplete() {
                            //
                            connectionStateObserver.onNext(HubConnectionState.CONNECTED);
                            //
                            bindEventListeners();
                            mLogger.log(Level.INFO, "QSS: Hub Connection Started. ");
                            //
                            emitter.onSuccess(hubConnection.getConnectionId());
                        }

                        @Override
                        public void onError(Throwable ex) {
                            //
                            connectionStateObserver.onNext(HubConnectionState.DISCONNECTED);
                            mLogger.log(Level.SEVERE, "Hub Start Error => " + ex.getMessage());
                            //
                            //emitter.onError(ex);
                            init().doOnError(error -> {
                                mLogger.log(Level.INFO,"Service disconnected, reconnecting...");
                                init().subscribe();
                            }).subscribe();
                        }

                        @Override
                        public void onSubscribe(Disposable arg0) {
                            // TODO Auto-generated method stub
                        }
                    };
                    //
                    hubConnection.start().subscribe(connectionStartObserver);

                    //
                    hubConnection.onClosed(new OnClosedCallback() {
                        @Override
                        public void invoke(Exception arg0) {
                            // TODO Auto-generated method stub
                            connectionStateObserver.onNext(HubConnectionState.DISCONNECTED);
                            // Try start again
                            mLogger.log(Level.INFO,"Service disconnected, reconnecting...");
                            init().doOnError(error -> {
                                mLogger.log(Level.INFO,"Service disconnected, reconnecting...");
                                init().subscribe();
                            }).subscribe();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }

                mLogger.log(Level.INFO, "QSS: Start Hub Connection Exit.. ");
            }
        });

    }

    private void bindEventListeners() {
        //
        hubConnection.on("onConnected", (payload) -> {
            //

            mLogger.log(Level.INFO, "QSS: onConnect => " + payload);

        }, String.class);
        //
        hubConnection.on("onData", (payload) -> {
            //

            mLogger.log(Level.INFO, "QSS: Received Data => " + mGson.toJson(payload));
            try {
                SsData data = mGson.fromJson(payload, SsData.class);

                switch (data.action) {
                    //
                    case QssEvents.OnlineUsers: {
                        //
                        mLogger.log(Level.INFO, "QSS: Online Users => " + data.getData());
                        List<QssUser> users = mGson.fromJson(
                                data.getData(), new TypeToken<List<QssUser>>() {
                                }.getType());
                        //

                        qssService.updateOnlineUsers( new HashSet (users));
                    }
                    break;
                    //
                    case QssEvents.UserOnline: {
                        //
                        mLogger.log(Level.INFO, "QSS: New User has Joined => " + data.getData());
                        QssUser user = mGson.fromJson(data.getData(), QssUser.class);
                        //
                        userOnlineObserver.onNext(user);
                        //
                        qssService.addOrUpdateOnlineUser(user);
                    }
                    break;
                    //
                    case QssEvents.UserOffline: {
                        //
                        mLogger.log(Level.INFO, "QSS: User has Left => " + data.getData());
                        QssUser usr = mGson.fromJson(data.getData(), QssUser.class);
                        //
                        userOfflineObserver.onNext(usr);
                        //
                        qssService.removeOnlineUser(usr);
                    }
                    break;
                    case QssEvents.Payload:
                        mLogger.log(Level.INFO, "QSS: Payload Received => " + data.getData());
                        try {
                            SsData content = (SsData) mGson.fromJson(payload, SsData.class);
                            //
                            qssContentObserver.onNext(content);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;

                    default:
                        break;
                }
            } catch (Exception e) {
                mLogger.log(Level.INFO, "QSS: Event received but could not be processed, => " + e.getMessage());
                e.printStackTrace();
            }


        }, String.class);

        //
        hubConnection.on("onNotification", (payload) -> {
            //
            mLogger.log(Level.INFO, "QSS: Received Notification " + payload);
            //
            SsNotification nn = mGson.fromJson(payload, QssAlert.class);
            //
            qssNotificationsObserver.onNext(nn);
        }, String.class);
    }

    private Single<String> loadToken(){
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> emitter) throws Exception {
                //
                QssUser user = getCurrentUser().blockingGet();

                HttpHeaders headers = new HttpHeaders();
                headers.set("grant_type","client_secret");
                headers.set("client_secret",user.getClientSecret());
                headers.set("client_id",user.getClientId());
                headers.set("did","");
                headers.set("os","JVM ¦ "+System.getProperty("java.runtime.version"));
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

                MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                map.add("grant_type","client_secret");
                map.add("client_id",user.getClientId());
                map.add("client_secret",user.getClientSecret());
                map.add("did","-");
                map.add("os","JVM ¦ "+System.getProperty("java.runtime.version"));

                HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
                try {

                    RestTemplate restTemplate = new RestTemplate();


                    ResponseEntity<String> responseEntity = restTemplate.postForEntity(mServiceHost + "/token",
                            entity, String.class);


                    JsonObject payload = JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();

                    if (payload.isJsonObject() && payload.getAsJsonObject().has("status") &&
                            payload.getAsJsonObject().get("status").getAsInt() == 1) {
                        //
                        authToken = mGson.fromJson(payload.getAsJsonObject().get("data")
                                .getAsJsonObject(), QssToken.class);
                        //
                        emitter.onSuccess(authToken.getValue());
                    } else {
                        emitter.onError(new Exception(payload.getAsJsonObject().get("message").getAsString()));
                    }


                }catch (Exception ex){
                    mLogger.info(ex.getMessage());
                    //
                    emitter.onError(ex);
                }
            }
        });
    }

    @Override
    public Single<QssUser> getCurrentUser() {
        return Single.just(mCurrentUser);
    }

    public Single<Boolean> sendDirectData(SsData message, String recId) {

//
        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<Boolean> emitter) throws Throwable {
                if (hubConnection == null || hubConnection.getConnectionState() != HubConnectionState.CONNECTED) {
                    mLogger.log(Level.WARNING, "Direct Message  sending is failing. Hub is not connected");
                    //mLogger.log(Level.WARNING, "Attempt reconnect ..");
                    // Connect first..
//                    init((JsonObject) authToken).subscribeOn(Schedulers.io())
//                            .doOnError(err -> {
//                                // Ignore
//                                mLogger.log(Level.SEVERE, "Direct Message Could NOT be sent. Attempt to reconnect hub was not successful");
//                                emitter.onSuccess(false);
//                            }).subscribe(ess -> {
//                                //
//                                if (ess.length() > 0) {
//                                    mLogger.log(Level.INFO, "QSS: Attempting to resend Direct Message  after reconnect ..");
//                                    sendDirectData(message,recId).subscribe(observer);
//                                } else {
//                                    mLogger.log(Level.SEVERE, "Direct Message  Could NOT be sent. Reconnect failed,");
//                                    emitter.onSuccess(false);
//                                }
//                            });
                    emitter.onError(new ConnectException("QSS Service Not Connected. Connect then try again"));
                    return;
                }

                // Continue ..
                //"{\"rec\":\"ESB_BACKEND\",\"event\":\"message\",\"kind\":\"Text\",\"content\":\"234234\"}"
                try {
                    message.setSenderId(mCurrentUser.getId());
                    //message.setAction("OnData");
                    hubConnection.invoke(String.class,"Send",recId, mGson.toJson(message))
                            .subscribe(new BiConsumer<String, Throwable>() {
                        @Override
                        public void accept(String s, Throwable throwable) throws Throwable {
                            if(throwable != null){

                                emitter.onSuccess(false);

                                mLogger.log(Level.SEVERE, "Direct Message NOT sent " + throwable.getMessage(), throwable);

                            }else{

                                // OR
                                //hubConnection.send("Send", recId, message);

                                emitter.onSuccess(true);

                                mLogger.log(Level.INFO, "QSS: Message Sent Successfully, "+s);
                                return;
                            }
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                    mLogger.log(Level.SEVERE, "Pushing Direct Content Failed " + e.getMessage(), e);

                    emitter.onSuccess(false);
                }
            }
        });
    }

    @Override
    public Single<Boolean> sendGroupData(SsData data, String groupId) {


        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<Boolean> emitter) throws Throwable {
                if (hubConnection == null || hubConnection.getConnectionState() != HubConnectionState.CONNECTED) {
                    mLogger.log(Level.WARNING, "Alert sending is failing. Hub is not connected");
                    //mLogger.log(Level.WARNING, "Attempt reconnect ..");
                    // Connect first..
//                    init((JsonObject) authToken).subscribeOn(Schedulers.io())
//                            .doOnError(err -> {
//                                // Ignore
//                                mLogger.log(Level.SEVERE, "Group Message Could NOT be sent. Attempt to reconnect hub was not successful");
//                                emitter.onSuccess(false);
//                            }).subscribe(ess -> {
//                                //
//                                if (ess.length() > 0) {
//                                    mLogger.log(Level.INFO, "QSS: Attempting to resend group message after reconnect ..");
//                                    sendGroupData(data,groupId).subscribe();
//                                } else {
//                                    mLogger.log(Level.SEVERE, "Group message Could NOT be sent. Reconnect failed,");
//                                    emitter.onSuccess(false);
//                                }
//                            });
                    emitter.onError(new ConnectException("QSS Service Not Connected. Connect then try again"));
                    return ;
                }

                // Continue ..

                try {

                    data.setSenderId(mCurrentUser.getId());
                    //data.setAction("OnData");
                    hubConnection.invoke(String.class, "sendToGroup",groupId,  mGson.toJson(data)).subscribe(new BiConsumer<String, Throwable>() {
                        @Override
                        public void accept(String s, Throwable throwable) throws Throwable {

                            if(throwable == null){

                                //
                                emitter.onSuccess(true);
                                //
                                mLogger.log(Level.INFO, "QSS: Group Message Sent Successfully, "+s);

                            }else{

                                //
                                emitter.onSuccess(false);
                                //
                                mLogger.log(Level.SEVERE, "Group Message NOT sent " + throwable.getMessage(), throwable);

                            }
                        }
                    });

                    // OR
                    // hubConnection.send("send", recId, payload);
                } catch (Exception e) {
                    e.printStackTrace();

                    emitter.onSuccess(false);
                    mLogger.log(Level.SEVERE, "Pushing Group Content Failed " + e.getMessage(), e);
                }
            }
        });
    }

    @Override
    public io.reactivex.rxjava3.core.Observable<SsData> subscribeForData() {
        return qssContentObserver;
    }

    @Override
    public io.reactivex.rxjava3.core.Observable<? extends SsNotification> subscribeForNotifications() {
        return qssNotificationsObserver;
    }
    @Override
    public Single<String> pushAlert(String recId, String title,String content,String category) {

        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<String> emitter) throws Throwable {
                if (hubConnection == null || hubConnection.getConnectionState() != HubConnectionState.CONNECTED) {
                    mLogger.log(Level.WARNING, "Alert sending is failing. Hub is not connected");

                    emitter.onError(new ConnectException("QSS Service Not Connected. Connect then try again"));
                    return;
                }

                JsonObject data = new JsonObject();
                data.addProperty("content",content);
                data.addProperty("category",category);
                data.addProperty("title",title);
                // Continue ..

                try {
                    hubConnection.invoke(String.class,"notify","",recId, data.toString())
                            .subscribe(new BiConsumer<String, Throwable>() {
                        @Override
                        public void accept(String s, Throwable throwable) throws Throwable {

                            if(throwable == null){
                                emitter.onSuccess(s);
                                mLogger.log(Level.INFO, "QSS: Pushing Alert Success, "+s);
                            }else{

                                mLogger.log(Level.INFO, "QSS: Pushing Alert FAILED, ");
                                //mLogger.log(Level.SEVERE, "Pushing Alert Failed : " + throwable.getMessage(), throwable);

                                emitter.onError(throwable);
                            }
                        }
                    });

                    // OR
                    // hubConnection.send("send", recId, payload);

                } catch (Exception e) {
                    e.printStackTrace();
                    mLogger.log(Level.SEVERE, "Pushing Alert Failed " + e.getMessage(), e);

                    emitter.onError(e);
                }
            }
        });
    }
    @Override
    public Single<String> pushAlertToAll(SsData content) {

        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<String> emitter) throws Throwable {

                if (hubConnection == null || hubConnection.getConnectionState() != HubConnectionState.CONNECTED) {
                    mLogger.log(Level.WARNING, "Alert sending is failing. Hub is not connected");
//                    mLogger.log(Level.WARNING, "Attempt reconnect ..");
//                    // Connect first..
//                    init((JsonObject) authToken).subscribeOn(Schedulers.io())
//                            .doOnError(err -> {
//                                // Ignore
//                                mLogger.log(Level.SEVERE, "Alert Could NOT be sent. Attempt to reconnect hub was not successful");
//
//                                emitter.onSuccess(false);
//                            }).subscribe(ess -> {
//                                //
//                                if (ess.length() > 0) {
//                                    mLogger.log(Level.INFO, "QSS: Attempting to resend group message after reconnect ..");
//                                    pushAlertToAll(content).subscribe();
//                                } else {
//                                    mLogger.log(Level.SEVERE, "Alert Could NOT be sent. Reconnect failed,");
//
//                                    emitter.onSuccess(false);
//                                }
//                            });
                    emitter.onError(new ConnectException("QSS Service Not Connected. Connect then try again"));
                    return;
                }

                // Continue ..

                try {

                    content.setSenderId(mCurrentUser.getId());
                    hubConnection.invoke(String.class, "notifyAll",qssUserId, mGson.toJson(content)).subscribe(new BiConsumer<String, Throwable>() {
                        @Override
                        public void accept(String s, Throwable throwable) throws Throwable {

                            if(throwable == null){

                                mLogger.log(Level.INFO, "QSS: Pushing Alert Success, "+s);

                                emitter.onSuccess(s);
                            }else{

                                emitter.onSuccess(s);
                                mLogger.log(Level.SEVERE, "Pushing Alert Failed : " + throwable.getMessage(), throwable);

                            }
                        }
                    });

                    // OR
                    // hubConnection.send("send", recId, payload);

                } catch (Exception e) {
                    e.printStackTrace();
                    mLogger.log(Level.SEVERE, "Pushing Alert Failed : " + e.getMessage(), e);

                    emitter.onError(e);
                }
            }
        });
    }

    private FileHandler prepareLogger() {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            Path path = Paths.get("logs/qss");
            Files.createDirectories(path);
            // This block configure the logger with handler and formatter
            FileHandler mFileHandler = new FileHandler("logs/qss/kcb-qss-client-" + sdf.format(Calendar.getInstance().getTime()) + ".log");
            mLogger.addHandler(mFileHandler);
            // SimpleFormatter formatter = new SimpleFormatter();
            mFileHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    SimpleDateFormat logTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                    Calendar cal = new GregorianCalendar();
                    cal.setTimeInMillis(record.getMillis());
                    return record.getLevel() + " > " + logTime.format(cal.getTime()) + " || "
                            + record.getSourceClassName().substring(record.getSourceClassName().lastIndexOf(".") + 1,
                            record.getSourceClassName().length())
                            + "." + record.getSourceMethodName() + "() : " + record.getMessage() + "\n";
                }
            });
          return mFileHandler;
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
