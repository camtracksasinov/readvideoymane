//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.exception.dao;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.camtrack.bean.ListInvalidExceptionBean;
import com.camtrack.entities.User;

public interface ExceptionInterface {
	List<Map<String, Object>> detailsexception(final User user, final String listToString, final String listToString2,
			final String listToString3, final String listToString4, final String listToString5, final String datedebut,
			final String datefin, final String listToString6, final Boolean record, final Boolean alert,
			final Boolean alarm);

	ResponseEntity<?> groupinvalidation(final User user, final String listToString, final String listToString2,
			final String listToString3, final String listToString4, final String listToString5, final String datedebut,
			final String datefin, final String listToString6, final Boolean record, final Boolean alert,
			final Boolean alarm);

	List<Map<String, Object>> invalidexception(final User user, final String listToString, final String listToString2,
			final String listToString3, final String listToString4, final String listToString5, final String datedebut,
			final String datefin, final String listToString6, final Boolean record, final Boolean alert,
			final Boolean alarm);

	ResponseEntity<?> invalidexceptionupdatestatus(final User user, final ListInvalidExceptionBean invalidorvalid);

	List<Map<String, Object>> listexceptionlevel();

	List<?> listExceptionTypes();

	List<?> filterexception();
}
