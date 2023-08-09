//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.camtrack.entities.User;

public interface UsersRepository extends JpaRepository<User, Integer> {
	@Query(value = "select emailid from  users u  where u.userid =:userid and  u.enabled = true", nativeQuery = true)
	String emailUser(@Param("userid") final Integer userid);

	@Query("select cus.customerid from  Customer cus  where cus.status = 1")
	List<Integer> findAllActiveCustomerId();

	@Query("from  User u  where u.enabled = true")
	List<User> findAllActives();

	@Query("from  User u  where u.userid =:idusers and u.enabled = true")
	Optional<User> findByCodeUser(@Param("idusers") final Integer idusers);

	@Query("from  User u  where u.contacts =:contacts and u.enabled = true")
	Optional<User> findByContacts(@Param("contacts") final String contacts);

	@Query("from  User u  where u.emailid =:email and  u.enabled = true")
	Optional<User> findByEmail(@Param("email") final String email);

	@Query("from  User u  where u.username =:username and u.enabled = true")
	Optional<User> findByUserName(@Param("username") final String username);

	@Query("from  User u  where u.username =:username and u.enabled = true and u.password=:password")
	Optional<User> findByUserNameAndPassword(@Param("username") final String username,
			@Param("password") final String password);

	@Query(value = "select disticnt u.username from  users u  where u.userid in :listuserid and  u.enabled = true", nativeQuery = true)
	List<String> findListUserName(@Param("listuserid") final List<Integer> listuserid);

	@Query(value = "select name from  users u  where u.userid =:userid and  u.enabled = true", nativeQuery = true)
	String findNameUser(@Param("userid") final Integer userid);

	@Query(value = "select name,userid from  users u  where u.emailid =:email and  u.enabled = true", nativeQuery = true)
	Object[][] findNativeByEmail(@Param("email") final String email);

	@Query("from  User u  where u.randoms =:randoms")
	Optional<User> findrandoms(@Param("randoms") final String username);

	@Query("UPDATE User u SET u.failedAttempt = ?1 WHERE u.emailid = ?2")
	@Modifying
	public void updateFailedAttempts(Short failAttempts, String email);
}
