package com.deltacode.kcb.DSRModule.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZoneCoordinatesResponse {
    private BigDecimal latitude;
    private BigDecimal longitude;
}
