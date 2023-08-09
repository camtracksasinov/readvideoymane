package com.camtrack.security;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.camtrack.config.StaticValues;
import com.camtrack.entities.Success;
import com.camtrack.entities.User;
import com.camtrack.user.repository.UsersRepository;

@Component
public class CustomLoginFailureHandler {

	@Value("${attempsfails.maxfailed}")
	private Integer MAX_FAILED_ATTEMPTS;

	@Value("${attempsfails.initialhour}")
	private Integer MAX_FAILED_ATTEMPTS_initialhour;

	@Value("${attempsfails.secondhour}")
	private Integer MAX_FAILED_ATTEMPTS_secondhour;

	@Value("${attempsfails.secondattemps}")
	private Integer MAX_FAILED_SECONDATTEMPTS;
	@Autowired
	UsersRepository userR;
	@Autowired
	private UserServices userService;

	public ResponseEntity<?> onAuthenticationFailure(String username) {
		// String username = request.getParameter("username");
		User user = userR.findByUserName(username).orElse(null);

		if (!Objects.isNull(user)) {
			if (user.getEnabled() && user.isAccountNonLocked()) {
				if (user.getFailedAttempt() < (MAX_FAILED_ATTEMPTS - 1)) {
					userService.increaseFailedAttempts(user);
					return ResponseEntity.status(HttpStatus.OK).body(new Success(
							StaticValues.UserNameorPasswordIncorrect, StaticValues.UserNameorPasswordIncorrect_Int));

				} else {
					userService.lock(user);
					if (user.getFlockn().intValue() < MAX_FAILED_SECONDATTEMPTS) {
						return ResponseEntity.status(HttpStatus.OK)
								.body(new Success(
										StaticValues.WrongAttempFail.replace("XXXX",
												MAX_FAILED_ATTEMPTS_initialhour + ""),
										StaticValues.WrongAttempFail_Int));
					} else {
						return ResponseEntity.status(HttpStatus.OK)
								.body(new Success(
										StaticValues.WrongAttempFail2.replace("XXXX",
												MAX_FAILED_ATTEMPTS_secondhour + ""),
										StaticValues.WrongAttempFail2_Int));
					}

					/**
					 * if(Objects.isNull(user.getFlockd())) { user.setFlockd(new Date());
					 * userR.saveAndFlush(user); return ResponseEntity.status(HttpStatus.OK)
					 * .body(new Success(StaticValues.WrongAttempFail.replace("XXXX",
					 * MAX_FAILED_ATTEMPTS_initialhour+""), StaticValues.WrongAttempFail_Int)); }
					 * else { return ResponseEntity.status(HttpStatus.OK) .body(new
					 * Success(StaticValues.WrongAttempFail2.replace("XXXX",
					 * MAX_FAILED_ATTEMPTS_secondhour+""), StaticValues.WrongAttempFail2_Int)); }
					 */

					// exception = new LockedException("Your account has been locked due to 3 failed
					// attempts."
					// + " It will be unlocked after 24 hours.");
				}
			} else if (!user.isAccountNonLocked()) {
				if (userService.unlockWhenTimeExpired(user)) {
					return ResponseEntity.status(HttpStatus.OK).body(
							new Success(StaticValues.AccountUnlockAfterFail, StaticValues.AccountUnlockAfterFail_Int));

				} else {

					/**
					 * if(Objects.isNull(user.getFlockd())) { return
					 * ResponseEntity.status(HttpStatus.OK) .body(new
					 * Success(StaticValues.WrongAttempFail.replace("XXXX",
					 * MAX_FAILED_ATTEMPTS_initialhour+""), StaticValues.WrongAttempFail_Int)); }
					 * else { return ResponseEntity.status(HttpStatus.OK) .body(new
					 * Success(StaticValues.WrongAttempFail2.replace("XXXX",
					 * MAX_FAILED_ATTEMPTS_secondhour+""), StaticValues.WrongAttempFail2_Int)); }
					 */
					if (user.getFlockn().intValue() < MAX_FAILED_SECONDATTEMPTS) {
						return ResponseEntity.status(HttpStatus.OK)
								.body(new Success(
										StaticValues.WrongAttempFail.replace("XXXX",
												MAX_FAILED_ATTEMPTS_initialhour + ""),
										StaticValues.WrongAttempFail_Int));
					} else {
						return ResponseEntity.status(HttpStatus.OK)
								.body(new Success(
										StaticValues.WrongAttempFail2.replace("XXXX",
												MAX_FAILED_ATTEMPTS_secondhour + ""),
										StaticValues.WrongAttempFail2_Int));
					}
					/**
					 * return ResponseEntity.status(HttpStatus.OK) .body(new
					 * Success(StaticValues.WrongAttempFail, StaticValues.WrongAttempFail_Int));
					 */
				}
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(new Success(StaticValues.UserNameorPasswordIncorrect,
						StaticValues.UserNameorPasswordIncorrect_Int));
			}

		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Success(StaticValues.UsernameIncorrect, StaticValues.UsernameIncorrect_Int));
		}
		// return ResponseEntity.status(HttpStatus.OK).body(new
		// Success(StaticValues.UserNameorPasswordIncorrect,
		// StaticValues.UserNameorPasswordIncorrect_Int));
	}

}
