package com.ekenya.rnd.backend.fskcb.payload;

import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.BranchEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchResponse {
    private List<BranchEntity> content;
    private int pageNo;
    private int pageSize;
    private int totalElements;
    private int totalPages;
    private boolean last;
}
