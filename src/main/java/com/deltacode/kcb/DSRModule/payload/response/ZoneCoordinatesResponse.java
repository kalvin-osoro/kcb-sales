package com.deltacode.kcb.DSRModule.payload.response;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZoneCoordinatesResponse {
    private BigDecimal latitude;
    private BigDecimal longitude;
}
