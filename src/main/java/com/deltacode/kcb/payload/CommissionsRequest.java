package com.deltacode.kcb.payload;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommissionsRequest {
    private long agentId;
    private String month;
}
