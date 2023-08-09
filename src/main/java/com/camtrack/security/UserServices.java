package com.camtrack.security;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.camtrack.config.Utils;
import com.camtrack.entities.User;
import com.camtrack.user.repository.UsersRepository;

@Service
@Transactional
public class UserServices {

	// 86400000
	@Value("${attempsfails.locktimeduration0}")
	private long LOCK_TIME_DURATION;

	@Value("${attempsfails.locktimeduration}")
	private long LOCK_TIME_DURATION2;
	// public static final int MAX_FAILED_ATTEMPTS = 3;

	@Value("${attempsfails.secondattemps}")
	private Integer MAX_FAILED_SECONDATTEMPTS;

	// private static final long = 24 * 60 * 60 * 1000; // 24 hours

	@Autowired
	private UsersRepository repo;

	public User getByEmail(String email) {
		// TODO Auto-generated method stub
		return repo.findByEmail(email).orElse(null);
	}

	public void increaseFailedAttempts(User user) {
		Short newFailAttempts = (short) (user.getFailedAttempt() + (short) 1);
		repo.updateFailedAttempts(newFailAttempts, user.getEmailid());
	}

	public void lock(User user) {
		Date dat = new Date();
		user.setAccountNonLocked(false, false);
		user.setLockTime(dat);
		user.setFlockn((short) (user.getFlockn() + Short.valueOf("1")));

		if (Objects.isNull(user.getFlockd()) || user.getFlockd().before(Utils.getStartOfDay(dat))) {
			user.setFlockd(dat);
		}

		repo.save(user);
	}

	public void resetFailedAttempts(String email) {
		repo.updateFailedAttempts((short) 0, email);
	}

	public boolean unlockWhenTimeExpired(User user) {

		Long lockTimeInMillis = null;
		Date dat = new Date();
		if (!Objects.isNull(user.getLockTime())) {
			lockTimeInMillis = user.getLockTime().getTime();
		} else {
			lockTimeInMillis = dat.getTime();
		}

		long currentTimeInMillis = System.currentTimeMillis();

		Calendar cal = Calendar.getInstance();
		cal.setTime(dat);

		Calendar cal2 = Calendar.getInstance();
		if (!Objects.isNull(user.getFlockd())) {
			cal2.setTime(user.getFlockd());
		} else {
			cal2.setTime(new Date());
		}

		// user.getFlockd().equals(dat)
		if (Utils.isDateSame(cal2, cal)) {
			if (user.getFlockn().intValue() < MAX_FAILED_SECONDATTEMPTS) {
				if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
					user.setAccountNonLocked(true, false);
					user.setLockTime(null);
					user.setFailedAttempt((short) 0);
					repo.save(user);

					return true;
				}
			} else {
				if (lockTimeInMillis + LOCK_TIME_DURATION2 < currentTimeInMillis) {
					user.setAccountNonLocked(true, false);
					user.setLockTime(null);
					user.setFailedAttempt((short) 0);
					user.setFlockd(dat);
					user.setFlockn((short) 0);
					repo.save(user);

					return true;
				}
			}
		} else {
			user.setFlockd(dat);
			user.setFlockn((short) 0);
			repo.save(user);
		}

		return false;
	}
}
