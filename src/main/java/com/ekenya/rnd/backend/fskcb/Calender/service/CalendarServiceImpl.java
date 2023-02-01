package com.ekenya.rnd.backend.fskcb.Calender.service;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringPrincipalInfoRequest;
import com.ekenya.rnd.backend.fskcb.Calender.datasource.entities.MeetingDetailsEntity;
import com.ekenya.rnd.backend.fskcb.Calender.datasource.entities.MembersEntity;
import com.ekenya.rnd.backend.fskcb.Calender.datasource.repositories.MeetingDetailsRepository;
import com.ekenya.rnd.backend.fskcb.Calender.datasource.repositories.MembersRepository;
import com.ekenya.rnd.backend.fskcb.Calender.model.CalendarRequest;
import com.ekenya.rnd.backend.fskcb.Calender.model.MemberRequest;
import com.ekenya.rnd.backend.fskcb.QSSAdapter.services.IQssService;
import com.ekenya.rnd.backend.utils.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Slf4j
@RequiredArgsConstructor

@Service
public class CalendarServiceImpl implements CalendarService {
    private final MeetingDetailsRepository meetingDetailsRepository;
    private final MembersRepository membersRepository;
    private final IQssService qssService;
    @Override
    public boolean createCalendar(CalendarRequest model) {
        try {
            if (model==null){
                return false;
            }
            //create meeting details
            MeetingDetailsEntity meetingDetailsEntity = new MeetingDetailsEntity();
            meetingDetailsEntity.setOwner(model.getOwner());
            meetingDetailsEntity.setVenue(model.getVenue());
            meetingDetailsEntity.setReason(model.getReason());
            meetingDetailsEntity.setDateOfEvent(model.getDateOfEvent());
            meetingDetailsEntity.setPeriod(model.getPeriod());
            meetingDetailsEntity.setPhoneNumber(model.getPhoneNumber());
            meetingDetailsEntity.setSalesCode(model.getSalesCode());
            meetingDetailsEntity.setProfileCode(model.getProfileCode());
            meetingDetailsEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            meetingDetailsEntity.setProfileCode(model.getProfileCode());
            //save
            meetingDetailsRepository.save(meetingDetailsEntity);
           //add members
            List<MemberRequest> members = model.getMemberRequests();
            if (members!=null){
                for (MemberRequest member:members) {
                    MembersEntity membersEntity = new MembersEntity();
                    membersEntity.setAppointmentId(meetingDetailsEntity.getId());
                    membersEntity.setName(member.getName());
                    membersEntity.setSalesCode(member.getSalesCode());
                    membersEntity.setEmail(member.getEmail());
                    MembersEntity members1 = membersRepository.save(membersEntity);
                    qssService.sendAlert(
                           members1 .getSalesCode(),
                            "New Calendar",
                            "You have been invited to a new calendar by {} "+meetingDetailsEntity.getOwner(),
                            null
                );
                }
            }
                return true;

        }catch (Exception e){
            log.error("Error occurred while creating calendar",e);
        }
        return false;
    }
}

