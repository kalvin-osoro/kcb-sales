package com.deltacode.kcb.payload;

import java.util.List;

public class TargetResponse {
    private List<TargetDto> content;
    private int pageNo;
    private int pageSize;
    private int totalElements;
    private int totalPages;
    private boolean last;
}
