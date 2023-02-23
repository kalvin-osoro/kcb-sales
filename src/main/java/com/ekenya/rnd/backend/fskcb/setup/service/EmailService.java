package com.ekenya.rnd.backend.fskcb.setup.service;

import com.ekenya.rnd.backend.fskcb.setup.model.EmailWrapper;

public interface EmailService {

    boolean addNewEmail(EmailWrapper emailWrapper);
}
