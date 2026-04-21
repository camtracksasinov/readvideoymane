// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.camtrack.entities.Customer;
import com.camtrack.entities.Customeraffiliate;
import com.camtrack.entities.Transporter;
import com.camtrack.entities.Userights;

public interface UserightsRepository extends JpaRepository<Userights, Integer> {

	@Query("from Userights userr where  userr.status = 1 and userr.userid.userid = :userid and userr.affiliateid.affiliateid = :affiliateid and userr.idtypealert.parametertypeid = :parametertypeid")
	Optional<Userights> findUserightsForAffiliate(final Integer userid, final Integer affiliateid,
			final Integer parametertypeid);

	@Query("from Userights userr where  userr.status = 1 and userr.userid.userid = :userid and userr.customerid.customerid = :customerid and userr.idtypealert.parametertypeid = :parametertypeid")
	Optional<Userights> findUserightsForCustomer(final Integer userid, final Integer customerid,
			final Integer parametertypeid);

	@Query("from Userights userr where  userr.status = 1 and userr.userid.userid = :userid and userr.transporterid.transporterid = :transporterid and userr.idtypealert.parametertypeid = :parametertypeid")
	Optional<Userights> findUserightsForTransporter(final Integer userid, final Integer transporterid,
			final Integer parametertypeid);

	@Query("from Userights userr where  userr.status = 1 and userr.userid.userid = :userid and userr.affiliateid.affiliateid = :affiliateid")
	List<Userights> findListUserightsForAffiliate(final Integer userid, final Integer affiliateid);

	@Query("from Userights userr where  userr.status = 1 and userr.userid.userid = :userid and userr.customerid.customerid = :customerid")
	List<Userights> findListUserightsForCustomer(final Integer userid, final Integer customerid);

	@Query("from Userights userr where  userr.status = 1 and userr.userid.userid = :userid and userr.transporterid.transporterid = :transporterid")
	List<Userights> findListUserightsForTransporter(final Integer userid, final Integer transporterid);

	@Query("from Userights userr where  userr.status = 1 and userr.userid.enabled = true order by userr.userid.userid")
	List<Userights> findActivesUsers();

	@Query("select distinct userr.affiliateid from Userights userr where  userr.status = 1 and userr.userid.userid = :userid")
	List<Customeraffiliate> findAllCustomerAffiliateHierarchieOfUserId(final Integer userid);

	@Query("select distinct userr.affiliateid from Userights userr where  userr.status = 1 and userr.userid.userid = :userid and userr.customerid.customerid in :listcostomerid")
	List<Customeraffiliate> findAllCustomerAffiliateHierarchieUser(final Integer userid,
			final List<Integer> listcostomerid);

	@Query("select distinct userr.affiliateid from Userights userr where  userr.status = 1 and userr.userid.userid = :userid")
	List<Customeraffiliate> findAllCustomerAffiliateUser(final Integer userid);

	@Query("select distinct userr.customerid from Userights userr where  userr.status = 1 and userr.userid.userid = :userid")
	List<Customer> findAllCustomerHierarchieOfUserId(final Integer userid);

	@Query("select distinct userr.affiliateid.customerid from Userights userr where  userr.status = 1 and userr.userid.userid = :userid")
	List<Customer> findAllCustomerFromCustomerAffiliateUser(final Integer userid);

	@Query("select distinct userr.transporterid.affiliateid.customerid from Userights userr where  userr.status = 1 and userr.userid.userid = :userid")
	List<Customer> findAllCustomerFromAffiliateFromTransporterUser(final Integer userid);

	@Query(value = "select distinct userr.customerid from userights userr where  userr.status = 1 and userr.userid = :userid and userr.transporterid is null  and userr.affiliateid is null", nativeQuery = true)
	List<Integer> findAllCustomerIdOfUserId(final Integer userid);

	@Query("select distinct userr.customerid from Userights userr where  userr.status = 1 and userr.userid.userid = :userid")
	List<Customer> findAllCustomerOfUserId(final Integer userid);

	@Query("select distinct userr.transporterid from Userights userr where  userr.status = 1 and userr.userid.userid = :userid and userr.affiliateid.affiliateid in :listaffiliateid")
	List<Transporter> findAllCustomerTransporterHierarchieUser(final Integer userid,
			final List<Integer> listaffiliateid);

	@Query(value = "select distinct userr.idtypealert from userights userr where  userr.status = 1 and userr.userid = :userid", nativeQuery = true)
	List<Integer> findAllParametertypeIdOfUserId(final Integer userid);

	@Query(value = "select distinct userr.affiliateid from userights userr where  userr.status = 1 and userr.userid = :userid and userr.affiliateid is not null", nativeQuery = true)
	List<Integer> findAllReelAffiliateIdOfUserId(final Integer userid);

	@Query(value = "select distinct userr.customerid from userights userr where  userr.status = 1 and userr.userid = :userid and userr.customerid is not null", nativeQuery = true)
	List<Integer> findAllReelCustomerIdOfUserId(final Integer userid);

	@Query(value = "select distinct userr.transporterid from userights userr where  userr.status = 1 and userr.userid = :userid and userr.transporterid is not null", nativeQuery = true)
	List<Integer> findAllReelTransporterIdOfUserId(final Integer userid);

	@Query(value = "select distinct userr.idtypealert from userights userr where  userr.status = 1 and userr.userid = :userid and userr.affiliateid = :affiliateid", nativeQuery = true)
	List<Integer> findAllReelTypeAlertIdOfAffiliateIdAndUserId(final Integer userid, final Integer affiliateid);

	@Query(value = "select distinct userr.idtypealert from userights userr where  userr.status = 1 and userr.userid = :userid and userr.customerid = :customerid", nativeQuery = true)
	List<Integer> findAllReelTypeAlertIdOfCustomerIdAndUserId(final Integer userid, final Integer customerid);

	@Query(value = "select distinct userr.idtypealert from userights userr where  userr.status = 1 and userr.userid = :userid and userr.transporterid = :transporterid", nativeQuery = true)
	List<Integer> findAllReelTypeAlertIdOfTransporterIdAndUserId(final Integer userid, final Integer transporterid);

	@Query("select distinct userr.transporterid.affiliateid from Userights userr where  userr.status = 1 and userr.userid.userid = :userid")
	List<Customeraffiliate> findAllTransporterCustomerAffiliateUser(final Integer userid);

	@Query("select distinct userr.transporterid from Userights userr where  userr.status = 1 and userr.userid.userid = :userid")
	List<Transporter> findAllTransporterUser(final Integer userid);

	@Query("from Userights userr where  userr.status = 1 and userr.userid.userid = :userid and  userr.affiliateid is not null and userr.transporterid is null and userr.customerid is null")
	List<Userights> findAllUsersRightOfAffiliateOfUserId(final Integer userid);

	@Query("from Userights userr where  userr.status = 1 and userr.userid.userid = :userid and userr.customerid is not null and userr.transporterid is null  and userr.affiliateid is null")
	List<Userights> findAllUsersRightOfCustomerOfUserId(final Integer userid);

	@Query("from Userights userr where  userr.status = 1 and userr.userid.userid = :userid and  userr.affiliateid is null and userr.transporterid is not null and userr.customerid is null")
	List<Userights> findAllUsersRightOfTransporterOfUserId(final Integer userid);

	@Query("select distinct userr.affiliateid.customerid.customerid,userr.affiliateid.customerid.name from Userights userr where  userr.status = 1 and userr.userid.userid = :userid")
	List<Object[]> findNativeAllAffiliateUser(final Integer userid);

	@Query("select distinct userr.affiliateid.affiliateid,userr.affiliateid.name from Userights userr where  userr.status = 1 and userr.userid.userid = :userid")
	List<Object[]> findNAtiveAllCustomerAffiliateHierarchieOfUserId(final Integer userid);

	@Query("select distinct userr.customerid.customerid,userr.customerid.name from Userights userr where  userr.status = 1 and userr.userid.userid = :userid")
	List<Object[]> findNAtiveAllCustomerHierarchieOfUserId(final Integer userid);

	@Query("select distinct userr.transporterid.affiliateid.affiliateid,userr.transporterid.affiliateid.name from Userights userr where  userr.status = 1 and userr.userid.userid = :userid")
	List<Object[]> findNativeAllTransporterCustomerAffiliateUser(final Integer userid);

	@Query("select distinct userr.transporterid.affiliateid.customerid.customerid,userr.transporterid.affiliateid.customerid.name from Userights userr where  userr.status = 1 and userr.userid.userid = :userid")
	List<Object[]> findNativeAllTransporterUser(final Integer userid);

	@Query("from Userights userr where  userr.status = 1 and userr.affiliateid.affiliateid in :listaffiliateid order by userr.userid.userid")
	List<Userights> findUserAffiliateLevel(final List<Integer> listaffiliateid);

	@Query("from Userights userr where  userr.status = 1 and userr.customerid.customerid in :listcostomerid and userr.transporterid is null  and userr.affiliateid is null order by userr.userid.userid")
	List<Userights> findUserCustomerLevel(final List<Integer> listcostomerid);

	@Query("from Userights userr where  userr.status = 1 and userr.transporterid.transporterid in :listtransporterid order by userr.userid.userid")
	List<Userights> findUserTransporterLevel(final List<Integer> listtransporterid);

	@Modifying
	@Transactional
	@Query(value = "update userights set status = 0 where userid = :userid and (customerid is not null or transporterid is not null)", nativeQuery = true)
	Integer Updateaffiliatestatus(final Integer userid);

	@Modifying
	@Transactional
	@Query(value = "delete from userights where userid = :userid", nativeQuery = true)
	Integer deleteAllUsersRights(Integer userid);

	@Modifying
	@Transactional
	@Query(value = "update userights set status = 0 where userid = :userid and (affiliateid is not null or transporterid is not null)", nativeQuery = true)
	Integer Updatecustomerstatus(final Integer userid);

	@Modifying
	@Transactional
	@Query(value = "update userights set status = 0 where userid = :userid and (customerid is not null or affiliateid is not null)", nativeQuery = true)
	Integer Updatetransporterstatus(final Integer userid);

	@Query("select distinct userr.alertlevel,userr.alarmlevel,userr.recordlevel from Userights userr where  userr.status = 1 and userr.userid.userid = :userid and userr.transporterid.transporterid = :transporterid and userr.customerid is null and userr.affiliateid is null")
	List<Object[]> findAllAlertLevelFromTransporter(Integer transporterid, Integer userid);
}
