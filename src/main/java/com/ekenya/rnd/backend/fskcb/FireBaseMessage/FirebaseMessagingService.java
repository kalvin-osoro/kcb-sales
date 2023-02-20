package com.ekenya.rnd.backend.fskcb.FireBaseMessage;

import com.ekenya.rnd.backend.fskcb.FireBaseMessage.Wrapper.Note;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirebaseMessagingService {

    private final FirebaseMessaging firebaseMessaging;



    public String sendNotification(Note note, String token) throws FirebaseMessagingException {

        Notification notification = Notification
                .builder()
                .setTitle(note.getSubject())
                .setBody(note.getContent())
                .build();

        Message message = Message
                .builder()
                .setToken(token)
                .setNotification(notification)
                .putAllData(note.getData())
                .build();

        return firebaseMessaging.send(message);
    }

    public void sendNotification() {
        Notification notification = Notification
                .builder()
                .setTitle("title")
                .setBody("body")
                .build();
        Message message = Message
                .builder()
                .setToken("token")
                .setNotification(notification)
                .build();
    }
}