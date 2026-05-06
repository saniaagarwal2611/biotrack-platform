package com.biotrack.protocolservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.biotrack.protocolservice.entity.Protocol;

@Repository
public interface ProtocolRepository extends JpaRepository<Protocol, Long> {

}
