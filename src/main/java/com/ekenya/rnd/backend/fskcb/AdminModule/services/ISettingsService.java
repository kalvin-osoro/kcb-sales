package com.ekenya.rnd.backend.fskcb.AdminModule.services;

import com.ekenya.rnd.backend.fskcb.AdminModule.AddBranchRequest;
import com.ekenya.rnd.backend.fskcb.AdminModule.datasource.entities.BranchEntity;
import com.ekenya.rnd.backend.fskcb.payload.BranchResponse;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public interface ISettingsService {

    boolean createBranch(AddBranchRequest model);

    ArrayNode loadAllBranches();

    ObjectNode getById(Long branchId);
}
