package com.biotrack.protocolservice.dto.response;

import com.biotrack.protocolservice.enums.SiteStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SiteResponseDTO {

    private Long siteId;
    private String name;
    private String location;
    private String investigatorId;
    private SiteStatus status;

    // ✅ Include linked protocols (IDs or titles)
    private List<String> protocols;
}
