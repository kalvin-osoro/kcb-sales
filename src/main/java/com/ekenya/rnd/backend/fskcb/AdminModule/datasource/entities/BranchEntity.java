package com.ekenya.rnd.backend.fskcb.AdminModule.datasource.entities;

import com.ekenya.rnd.backend.utils.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@ApiModel(description = "Bank Branch")
@Entity
@Table(name = "dbo_bank_branches")
public class BranchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    private Status status = Status.ACTIVE;

    private Date dateCreated;
}
