package com.ekenya.rnd.backend.fskcb.UserManagement.models.reps;

import com.ekenya.rnd.backend.utils.AppConstants;
import org.springframework.web.bind.annotation.RequestParam;

public class UsersListRequest {

    public int pageNo = 1;//AppConstants.DEFAULT_PAGE_NUMBER;
    public int pageSize = 50;//AppConstants.DEFAULT_PAGE_SIZE;
    public String sortBy = AppConstants.DEFAULT_SORT_BY;
    public String sortDir = AppConstants.DEFAULT_SORT_DIR;
}
