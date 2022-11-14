package com.ekenya.rnd.backend.fskcb.GeneralSettingsModule.services;

import com.ekenya.rnd.backend.fskcb.GeneralSettingsModule.AddBranchRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public interface ISettingsService {

    boolean createBranch(AddBranchRequest model);

    List<ObjectNode> loadAllBranches();
}
