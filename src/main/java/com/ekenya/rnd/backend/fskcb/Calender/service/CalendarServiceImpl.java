package com.ekenya.rnd.backend.fskcb.Calender.service;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringPrincipalInfoRequest;
import com.ekenya.rnd.backend.fskcb.Calender.datasource.entities.MeetingDetailsEntity;
import com.ekenya.rnd.backend.fskcb.Calender.datasource.entities.MeetingType;
import com.ekenya.rnd.backend.fskcb.Calender.datasource.entities.MembersEntity;
import com.ekenya.rnd.backend.fskcb.Calender.datasource.repositories.MeetingDetailsRepository;
import com.ekenya.rnd.backend.fskcb.Calender.datasource.repositories.MembersRepository;
import com.ekenya.rnd.backend.fskcb.Calender.model.*;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBCustomerAppointment;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBAppointmentDateRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRTeamEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRAccountsRepository;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRRegionsRepository;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRTeamsRepository;
import com.ekenya.rnd.backend.fskcb.QSSAdapter.services.IQssService;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Slf4j
@RequiredArgsConstructor

@Service
public class CalendarServiceImpl implements CalendarService {
    private final MeetingDetailsRepository meetingDetailsRepository;
    private final MembersRepository membersRepository;
    private final IDSRAccountsRepository  dsrAccountRepository;
    private final IDSRTeamsRepository  teamsRepository;
    private final IDSRRegionsRepository regionsRepository;
    private final IQssService qssService;
    @Override
    public boolean createCalendar(CalendarRequest model) {
        try {
            if (model == null) {
                return false;
            }
            //create meeting details
            MeetingDetailsEntity meetingDetailsEntity = new MeetingDetailsEntity();
            meetingDetailsEntity.setOwner(model.getOwner());
            meetingDetailsEntity.setDsrId(model.getDsrId());
            meetingDetailsEntity.setMeetingType(model.getMeetingType());
            meetingDetailsEntity.setCustomerName(model.getCustomerName());
            meetingDetailsEntity.setCustomerPhoneNumber(model.getCustomerPhoneNumber());
            meetingDetailsEntity.setVenue(model.getVenue());
            meetingDetailsEntity.setTime(model.getTime());
            meetingDetailsEntity.setLink(model.getLink());
            meetingDetailsEntity.setReason(model.getReason());
            meetingDetailsEntity.setDateOfEvent(model.getDateOfEvent());
            meetingDetailsEntity.setPeriod(model.getPeriod());
            meetingDetailsEntity.setOwnerPhone(model.getOwnerPhone());
            meetingDetailsEntity.setSalesCode(model.getSalesCode());
            meetingDetailsEntity.setProfileCode(model.getProfileCode());
            meetingDetailsEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save
            meetingDetailsRepository.save(meetingDetailsEntity);
            return true;

        }catch (Exception e){
            log.error("Error occurred while creating calendar",e);
        }
        return false;
    }

    @Override
    public boolean assignMembersToAppointment(AssignMembers model) {
        try {
            if (model == null) {
                return false;
            }
            Set<DSRAccountEntity> assignedMembers = null;
            MeetingDetailsEntity meetingDetailsEntity = meetingDetailsRepository.findById(model.getAppointmentId()).get();
            DSRAccountEntity dsrAccountEntity = dsrAccountRepository.findById(model.getDsrId()).get();
            assignedMembers = meetingDetailsEntity.getAssignedMembers();
            assignedMembers.add(dsrAccountEntity);
            meetingDetailsEntity.setAssignedMembers(assignedMembers);
            meetingDetailsRepository.save(meetingDetailsEntity);
            qssService.sendAlert(
                    dsrAccountEntity.getStaffNo(),
                    "New Calendar",
                    "You have been invited to a new calendar by {} " + meetingDetailsEntity.getOwner(),
                    null

            );
            return true;
        } catch (Exception e) {
            log.error("Error occurred while assigning members to appointment",e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getCustomerAppointmentByDSRIdAndDate(AppointmentByDSRRequest model) {
        try {
            if (model == null) {
                return null;
            }
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (MeetingDetailsEntity meetingDetails : meetingDetailsRepository.findAllByDsrIdAndDateOfEvent(model.getDsrId(), model.getDateOfEvent())) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id",meetingDetails.getId());
                node.put("customerName", meetingDetails.getCustomerName());
                node.put("customerPhoneNumber", meetingDetails.getCustomerPhoneNumber());
                node.put("typeOfAppointment", meetingDetails.getIsOnline());
                node.put("appointmentDate", meetingDetails.getDateOfEvent());
                node.put("appointmentTime", meetingDetails.getTime());
                node.put("link", meetingDetails.getLink());
                node.put("duration", meetingDetails.getPeriod());
                node.put("reasonForVisit", meetingDetails.getReason());
                //list of members
                List<String> members = new ArrayList<>();
                for (DSRAccountEntity dsrAccountEntity : meetingDetails.getAssignedMembers()) {
                    members.add(dsrAccountEntity.getStaffNo());
                    members.add(dsrAccountEntity.getFullName());
                    members.add(dsrAccountEntity.getProfileCode());
                    members.add(dsrAccountEntity.getEmail());
                }
                node.put("members", members.toString());

                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading questionnaires", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> getAllAppointments(GetAppointmentDto model) {
        try {
            if (model ==null){
                return null;
            }
            List<ObjectNode>list =new ArrayList<>();
            ObjectMapper objectMapper =new ObjectMapper();

            for (MeetingDetailsEntity meetingDetails : meetingDetailsRepository.findByProfileCode(model.getProfileCode())){
                ObjectNode node = objectMapper.createObjectNode();
                node.put("customerName",meetingDetails.getCustomerName());
                node.put("visitdate",meetingDetails.getDateOfEvent());
                node.put("reasonForVisit",meetingDetails.getReason());
                node.put("createdOn",meetingDetails.getCreatedOn()==null?null : meetingDetails.getCreatedOn().getTime());
                node.put("profileCode",meetingDetails.getProfileCode());
                node.put("owner",meetingDetails.getOwner());
                node.put("region",meetingDetails.getRegion());
                node.put("customerPhone",meetingDetails.getCustomerPhoneNumber());
                node.put("meetingType",meetingDetails.getMeetingType()==null ?null :meetingDetails.getMeetingType().toString());
                if (meetingDetails.getMeetingType()== MeetingType.ONLINE){
                    node.put("meetingLink",meetingDetails.getLink());
                }
                node.put("meetingVenue",meetingDetails.getVenue());
                //members
                List<String> members = new ArrayList<>();
                for (DSRAccountEntity dsrAccountEntity : meetingDetails.getAssignedMembers()) {
                    members.add(dsrAccountEntity.getStaffNo());
                    members.add(dsrAccountEntity.getFullName());
                    members.add(dsrAccountEntity.getProfileCode());
                    members.add(dsrAccountEntity.getEmail());
                }
                node.put("members", members.toString());
                list.add(node);


            }
            return list;

        } catch (Exception e) {
            log.error("something wrong has occured,please try again later");
        }
        return null;
    }
}

