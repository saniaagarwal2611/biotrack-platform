package com.biotrack.protocolservice.dto.response;

import com.biotrack.protocolservice.enums.ProtocolPhase;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProtocolResponseDTO {

    private Long protocolId;
    private String title;
    private ProtocolPhase phase;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    // ✅ Include linked sites (IDs or names)
    private List<String> sites;
}
