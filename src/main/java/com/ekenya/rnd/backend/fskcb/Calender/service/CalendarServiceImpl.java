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
            List<MemberRequest> membersList = model.getMemberRequests();
            for (MemberRequest memberRequest : membersList){
                MembersEntity membersEntity =new MembersEntity();
                membersEntity.setName(memberRequest.getName());
                membersEntity.setEmail(memberRequest.getEmail());
                membersEntity.setSalesCode(memberRequest.getSalesCode());
                membersEntity.setMeetingDetailsEntities((List<MeetingDetailsEntity>) meetingDetailsEntity);
                membersEntity.setPhoneNumber(memberRequest.getPhoneNumber());
                membersRepository.save(membersEntity);
                //send notification to saved members
                qssService.sendAlert(
                        membersEntity.getSalesCode(),
                        "New Calendar",
                        "You have been invited to a new calendar by {} "+meetingDetailsEntity.getOwner(),
                        null
                );

            }
                return true;

        }catch (Exception e){
            log.error("Error occurred while creating calendar",e);
        }
        return false;
    }
}

