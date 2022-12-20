package com.ekenya.rnd.backend.fskcb.AdminModule.services;

import com.ekenya.rnd.backend.fskcb.AdminModule.models.reqs.LoadLogFileRequest;
import com.ekenya.rnd.backend.fskcb.AdminModule.models.resp.LogFileResponse;
import com.fasterxml.jackson.databind.node.ArrayNode;

public interface IAppLogsService {

    ArrayNode loadAvailableLogs();

    LogFileResponse loadLogFile(LoadLogFileRequest model);
}
