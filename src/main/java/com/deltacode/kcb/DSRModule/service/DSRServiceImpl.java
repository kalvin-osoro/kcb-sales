package com.deltacode.kcb.DSRModule.service;

import com.deltacode.kcb.entity.DSR;
import com.deltacode.kcb.entity.Target;
import com.deltacode.kcb.entity.Team;
import com.deltacode.kcb.exception.MessageResponse;
import com.deltacode.kcb.exception.ResourceNotFoundException;
import com.deltacode.kcb.payload.CommissionsRequest;
import com.deltacode.kcb.payload.DSRDto;
import com.deltacode.kcb.payload.DSRResponse;
import com.deltacode.kcb.repository.DSRRepository;
import com.deltacode.kcb.repository.TargetRepository;
import com.deltacode.kcb.repository.TeamRepository;
import com.deltacode.kcb.utils.Status;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j

@Service
public class DSRServiceImpl implements DSRService {
    private final String SUCCESS = "success";
    private final DSRRepository dsrRepository;
    private final ModelMapper modelMapper;
    private final TeamRepository teamRepository;
    private final TargetRepository targetRepository;

    public DSRServiceImpl(DSRRepository dsrRepository, ModelMapper modelMapper, TeamRepository teamRepository, TargetRepository targetRepository) {
        this.dsrRepository = dsrRepository;
        this.modelMapper = modelMapper;
        this.teamRepository = teamRepository;
        this.targetRepository = targetRepository;
    }


    @Override
    public DSRDto createDSR(long teamId, DSRDto dsrDto) {
        log.info("Creating DSR");
        DSR dsr = mapToEntity(dsrDto);
        //set team to dsr
        // retrieve team entity by id
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new ResourceNotFoundException("Team", "id", teamId));
        //set team to dsr entity
        dsr.setTeam(team);
        //dsr entity to DB
        DSR newDSR = dsrRepository.save(dsr);
        return mapToDto(newDSR);
    }

    @Override
    public List<DSRDto> getDSRByTeamId(long teamId) {
        log.info("Getting all DSRs by team id = {}", teamId);

//retrieve dsr by teamId
        List<DSR> dsr = dsrRepository.findByTeamId(teamId);
        //convert list of dsr entities to list of dsr dto's
        return dsr.stream().map(this::mapToDto).collect(Collectors.toList());

    }

    @Override
    public DSRResponse getAllDSRs(int pageNo, int pageSize, String sortBy, String sortDir) {
        log.info("Getting all DSRs");
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNo, pageSize, sort);
        //create a pageable instance
        Page<DSR> dsr=dsrRepository.findAll(pageable);
        //get content for page object
        List<DSR> dsrList=dsr.getContent();
        List<DSRDto> content=dsrList.stream().map(dsr1 -> mapToDto(dsr1)).collect(Collectors.toList());
        DSRResponse dsrResponse=new DSRResponse();
        dsrResponse.setContent(content);
        dsrResponse.setPageNo(dsr.getNumber());
        dsrResponse.setPageSize(dsr.getSize());
        dsrResponse.setTotalElements((int) dsr.getTotalElements());
        dsrResponse.setTotalPages(dsr.getTotalPages());
        dsrResponse.setLast(dsr.isLast());
        return dsrResponse;
    }

    @Override
    public DSRDto getDSRById(Long teamId, Long dsrId) {
        log.info("Getting DSR by id = {}", dsrId);
        //retrieve team by id
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new ResourceNotFoundException("Team", "id", teamId));
        //retrieve dsr by id
        DSR dsr = dsrRepository.findById(dsrId).orElseThrow(
                () -> new ResourceNotFoundException("DSR", "id", dsrId));
        //check if dsr belongs to team
        if (dsr.getTeam().getId() != teamId) {
            throw new ResourceNotFoundException("DSR", "id", dsrId);
        }
        return mapToDto(dsr);
    }

    @Override
    public DSRDto updateDSR(Long teamId, long dsrId, DSRDto dsrDto) {
        log.info("Updating DSR by id = {} and Team id = {}", dsrId, teamId);
        //retrieve team by id
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new ResourceNotFoundException("Team", "id", teamId));
        //retrieve dsr by id
        DSR dsr = dsrRepository.findById(dsrId).orElseThrow(
                () -> new ResourceNotFoundException("DSR", "id", dsrId));
        //check if dsr belongs to team
        if (dsr.getTeam().getId() != teamId) {
            throw new ResourceNotFoundException("DSR", "id", dsrId);
        }
        //update dsr
        dsr.setUsername(dsrDto.getUsername());
        dsr.setFirstName(dsrDto.getFirstName());
        dsr.setLastName(dsrDto.getLastName());
        dsr.setPhoneNumber(dsrDto.getPhoneNumber());
        dsr.setEmail(dsrDto.getEmail());
        dsr.setMiddleName(dsrDto.getMiddleName());
        dsr.setGender(dsrDto.getGender());
        dsr.setIdNumber(dsrDto.getIdNumber());
        dsr.setPassword(dsrDto.getPassword());
        dsr.setDateOfBirth(dsrDto.getDateOfBirth());
        dsr.setStatus(Status.ACTIVE);
        DSR updatedDSR = dsrRepository.save(dsr);
        return mapToDto(updatedDSR);
    }

    @Override
    public void deleteDSR(Long teamId, Long dsrId) {
        log.info("Deleting DSR by id = {} and Team id = {}", dsrId, teamId);
        //retrieve team by id
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new ResourceNotFoundException("Team", "id", teamId));
        //retrieve dsr by id
        DSR dsr = dsrRepository.findById(dsrId).orElseThrow(
                () -> new ResourceNotFoundException("DSR", "id", dsrId));
        //check if dsr belongs to team
        if (dsr.getTeam().getId() != teamId) {
            throw new ResourceNotFoundException("DSR", "id", dsrId);
        }
        dsrRepository.delete(dsr);

    }



    @Override
    public ResponseEntity<?> getCommissionByDSRId(Long teamId, Long dsrId, CommissionsRequest commissionsRequest) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        LinkedHashMap<String,Object> responseParams = new LinkedHashMap<>();
        log.info("Getting commission by DSR id = {} and Team id = {}", dsrId, teamId);
        try {
            //retrieve team by id
            Team team = teamRepository.findById(teamId).orElseThrow(
                    () -> new ResourceNotFoundException("Team", "id", teamId));
            //retrieve dsr by id
            DSR dsr = dsrRepository.findById(dsrId).orElseThrow(
                    () -> new ResourceNotFoundException("DSR", "id", dsrId));
            //check if dsr belongs to team
            if (dsr.getTeam().getId() != teamId) {
                throw new ResourceNotFoundException("DSR", "id", dsrId);
            }
            //Transaction data should come from CRM Api this just a temporary solution
            responseParams.put("week 1",4304.32);
            responseParams.put("week 2",503.50);
            responseParams.put("week 3",2005.30);
            responseParams.put("week 4",3000.25);

            responseObject.put("status", SUCCESS);
            responseObject.put("message", "Transactions commissions available");
            responseObject.put("data", responseParams);
            return ResponseEntity.ok().body(responseObject);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public ResponseEntity<?> assignTarget(Long dsrId, Long targetId) {
        LinkedHashMap<String,Object>responseObject=new LinkedHashMap<>();
        LinkedHashMap<String,Object>responseParams=new LinkedHashMap<>();

        try {
          //assign target to dsr
            DSR dsr=dsrRepository.findById(dsrId).orElseThrow(()->new ResourceNotFoundException("DSR","id",dsrId));
            Target target=targetRepository.findById(targetId).orElseThrow(()->new ResourceNotFoundException("Target","id",targetId));
            dsr.setTarget(target);
            dsrRepository.save(dsr);
            responseObject.put("status",SUCCESS);
            responseObject.put("message","Target assigned successfully");
            responseObject.put("data",responseParams);
            return ResponseEntity.ok().body(responseObject);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }
    }

    @Override
    public ResponseEntity<?> unassignTarget(Long dsrId, Long targetId) {
        LinkedHashMap<String,Object>responseObject=new LinkedHashMap<>();
        LinkedHashMap<String,Object>responseParams=new LinkedHashMap<>();

        try {
            //unassign target from dsr
            DSR dsr=dsrRepository.findById(dsrId).orElseThrow(()->new ResourceNotFoundException("DSR","id",dsrId));
            Target target=targetRepository.findById(targetId).orElseThrow(()->new ResourceNotFoundException("Target","id",targetId));
            dsr.setTarget(null);
            dsrRepository.save(dsr);
            responseObject.put("status",SUCCESS);
            responseObject.put("message","Target unassigned successfully");
            responseObject.put("data",responseParams);
            return ResponseEntity.ok().body(responseObject);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }
    }

    //convert entity to dto
    private DSRDto mapToDto(DSR dsr) {

        return modelMapper.map(dsr, DSRDto .class);

    }
    //convert dto to entity
    private DSR mapToEntity(DSRDto dsrDto) {

        return modelMapper.map(dsrDto, DSR.class);

    }
}
