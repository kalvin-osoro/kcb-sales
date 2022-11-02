package com.deltacode.kcb.payload;

import com.deltacode.kcb.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalAccountTypeRequest {
    private Long id;
    @NotBlank(message = "Personal account type name is mandatory")
    private String personalAccountTypeName;
    private Status status;
    @NotBlank(message = "User id is mandatory")
    private long createdBy;
}
