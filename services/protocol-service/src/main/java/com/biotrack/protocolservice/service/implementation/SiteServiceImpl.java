package com.biotrack.protocolservice.service.implementation;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.biotrack.protocolservice.dto.request.SiteRequestDTO;
import com.biotrack.protocolservice.dto.response.SiteResponseDTO;
import com.biotrack.protocolservice.entity.Site;
import com.biotrack.protocolservice.entity.Protocol;
import com.biotrack.protocolservice.enums.SiteStatus;
import com.biotrack.protocolservice.exception.IdNotFoundException;
import com.biotrack.protocolservice.repository.SiteRepository;
import com.biotrack.protocolservice.repository.ProtocolRepository;
import com.biotrack.protocolservice.service.SiteService;

@Service
public class SiteServiceImpl implements SiteService {

    private final SiteRepository siteRepository;
    private final ProtocolRepository protocolRepository;

    public SiteServiceImpl(SiteRepository siteRepository, ProtocolRepository protocolRepository) {
        this.siteRepository = siteRepository;
        this.protocolRepository = protocolRepository;
    }

    @Override
    public SiteResponseDTO addSite(SiteRequestDTO dto) {
        Site site = new Site();
        site.setName(dto.getName());
        site.setLocation(dto.getLocation());
        site.setInvestigatorId(dto.getInvestigatorId());
        site.setStatus(dto.getStatus());

        Site savedSite = siteRepository.save(site);
        return toResponseDTO(savedSite);
    }

    @Override
    public List<SiteResponseDTO> getAllSites() {
        return siteRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SiteResponseDTO getSiteById(Long id) {
        Site site = siteRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Site not found with id: " + id));
        return toResponseDTO(site);
    }

    @Override
    public SiteResponseDTO updateSite(Long id, SiteRequestDTO dto) {
        Site site = siteRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Site not found with id: " + id));

        site.setName(dto.getName());
        site.setLocation(dto.getLocation());
        site.setInvestigatorId(dto.getInvestigatorId());
        site.setStatus(dto.getStatus());

        Site updatedSite = siteRepository.save(site);
        return toResponseDTO(updatedSite);
    }

    @Override
    public String deleteSite(Long id) {
        Site site = siteRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Site not found with id: " + id));
        siteRepository.delete(site);
        return "Site deleted successfully";
    }

    @Override
    public List<SiteResponseDTO> getSitesByProtocol(Long protocolId) {
        Protocol protocol = protocolRepository.findById(protocolId)
                .orElseThrow(() -> new IdNotFoundException("Protocol not found with id: " + protocolId));

        return protocol.getSites().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SiteResponseDTO updateSiteStatus(Long id, SiteStatus status) {
        Site site = siteRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Site not found with id: " + id));
        site.setStatus(status);
        Site updatedSite = siteRepository.save(site);
        return toResponseDTO(updatedSite);
    }

    @Override
    public List<String> getInvestigators(Long siteId) {
        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new IdNotFoundException("Site not found with id: " + siteId));
        return List.of(site.getInvestigatorId());
    }

    @Override
    public List<String> getProtocols(Long siteId) {
        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new IdNotFoundException("Site not found with id: " + siteId));
        return site.getProtocols().stream()
                .map(Protocol::getTitle) // or getProtocolId if you prefer IDs
                .collect(Collectors.toList());
    }

    // ✅ Utility mapper
    private SiteResponseDTO toResponseDTO(Site site) {
        return new SiteResponseDTO(
                site.getSiteId(),
                site.getName(),
                site.getLocation(),
                site.getInvestigatorId(),
                site.getStatus(), // ✅ enum, not String
                site.getProtocols().stream()
                        .map(Protocol::getTitle)
                        .collect(Collectors.toList())
        );
    }

}
