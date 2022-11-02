package com.deltacode.kcb.UserManagement.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAppResponse {
    private List<UserAppDto> content;
    private int pageNo;
    private int pageSize;
    private int totalElements;
    private int totalPages;
    private boolean last;


}
