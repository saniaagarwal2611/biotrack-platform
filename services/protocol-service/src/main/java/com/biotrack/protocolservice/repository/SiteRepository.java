package com.biotrack.protocolservice.repository;

import com.biotrack.protocolservice.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SiteRepository extends JpaRepository<Site, Long> {
	List<Site> findByProtocols_ProtocolId(Long protocolId);
}