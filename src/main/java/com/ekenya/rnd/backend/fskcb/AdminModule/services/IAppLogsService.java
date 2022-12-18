package com.ekenya.rnd.backend.fskcb.AdminModule.services;

import com.ekenya.rnd.backend.fskcb.AdminModule.models.reqs.LoadLogFileRequest;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface IAppLogsService {

    ArrayNode loadAvailableLogs();

    ObjectNode loadLogFile(LoadLogFileRequest model);
}
