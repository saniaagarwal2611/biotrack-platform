package com.biotrack.protocolservice.dto.request;

import com.biotrack.protocolservice.enums.ProtocolPhase;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProtocolRequestDTO {

    private String title;
    private ProtocolPhase phase;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}

