//package com.deltacode.kcb.DSRModule.models;
//
//import com.deltacode.kcb.utils.Status;
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.Date;
//import java.util.List;
//
//@Getter
//@Setter
//@ToString
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(	name = "tb_zones")
//public class Zone {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String zoneName;
//    @Column(name="status")
//    @Enumerated(EnumType.STRING)
//    private Status status= Status.ACTIVE;
//    @Column(name="created_on")
//    private Date createdOn;
//    @Column(name="updated_on")
//    private Date updatedOn;
//    @Column(name = "created_by")
//    private Long createdBy;
//    @Column(name = "updated_by")
//    private Long updatedBy;
//
//    @OneToMany(mappedBy = "zone")
//    private List<ZoneCoordinates> coordinatesList;
//}
