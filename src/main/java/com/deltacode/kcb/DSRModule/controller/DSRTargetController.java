//package com.deltacode.kcb.DSRModule.controller;
//
//import com.deltacode.kcb.DSRModule.service.DSRService;
//import com.deltacode.kcb.repository.TargetRepository;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController("/dsr-target")
//public class DSRTargetController {
//    private final DSRService dsrTargetService;
//    private final TargetRepository targetRepository;
//
//    public DSRTargetController(DSRService dsrTargetService, TargetRepository targetRepository) {
//        this.dsrTargetService = dsrTargetService;
//        this.targetRepository = targetRepository;
//    }
//
//    @ApiOperation(value = "Assign Target  to dsr")
//    @PostMapping("/assign/{dsrId}/{targetId}")
//    public ResponseEntity<?> assignRoleToUser(@PathVariable Long dsrId,
//                                              @PathVariable Long targetId) {
//        dsrTargetService.assignTarget(dsrId, targetId);
//        return ResponseEntity.ok("Target assigned successfully");
//
//
//    }
//
//    //unassign target
//    @ApiOperation(value = "Unassign Target  to dsr")
//    @DeleteMapping("/unassign/{dsrId}/{targetId}")
//    public ResponseEntity<?> unassignRoleToUser(@PathVariable Long dsrId,
//                                                @PathVariable Long targetId) {
//        dsrTargetService.unassignTarget(dsrId, targetId);
//        return ResponseEntity.ok("Target unassigned successfully");
//
//    }
//    //TODO:Add target thru excel
//}
