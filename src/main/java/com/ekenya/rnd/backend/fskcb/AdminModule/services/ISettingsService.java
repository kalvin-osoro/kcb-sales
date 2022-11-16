package com.ekenya.rnd.backend.fskcb.AdminModule.services;

import com.ekenya.rnd.backend.fskcb.AdminModule.AddBranchRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public interface ISettingsService {

    boolean createBranch(AddBranchRequest model);

    List<ObjectNode> loadAllBranches();
}
