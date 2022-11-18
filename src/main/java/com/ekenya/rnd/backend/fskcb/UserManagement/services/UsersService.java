package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserAccount;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.UserRepository;
import com.ekenya.rnd.backend.fskcb.exception.ResourceNotFoundException;
import com.ekenya.rnd.backend.fskcb.exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class UsersService implements IUsersService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DateFormat dateFormat;

    private ObjectMapper mObjectMapper = new ObjectMapper();

    public boolean updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        UserAccount userAccount = userRepository.findByEmail(email).get();
        if (userAccount != null) {
            userAccount.setResetPasswordToken(token);
            userRepository.save(userAccount);

            return true;
        } else {
            throw new UserNotFoundException("User not found with email " + email);
        }

    }

    public UserAccount getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public boolean updatePassword(UserAccount userAccount, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        userAccount.setPassword(encodedPassword);

        userAccount.setResetPasswordToken(null);
        userRepository.save(userAccount);

        return true;
    }
    public UserAccount findById(Long id) {
        return userRepository.findById(id).orElse(null);//.orElseThrow(() -> new ResourceNotFoundException("UseApp","id", + id));
    }

    @Override
    public UserAccount findByStaffNo(String staffNo) {
        return userRepository.findByStaffNo(staffNo).orElse(null);//
    }

    @Override
    public List<ObjectNode> loadAllUsers() {

        try{
            List<ObjectNode> list = new ArrayList<>();
            for(UserAccount account : userRepository.findAll()){
                ObjectNode node = mObjectMapper.createObjectNode();

                node.put("id",account.getId());
                node.put("name",account.getFullName());
                node.put("staffNo",account.getStaffNo());
                node.put("email",account.getEmail());
                node.put("phoneNo",account.getPhoneNumber());
                node.put("verified",account.getIsVerified());
                node.put("type",account.getAccountType().toString());
                try {
                    if(account.getLastLogin() != null) {
                        node.put("lastLogin", dateFormat.format(account.getLastLogin()));
                    }else{
                        node.put("lastLogin", "");
                    }
                }catch (Exception ex){
                    log.error(ex.getMessage(),ex);
                    node.put("lastLogin", "");
                }
                node.put("lastLocation",account.getLastCoords());
                //
                list.add(node);
            }
            return list;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        return null;
    }


}
