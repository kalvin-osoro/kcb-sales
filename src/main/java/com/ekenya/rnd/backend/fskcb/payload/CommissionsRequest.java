package com.ekenya.rnd.backend.fskcb.payload;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommissionsRequest {
    private long agentId;
    private String month;
}
