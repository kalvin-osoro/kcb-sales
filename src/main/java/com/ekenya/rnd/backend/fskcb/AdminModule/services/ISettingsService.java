package com.ekenya.rnd.backend.fskcb.AdminModule.services;

import com.ekenya.rnd.backend.fskcb.AdminModule.AddBranchRequest;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface ISettingsService {

    boolean createBranch(AddBranchRequest model);

    ArrayNode loadAllBranches();

    ObjectNode getById(Long branchId);
}
