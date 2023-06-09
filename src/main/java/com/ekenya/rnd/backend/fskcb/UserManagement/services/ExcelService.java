package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserAccountEntity;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.UsersListRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.payload.UserAppDto;
import com.ekenya.rnd.backend.fskcb.UserManagement.payload.UserAppResponse;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.IUserAccountsRepository;
import com.ekenya.rnd.backend.fskcb.exception.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExcelService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    IUserAccountsRepository IUserAccountsRepository;


    public void save(MultipartFile file) {
//        try {
//            UsersExcelImportResult users = ExcelHelper.excelToUserAccounts(file.getInputStream());
//            userRepository.saveAll(users.getAccounts());
//        } catch (IOException e) {
//            throw new RuntimeException("fail to store excel data: " + e.getMessage());
//        }
    }

    //    public List<UserApp> getAllUsers() {
//        return userRepository.findAll();
//    }
    public UserAppResponse getAllUsers(UsersListRequest model) {
        Sort sort = model.sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(model.sortBy).ascending() : Sort.by(model.sortBy).descending();
        Pageable pageable = PageRequest.of(model.pageNo, model.pageSize, sort);
        //create a pageable instance
        Page<UserAccountEntity> user = IUserAccountsRepository.findAll(pageable);
        //get content from pageable instance
        List<UserAccountEntity> users = user.getContent();
        List<UserAppDto> content = users.stream().map(this::mapToDto).collect(Collectors.toList());
        UserAppResponse userAppResponse = new UserAppResponse();
        userAppResponse.setContent(content);
        userAppResponse.setTotalElements((int) user.getTotalElements());
        userAppResponse.setTotalPages(user.getTotalPages());
        userAppResponse.setPageNo(user.getNumber());
        userAppResponse.setPageSize(user.getSize());
        return userAppResponse;

    }
    //update user
    public UserAppDto updateUser(UserAppDto userAppDto, Long id) {
        log.info("Updating user by id {}", id);
        UserAccountEntity userAccount = IUserAccountsRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userAccount.setFullName(userAppDto.getFirstName()+" "+userAppDto.getLastName());
        userAccount.setEmail(userAppDto.getEmail());
        userAccount.setPhoneNumber(userAppDto.getPhoneNumber());
        userAccount.setStaffNo(userAppDto.getStaffId());
        IUserAccountsRepository.save(userAccount);
        return mapToDto(userAccount);
    }
//test for pdf
    public List<UserAccountEntity> listAll() {
        return IUserAccountsRepository.findAll(Sort.by("email").ascending());
    }

    public UserAppDto getUserById(Long id) {
        log.info("Getting user by id {}", id);
        UserAccountEntity userAccount = IUserAccountsRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));//get user by id if exists
        return mapToDto(userAccount);//map user to dto
    }
    //delete user
//    public void deleteUserById(Long id) {
//        log.info("Deleting User by id {}", id);
//        UserApp userApp = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
//        userRepository.delete(userApp);
//
//
//    }

    public ResponseEntity<?> deleteSystemUser(long id) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();
        try {
            Optional<UserAccountEntity> systemUserOptional = IUserAccountsRepository.findById(id);
            UserAccountEntity systemUser = systemUserOptional.get();
            IUserAccountsRepository.save(systemUser);
            responseObject.put("status", "success");
            responseObject.put("message", "Agent "
                    +systemUser.getStaffNo()+" successfully deleted");
            responseObject.put("data", responseParams);
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }
    }


    private UserAppDto mapToDto(UserAccountEntity userAccount) {

        return modelMapper.map(userAccount, UserAppDto.class);

    }
}
