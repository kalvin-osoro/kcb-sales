package com.deltacode.kcb.service.impl;

import com.deltacode.kcb.entity.Constituency;
import com.deltacode.kcb.entity.County;
import com.deltacode.kcb.entity.Town;
import com.deltacode.kcb.entity.Ward;
import com.deltacode.kcb.exception.FieldPortalApiException;
import com.deltacode.kcb.exception.MessageResponse;
import com.deltacode.kcb.exception.ResourceNotFoundException;
import com.deltacode.kcb.payload.*;
import com.deltacode.kcb.repository.ConstituencyRepository;
import com.deltacode.kcb.repository.CountyRepository;
import com.deltacode.kcb.repository.TownRepository;
import com.deltacode.kcb.repository.WardRepository;
import com.deltacode.kcb.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Slf4j
public class LocationServiceImpl implements LocationService {
    private final CountyRepository countyRepository;
    private  final ConstituencyRepository constituencyRepository;
    private final WardRepository wardRepository;
    private  final TownRepository townRepository;
    private final ModelMapper modelMapper;

    public LocationServiceImpl(CountyRepository countyRepository,
                               ConstituencyRepository constituencyRepository,
                               WardRepository wardRepository,
                               TownRepository townRepository, ModelMapper modelMapper) {
        this.countyRepository = countyRepository;
        this.constituencyRepository = constituencyRepository;
        this.wardRepository = wardRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public CountyDto createCounty(CountyDto countyDto) {
        log.info("Creating county");
        County county =mapToEntity(countyDto);
        County newCounty =  countyRepository.save(county);
        return mapToDto(newCounty);


    }

    @Override
    public CountyResponse getAllCounties(int pageNo, int pageSize, String sortBy, String sortDir) {
        log.info("Getting all counties");
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNo, pageSize, sort);
        //create a pageable instance
        Page<County> counties=countyRepository.findAll(pageable);
        //get content for page object
        List<County> countyList=counties.getContent();
        List<CountyDto> content=countyList.stream().map(county -> mapToDto(county)).collect(Collectors.toList());
        CountyResponse countyResponse =new CountyResponse();
        countyResponse.setContent(content);
        countyResponse.setTotalPages(counties.getTotalPages());
        countyResponse.setTotalElements((int) counties.getTotalElements());
        countyResponse.setPageSize(counties.getSize());
        countyResponse.setPageNo(counties.getTotalPages());
        countyResponse.setLast(counties.isLast());
        return countyResponse;
    }

    @Override
    public CountyDto getCountyById(Long id) {
        log.info("Getting county by id = {}", id);
        County county = countyRepository.findById(id).orElseThrow( () -> new RuntimeException("County not found"));
        return modelMapper.map(county, CountyDto.class);
    }

    @Override
    public ResponseEntity<?> updateCounty(CountyDto countyDto, Long id) {
        HashMap<String, Object> responseObject = new HashMap<>();
        HashMap<String,Object>responseParams=new HashMap<>();
        try {
            log.info("Updating county with id = {}", id);
            County county = countyRepository.findById(id).orElseThrow( () -> new RuntimeException("County not found"));
            county.setCountyName(countyDto.getCountyName());
            county.setCountyCode(countyDto.getCountyCode());
            county.setDescription(countyDto.getDescription());
            County newCounty = countyRepository.save(county);
            responseObject.put("status", "success");//set status
            responseObject.put("message", "county "
                    +countyDto.getCountyName()+" successfully updated");//set message
            responseObject.put("data", responseParams);
            mapToDto(newCounty);
            return ResponseEntity.ok(responseObject);
        } catch (Exception e) {
            responseObject.put("status", "error");//set status
            responseObject.put("message", "county "
                    +countyDto.getCountyName()+" could not be updated");//set message
            responseObject.put("data", responseParams);
            return ResponseEntity.badRequest().body(responseObject);
        }

    }

    @Override
    public void deleteCountyById(Long id) {
        log.info("Deleting county with id = {}", id);
        //check if county exists
        countyRepository.findById(id).orElseThrow( () -> new RuntimeException("County not found"));
        countyRepository.deleteById(id);

    }


    //convert entity to dto
    private CountyDto mapToDto(County county) {

        return modelMapper.map(county, CountyDto .class);
    }
    //convert dto to entity
    private County mapToEntity(CountyDto countyDto) {

        return modelMapper.map(countyDto, County.class);

    }
    @Override
    public ConstituencyDto createConstituency(long countyId, ConstituencyDto constituencyDto) {
        log.info("Creating constituency");
        Constituency constituency =mapToEntity(constituencyDto);
        // retrieve county entity by id
        County county = countyRepository.findById(countyId).orElseThrow(
                () -> new ResourceNotFoundException("County", "id", countyId));

        // set county to constituency entity
        constituency.setCounty(county);

        // constituency entity to DB
        Constituency newConstituency =  constituencyRepository.save(constituency);

        return mapToDto(newConstituency);
    }

    @Override
    public List<ConstituencyDto> getConstituencyByCountyId(long countyId) {
        log.info("Getting all constituencies by county id = {}", countyId);
        // retrieve constituency by countyId
        List<Constituency> constituencies = constituencyRepository.findByCountyId(countyId);

        // convert list of constituency entities to list of constituency dto's
        return constituencies.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ConstituencyResponse getAllConstituency(int pageNo, int pageSize, String sortBy, String sortDir) {
        log.info("Getting all constituencies");
        Sort sort=sortDir.equalsIgnoreCase(Sort
                .Direction
                .ASC
                .name())?Sort.by(sortBy)
                .ascending():Sort.
                by(sortBy).
                descending();
        Pageable pageable= PageRequest.of(pageNo, pageSize, sort);
        //create a pageable instance
        Page<Constituency> constituency=constituencyRepository.findAll(pageable);
        //get content for page object
        List<Constituency> constituencyList=constituency.getContent();
        List<ConstituencyDto> content=constituencyList.stream().map(constituency1 -> mapToDto(constituency1)).collect(Collectors.toList());
        ConstituencyResponse constituencyResponse=new ConstituencyResponse();
        constituencyResponse.setContent(content);
        constituencyResponse.setPageNo(constituency.getNumber());
        constituencyResponse.setPageSize(constituency.getSize());
        constituencyResponse.setTotalElements((int) constituency.getTotalElements());
        constituencyResponse.setTotalPages(constituency.getTotalPages());
        constituencyResponse.setLast(constituency.isLast());
        return constituencyResponse;
    }

    @Override
    public ConstituencyDto getConstituencyById(Long countyId, Long constituencyId) {
        log.info("Getting constituency by id ={}", constituencyId);
        // retrieve county entity by id
        County county = countyRepository.findById(countyId).orElseThrow(
                () -> new ResourceNotFoundException("County", "id", countyId));

        // retrieve constituency by id
        Constituency constituency = constituencyRepository.findById(constituencyId).orElseThrow(() ->
                new ResourceNotFoundException("Constituency", "id", constituencyId));

        if(!constituency.getCounty().getId().equals(county.getId())){
            throw new FieldPortalApiException(HttpStatus.BAD_REQUEST, "Constituency does not belong to a county");
        }

        return mapToDto(constituency);
    }

    @Override
    public ConstituencyDto updateConstituency(Long countyId, long constituencyId, ConstituencyDto constituencyDto) {
        log.info("Updating constituency by id ={} and County id = {}", constituencyId, countyId);
        // retrieve county entity by id
        County county = countyRepository.findById(countyId).orElseThrow(
                () -> new ResourceNotFoundException("County", "id", countyId));
        //retrieve constituency by id
        Constituency constituency = constituencyRepository.findById(constituencyId).orElseThrow(() ->
                new ResourceNotFoundException("Constituency", "id", constituencyId));
        if (!constituency.getCounty().getId().equals(county.getId())) {
            throw new FieldPortalApiException(HttpStatus.BAD_REQUEST, "Constituency does not belong to a county");
        }
        constituency.setConstituencyName(constituencyDto.getConstituencyName());
        constituency.setConstituencyCode(constituencyDto.getConstituencyCode());
        constituency.setDescription(constituencyDto.getDescription());
        Constituency updatedConstituency = constituencyRepository.save(constituency);
        return mapToDto(updatedConstituency);
    }

    @Override
    public void deleteConstituency(Long countyId, Long constituencyId) {
        log.info("Deleting constituency by id ={} and County id = {}", constituencyId, countyId);
        // retrieve county entity by id
        County county = countyRepository.findById(countyId).orElseThrow(
                () -> new ResourceNotFoundException("County", "id", countyId));
        //retrieve constituency by id
        Constituency constituency = constituencyRepository.findById(constituencyId).orElseThrow(() ->
                new ResourceNotFoundException("Constituency", "id", constituencyId));
        if (!constituency.getCounty().getId().equals(county.getId())) {
            throw new FieldPortalApiException(HttpStatus.BAD_REQUEST, "Constituency does not belong to a county");
        }
        constituencyRepository.delete(constituency);

    }

    //convert entity to dto
    private ConstituencyDto mapToDto(Constituency constituency) {

        return modelMapper.map(constituency, ConstituencyDto .class);
    }
    //convert dto to entity
    private Constituency mapToEntity(ConstituencyDto constituencyDto) {

        return modelMapper.map(constituencyDto, Constituency.class);

    }
    @Override
    public WardDto createWard(long constituencyId, WardDto wardDto) {
        log.info("Creating ward");
        Ward ward =mapToEntity(wardDto);
        //set constituency to ward
        // retrieve constituency entity by id
        Constituency constituency = constituencyRepository.findById(constituencyId).orElseThrow(
                () -> new ResourceNotFoundException("Constituency", "id", constituencyId));

        // set constituency to ward entity
        ward.setConstituency(constituency);
        // ward entity to DB
        Ward newWard =  wardRepository.save(ward);
        return mapToDto(newWard);
    }

    @Override
    public WardResponse getAllWards(int pageNo, int pageSize, String sortBy, String sortDir) {
        log.info("Getting all wards");
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNo, pageSize, sort);
        //create a pageable instance
        Page<Ward> ward=wardRepository.findAll(pageable);
        //get content for page object
        List<Ward> WardList=ward.getContent();
        List<WardDto> content=WardList.stream().map(ward1 -> mapToDto(ward1)).collect(Collectors.toList());
        WardResponse wardResponse=new WardResponse();
        wardResponse.setContent(content);
        wardResponse.setPageNo(ward.getNumber());
        wardResponse.setPageSize(ward.getSize());
        wardResponse.setTotalElements((int) ward.getTotalElements());
        wardResponse.setTotalPages(ward.getTotalPages());
        wardResponse.setLast(ward.isLast());
        return wardResponse;
    }

    @Override
    public List<WardDto> getWardByConstituencyId(long constituencyId) {
        log.info("Getting wards by constituency id {}", constituencyId);
        // retrieve ward by constituencyId
        List<Ward> wards = wardRepository.findByConstituencyId(constituencyId);
        // convert list of ward entities to list of ward dto's
        return wards.stream().map(this::mapToDto).collect(Collectors.toList());

    }

    @Override
    public WardDto getWardById(Long constituencyId, Long wardId) {
        log.info("Getting ward by id {}", wardId);
        // retrieve constituency by id
        Constituency constituency =constituencyRepository.findById(constituencyId).orElseThrow(
                () -> new ResourceNotFoundException("Constituency", "id", constituencyId));
        // retrieve ward by id
        Ward ward = wardRepository.findById(wardId).orElseThrow(
                () -> new ResourceNotFoundException("Ward", "id", wardId));
        // check if ward belongs to constituency
        if(!ward.getConstituency().equals(constituency)){
            throw new ResourceNotFoundException("Ward", "id", wardId);
        }
        return mapToDto(ward);

    }

    @Override
    public WardDto updateWard(Long constituencyId, long wardId, WardDto wardDto) {
        log.info("Updating ward by id {}", wardId);
        Constituency constituency =constituencyRepository.findById(constituencyId).orElseThrow(
                () -> new ResourceNotFoundException("Constituency", "id", constituencyId));
        Ward ward = wardRepository.findById(wardId).orElseThrow(
                () -> new ResourceNotFoundException("Ward", "id", wardId));
        if(!ward.getConstituency().equals(constituency)){
            throw new ResourceNotFoundException("Ward", "id", wardId);
        }
        ward.setWardName(wardDto.getWardName());
        ward.setWardCode(wardDto.getWardCode());
        ward.setDescription(wardDto.getDescription());
        Ward updatedWard = wardRepository.save(ward);
        return mapToDto(updatedWard);
    }

    @Override
    public void deleteWard(Long constituencyId, Long wardId) {
        log.info("Deleting ward by id {}", wardId);
        Constituency constituency =constituencyRepository.findById(constituencyId).orElseThrow(
                () -> new ResourceNotFoundException("Constituency", "id", constituencyId));
        Ward ward = wardRepository.findById(wardId).orElseThrow(
                () -> new ResourceNotFoundException("Ward", "id", wardId));
        if(!ward.getConstituency().equals(constituency)){
            throw new ResourceNotFoundException("Ward", "id", wardId);
        }
        wardRepository.delete(ward);

    }
    //convert entity to dto
    private WardDto mapToDto(Ward ward) {

        return modelMapper.map(ward, WardDto .class);

    }
    //convert dto to entity
    private Ward mapToEntity(WardDto wardDto) {

        return modelMapper.map(wardDto, Ward.class);

    }
    @Override
    public ResponseEntity<?> getAllTowns() {
        HashMap<String, Object> responseObject = new HashMap<>();
        HashMap<String, Object> responseParams = new HashMap<>();
        try {
            List<Town> townList = townRepository.findAll();
            if (townList.isEmpty()) {
                responseObject.put("status", "error");
                responseObject.put("message", "No towns found");
                return ResponseEntity.ok(responseObject);
            } else {
                responseObject.put("status", "success");
                responseObject.put("message", "Towns found");
                responseParams.put("towns", townList);
                responseObject.put("data", responseParams);
            }
            return ResponseEntity.ok(responseObject);
        } catch (Exception e) {
            return ResponseEntity.ok(new MessageResponse(e.getMessage(), "failed"));
        }
    }
}
