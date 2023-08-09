//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.transporter.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.camtrack.entities.Transporter;

public interface TransporterRepository extends JpaRepository<Transporter, Integer> {
	@Query("select distinct trans.transporterid  from  Transporter trans  where trans.status = 1")
	List<Integer> findAllActiveIdTransporter();

	@Query("from  Transporter afftrans  where afftrans.status = 1 and afftrans.transporterid in :transporterids")
	List<Transporter> findAllActiveTransporter(final List<Integer> transporterids);

	@Query("from  Transporter afftrans  where afftrans.status = 1 and afftrans.affiliateid.affiliateid in :listaffiliateid")
	List<Transporter> findAllActiveTransporterAffiliate(final List<Integer> listaffiliateid);

	@Query("from  Transporter afftrans  where afftrans.status = 1 and afftrans.affiliateid.affiliateid in :listaffiliateid and afftrans.transporterid in :listtransporterId")
	List<Transporter> findAllActiveTransporterAffiliateWithListId(final List<Integer> listaffiliateid,
			final List<Integer> listtransporterId);

	@Query("from  Transporter trans  where trans.status = 1")
	List<Transporter> findAllActiveTransporterComplete();

	@Query("select distinct afftrans.transporterid from  Transporter afftrans  where afftrans.status = 1 and afftrans.affiliateid.affiliateid in :listaffiliateid")
	List<Integer> findAllActiveTransporterIdAffiliate(final List<Integer> listaffiliateid);

	@Query("select distinct afftrans.transporterid from  Transporter afftrans  where afftrans.status = 1 and afftrans.affiliateid.affiliateid = :listaffiliateid")
	List<Integer> findAllActiveTransporterIdAffiliateId(final Integer listaffiliateid);

	@Query("select distinct afftrans.transporterid from  Transporter afftrans  where afftrans.status = 1 and afftrans.transporterid not in :listtransporterid")
	List<Integer> findAllManualTransporterId(final List<Integer> listtransporterid);

	@Query(value = "select trans.name from transporter trans  where trans.transporterid = :transporterid", nativeQuery = true)
	String nameobject(final Integer transporterid);

	@Transactional
	@Modifying
	@Query(value = "update transporter set status = 0 where transporterid = :transporterid", nativeQuery = true)
	Integer updatestatustransporter(final Integer transporterid);
}
