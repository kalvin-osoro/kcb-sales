package com.ekenya.rnd.backend.fskcb.AdminModule.services;

import com.ekenya.rnd.backend.fskcb.AdminModule.AddBranchRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j

@Service
public class SettingsService implements ISettingsService {

    @Override
    public boolean createBranch(AddBranchRequest model) {
        return false;
    }

    @Override
    public List<ObjectNode> loadAllBranches() {
        return null;
    }
}
