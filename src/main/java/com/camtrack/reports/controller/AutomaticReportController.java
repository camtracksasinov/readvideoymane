//
// Decompiled by Procyon v0.5.30
//

package com.camtrack.reports.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camtrack.bean.AutomaticreportBean;
import com.camtrack.bean.ListAllIdBean;
import com.camtrack.bean.ListIdLongBean;
import com.camtrack.config.StaticValues;
import com.camtrack.config.Utils;
import com.camtrack.entities.Success;
import com.camtrack.entities.User;
import com.camtrack.reports.service.AutomaticReportService;
import com.camtrack.user.repository.UsersRepository;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import springfox.documentation.annotations.ApiIgnore;

@RestController
@RefreshScope
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
public class AutomaticReportController {
	@Autowired
	AutomaticReportService automR;
	@Autowired
	UsersRepository usersR;

	@PostMapping({ "/autoreports" })
	public ResponseEntity<?> autoreports(@Parameter(hidden = true) Principal userP,
			@RequestBody(required = true) final AutomaticreportBean autobean,
			@RequestParam(value = "subject", required = true) final String subject,
			@RequestParam(value = "emailist", required = true) final String emailist,
			@RequestParam(value = "idreportname", required = true) final Integer idreportname,
			@RequestParam(value = "idfrequence", required = true) final Short idfrequence,
			@RequestParam(value = "formatreport", required = true) final Short formatreport,
			@RequestParam(value = "timerange", required = true) final Short timerange,
			@RequestParam(value = "hourofrequest", required = true) final String hourofrequest,
			@RequestParam(value = "dayofweekordayofmonth", required = true) final Short dayofweekordayofmonth,
			@RequestParam(value = "firstdate", required = true) final String firstdate,
			@RequestParam(value = "bodymail", required = true) final String bodymail) {
		final Date date = Utils.StringToDates(firstdate, "yyyy-MM-dd");
		if (Objects.isNull(date)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body((Object) new Success(StaticValues.wrongdateformat, StaticValues.wrongdateformat_Int));
		}
		final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
		return this.automR.autoreports(user, autobean, Utils.StringEscape(subject), Utils.StringEscape(emailist),
				idreportname, idfrequence, formatreport, timerange, hourofrequest, dayofweekordayofmonth, date,
				Utils.StringEscape(bodymail));
	}

	@RequestMapping(value = { "/frontlistallconfigsreport" }, method = { RequestMethod.POST })
	public List<Map<String, Object>> frontlistallconfigsreport(@Parameter(hidden = true) Principal userP,
			@RequestBody final ListAllIdBean filter,
			@RequestParam(value = "startdate", required = true) final String startdate,
			@RequestParam(value = "endate", required = true) final String endate) throws Exception {
		try {
			final User user = this.usersR.findByUserName(userP.getName()).orElse(null);
			return this.automR.newactivelistallconfigs(filter, Utils.StringEscape(startdate),
					Utils.StringEscape(endate), user);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/listallconfigsreport" }, method = { RequestMethod.GET })
	public List<Map<String, Object>> listallconfigs(final String dates) throws Exception {
		try {
			return this.automR.activelistallconfigs(Utils.StringEscape(dates), (String) null, "");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/listformatreport" }, method = { RequestMethod.GET })
	public List<Map<String, Object>> listformatreport() throws Exception {
		try {
			return this.automR.listformatrepor();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/listfrequences" }, method = { RequestMethod.GET })
	public List<Map<String, Object>> listfrequences() throws Exception {
		try {
			return this.automR.listfrequence();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/listimerange" }, method = { RequestMethod.GET })
	public List<Map<String, Object>> listimerange() throws Exception {
		try {
			return this.automR.listimerange();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/listreportnames" }, method = { RequestMethod.GET })
	public List<Map<String, Object>> listreportnames() throws Exception {
		try {
			return this.automR.listreportnames();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = { "/updatereportstatus" }, method = { RequestMethod.POST })
	public ResponseEntity<?> updaterportstatus(@RequestBody(required = true) final ListIdLongBean listconfigreportid)
			throws Exception {
		try {
			return this.automR.updatereportstatus(listconfigreportid.getListids());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
