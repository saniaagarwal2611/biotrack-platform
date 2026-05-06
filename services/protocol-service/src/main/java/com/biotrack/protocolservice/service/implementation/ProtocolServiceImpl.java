package com.biotrack.protocolservice.service.implementation;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.biotrack.protocolservice.dto.request.ProtocolRequestDTO;
import com.biotrack.protocolservice.dto.response.ProtocolResponseDTO;
import com.biotrack.protocolservice.entity.Protocol;
import com.biotrack.protocolservice.entity.Site;
import com.biotrack.protocolservice.enums.ProtocolPhase;
import com.biotrack.protocolservice.exception.IdNotFoundException;
import com.biotrack.protocolservice.repository.ProtocolRepository;
import com.biotrack.protocolservice.repository.SiteRepository;
import com.biotrack.protocolservice.service.ProtocolService;

@Service
public class ProtocolServiceImpl implements ProtocolService {

    private final ProtocolRepository protocolRepository;
    private final SiteRepository siteRepository;

    public ProtocolServiceImpl(ProtocolRepository protocolRepository, SiteRepository siteRepository) {
        this.protocolRepository = protocolRepository;
        this.siteRepository = siteRepository;
    }

    @Override
    public ProtocolResponseDTO addProtocol(ProtocolRequestDTO dto) {
        Protocol protocol = new Protocol(dto.getTitle(), dto.getPhase(),
                dto.getStartDate(), dto.getEndDate(), dto.getStatus());
        Protocol savedProtocol = protocolRepository.save(protocol);
        return toResponseDTO(savedProtocol);
    }

    @Override
    public List<ProtocolResponseDTO> getAllProtocols() {
        return protocolRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProtocolResponseDTO getProtocolById(Long id) {
        Protocol protocol = protocolRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Protocol not found with id: " + id));
        return toResponseDTO(protocol);
    }

    @Override
    public ProtocolResponseDTO updateProtocol(Long id, ProtocolRequestDTO dto) {
        Protocol protocol = protocolRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Protocol not found with id: " + id));

        protocol.setTitle(dto.getTitle());
        protocol.setPhase(dto.getPhase());
        protocol.setStartDate(dto.getStartDate());
        protocol.setEndDate(dto.getEndDate());
        protocol.setStatus(dto.getStatus());

        Protocol updatedProtocol = protocolRepository.save(protocol);
        return toResponseDTO(updatedProtocol);
    }

    @Override
    public String deleteProtocol(Long id) {
        Protocol protocol = protocolRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Protocol not found with id: " + id));
        protocolRepository.delete(protocol);
        return "Protocol deleted successfully";
    }

    @Override
    public ProtocolResponseDTO updateProtocolPhase(Long id, ProtocolPhase phase) {
        Protocol protocol = protocolRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Protocol not found with id: " + id));
        protocol.setPhase(phase);
        return toResponseDTO(protocolRepository.save(protocol));
    }

    @Override
    public ProtocolResponseDTO closeProtocol(Long id) {
        Protocol protocol = protocolRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Protocol not found with id: " + id));
        protocol.setStatus("CLOSED");
        return toResponseDTO(protocolRepository.save(protocol));
    }

    @Override
    public ProtocolResponseDTO assignSiteToProtocol(Long protocolId, Long siteId) {
        Protocol protocol = protocolRepository.findById(protocolId)
                .orElseThrow(() -> new IdNotFoundException("Protocol not found with id: " + protocolId));
        Site site = siteRepository.findById(siteId)
                .orElseThrow(() -> new IdNotFoundException("Site not found with id: " + siteId));

        protocol.getSites().add(site);
        site.getProtocols().add(protocol);

        Protocol updatedProtocol = protocolRepository.save(protocol);
        return toResponseDTO(updatedProtocol);
    }

    // ✅ Utility mapper
    private ProtocolResponseDTO toResponseDTO(Protocol protocol) {
        return new ProtocolResponseDTO(
                protocol.getProtocolId(),
                protocol.getTitle(),
                protocol.getPhase(),
                protocol.getStartDate(),
                protocol.getEndDate(),
                protocol.getStatus(),
                protocol.getSites().stream()
                        .map(site -> site.getName()) // or site.getSiteId() if you prefer IDs
                        .collect(Collectors.toList())
        );
    }

}
