package com.ekenya.rnd.backend.fskcb.UserManagement.controller;

import com.ekenya.rnd.backend.fskcb.UserManagement.entity.UserAccount;
import com.ekenya.rnd.backend.fskcb.UserManagement.helper.ExcelHelper;
import com.ekenya.rnd.backend.fskcb.UserManagement.payload.UserAppDto;
import com.ekenya.rnd.backend.fskcb.UserManagement.payload.UserAppResponse;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.ExcelService;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.UserService;
import com.ekenya.rnd.backend.fskcb.exception.UserNotFoundException;
import com.ekenya.rnd.backend.fskcb.message.ResponseMessage;
import com.ekenya.rnd.backend.fskcb.utils.AppConstants;
import com.ekenya.rnd.backend.fskcb.utils.Utility;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Api(value = "Upload User  Rest Api")
@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1")
public class UsersVC {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    @Autowired
    ExcelService excelService;
    @ApiOperation(value = "Upload Users Rest  Api")
    @PreAuthorize("hasRole('ADMIN')")

    @PostMapping("/users-upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                excelService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }


    //    @GetMapping("/users")
    //    public ResponseEntity<List<UserApp>> getAllUsers() {
    //        try {
    //            List<UserApp> userApps = excelService.getAllUsers();
    //
    //            if (userApps.isEmpty()) {
    //                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    //            }
    //
    //            return new ResponseEntity<>(userApps, HttpStatus.OK);
    //        } catch (Exception e) {
    //            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    //        }
    //    }
    @ApiOperation(value = "Fetching all Users  Api")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users-all")
    public UserAppResponse getAllUsers(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value ="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir
    ){
        return excelService.getAllUsers(pageNo,pageSize,sortBy,sortDir);
    }
    @GetMapping("/users-forgot-password")
    public String showForgotPasswordForm() {
        return "forgot_password_form";

    }
    @PostMapping("/users-forgot-password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (UserNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }

        return "We have sent a reset password link to your email. Please check.";
    }
    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("davidcharomakuba@gmail.com", "Eclectics Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }
    @GetMapping("/users-reset-password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        UserAccount userAccount = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (userAccount == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "reset_password_form";
    }

    @PostMapping("/users-reset-password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        UserAccount userAccount = userService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (userAccount == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            userService.updatePassword(userAccount, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "message";
    }
    //update user
    @ApiOperation(value = "Update User  Api")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/users-update/{id}")
    public UserAppDto updateUser(@Valid @RequestBody UserAppDto userAppDto, @PathVariable Long id){
        return excelService.updateUser(userAppDto,id);
    }
    //find user by id
    @ApiOperation(value = "Find User by Id  Api")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users-find/{id}")
    public UserAppDto findUserById(@PathVariable Long id){
        return excelService.getUserById(id);
    }

    @RequestMapping(value = "/users-delete-user/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> deleteAgent(@PathVariable long id,HttpServletRequest httpServletRequest) {
        return excelService.deleteSystemUser(id, httpServletRequest);
    }
}
