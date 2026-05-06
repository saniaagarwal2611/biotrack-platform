package com.biotrack.protocolservice.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.biotrack.protocolservice.dto.request.SiteRequestDTO;
import com.biotrack.protocolservice.dto.response.SiteResponseDTO;
import com.biotrack.protocolservice.enums.SiteStatus;
import com.biotrack.protocolservice.service.SiteService;

@RestController
@RequestMapping("/api/v1/sites")
public class SiteController {

    private final SiteService siteService;

    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }

    @PostMapping
    public ResponseEntity<SiteResponseDTO> addSite(@RequestBody SiteRequestDTO dto) {
        SiteResponseDTO response = siteService.addSite(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SiteResponseDTO>> getAllSites() {
        return ResponseEntity.ok(siteService.getAllSites());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiteResponseDTO> getSiteById(@PathVariable Long id) {
        return ResponseEntity.ok(siteService.getSiteById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SiteResponseDTO> updateSite(@PathVariable Long id, @RequestBody SiteRequestDTO dto) {
        return ResponseEntity.ok(siteService.updateSite(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSite(@PathVariable Long id) {
        return ResponseEntity.ok(siteService.deleteSite(id));
    }

    // ✅ Get all sites linked to a protocol
    @GetMapping("/protocol/{protocolId}")
    public ResponseEntity<List<SiteResponseDTO>> getSitesByProtocol(@PathVariable Long protocolId) {
        return ResponseEntity.ok(siteService.getSitesByProtocol(protocolId));
    }

    // ✅ Update site status
    @PutMapping("/{id}/status")
    public ResponseEntity<SiteResponseDTO> updateSiteStatus(@PathVariable Long id, @RequestParam SiteStatus status) {
        return ResponseEntity.ok(siteService.updateSiteStatus(id, status));
    }

    // ✅ Get investigators at a site
    @GetMapping("/{id}/investigators")
    public ResponseEntity<List<String>> getSiteInvestigators(@PathVariable Long id) {
        return ResponseEntity.ok(siteService.getInvestigators(id));
    }

    // ✅ Get protocols running at a site
    @GetMapping("/{id}/protocols")
    public ResponseEntity<List<String>> getSiteProtocols(@PathVariable Long id) {
        return ResponseEntity.ok(siteService.getProtocols(id));
    }
}
