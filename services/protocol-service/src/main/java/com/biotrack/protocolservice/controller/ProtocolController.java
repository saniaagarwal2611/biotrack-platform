package com.biotrack.protocolservice.controller;

import com.biotrack.protocolservice.dto.request.ProtocolRequestDTO;
import com.biotrack.protocolservice.dto.response.ProtocolResponseDTO;
import com.biotrack.protocolservice.enums.ProtocolPhase;
import com.biotrack.protocolservice.service.ProtocolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/protocols")
public class ProtocolController {

    private final ProtocolService protocolService;

    public ProtocolController(ProtocolService protocolService) {
        this.protocolService = protocolService;
    }

    @PostMapping
    public ResponseEntity<ProtocolResponseDTO> addProtocol(@RequestBody ProtocolRequestDTO dto) {
        return ResponseEntity.ok(protocolService.addProtocol(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProtocolResponseDTO>> getAllProtocols() {
        return ResponseEntity.ok(protocolService.getAllProtocols());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProtocolResponseDTO> getProtocolById(@PathVariable Long id) {
        return ResponseEntity.ok(protocolService.getProtocolById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProtocolResponseDTO> updateProtocol(@PathVariable Long id,
                                                              @RequestBody ProtocolRequestDTO dto) {
        return ResponseEntity.ok(protocolService.updateProtocol(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProtocol(@PathVariable Long id) {
        return ResponseEntity.ok(protocolService.deleteProtocol(id));
    }

    @PatchMapping("/{id}/phase")
    public ResponseEntity<ProtocolResponseDTO> updateProtocolPhase(@PathVariable Long id,
                                                                   @RequestParam ProtocolPhase phase) {
        return ResponseEntity.ok(protocolService.updateProtocolPhase(id, phase));
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<ProtocolResponseDTO> closeProtocol(@PathVariable Long id) {
        return ResponseEntity.ok(protocolService.closeProtocol(id));
    }

    // ✅ New endpoint: assign site to protocol
    @PostMapping("/{protocolId}/sites/{siteId}")
    public ResponseEntity<ProtocolResponseDTO> assignSiteToProtocol(@PathVariable Long protocolId,
                                                                    @PathVariable Long siteId) {
        return ResponseEntity.ok(protocolService.assignSiteToProtocol(protocolId, siteId));
    }
}
