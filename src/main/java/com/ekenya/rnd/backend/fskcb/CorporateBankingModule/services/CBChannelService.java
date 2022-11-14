package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services;

import com.ekenya.rnd.backend.fskcb.entity.CustomerAppointments;
import com.ekenya.rnd.backend.fskcb.payload.CustomerAppointementRequest;
import com.ekenya.rnd.backend.utils.Status;
import com.ekenya.rnd.backend.utils.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor

@Service
public class CBChannelService implements ICBChannelService {

    @Override
    public List<?> getAllCustomerAppointment() {
        return null;
    }

    @Override
    public boolean createCustomerAppointment(CustomerAppointementRequest customerAppointementRequest) {
        return false;
    }

    @Override
    public boolean editCustomerAppointment(CustomerAppointementRequest customerAppointementRequest) {
        return false;
    }

    @Override
    public boolean deleteCustomerAppointment(long id) {
        return false;
    }

    @Override
    public List<?> getAllAppointmentByDsrId(long dsrId) {
        return null;
    }
}
