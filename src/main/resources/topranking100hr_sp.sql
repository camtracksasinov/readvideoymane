CREATE OR REPLACE FUNCTION public.sp_toprankingreport100hrs_new(customer_id integer, affiliate_id integer, transporter_id integer, vehicle_id integer, exceptiontype_id integer, exception_levels integer[], startdate date, enddate date)
 RETURNS TABLE(vehicleid integer, vehicledesc character varying, driverid integer, drivername character varying, affiliateid integer, affiliatename character varying, transporterid integer, transportername character varying, exceptionvalue numeric)
 LANGUAGE plpgsql
AS $function$

BEGIN

	IF customer_id=0  AND affiliate_id = 0 AND transporter_id=0 AND vehicle_id=0 THEN
		RETURN query

	SELECT DISTINCT e.affiliateid as vehicleid, c.name as vehicledesc,
	e.affiliateid as driverid, c.name as drivername,
		e.affiliateid as affiliateid, c.name AS affiliatename,
		e.affiliateid as transporterid, c.name AS transportername,
		
		(coalesce((
			SELECT sum(ex.timeexceeded)
			FROM exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid
			WHERE ex.level=ANY(exception_levels) AND ex.level=1
				AND "exceptiontype" = exceptiontype_id
				AND ex.affiliateid = e.affiliateid
				AND DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between startdate AND enddate
			)*100/(select totdurn from sp_get_totduration_exp(startdate, enddate, vehicle_id, 0, transporter_id , e.affiliateid, exceptiontype_id )),0)+
			
		coalesce((
			SELECT sum(ex.timeexceeded)
			FROM exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid
			WHERE ex.level=ANY(exception_levels) AND ex.level=2
				AND "exceptiontype" = exceptiontype_id
				AND ex.affiliateid = e.affiliateid
				AND DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between startdate AND enddate
			)*100/(select totdurn from sp_get_totduration_exp(startdate, enddate, vehicle_id, 0, transporter_id , e.affiliateid , exceptiontype_id)),0)+
			
		coalesce((
			SELECT sum(ex.timeexceeded)
			FROM exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid
			WHERE ex.level=ANY(exception_levels) AND ex.level=3
				AND "exceptiontype" = exceptiontype_id
				AND ex.affiliateid = e.affiliateid
				AND DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between startdate AND enddate
			)*100/(select totdurn from sp_get_totduration_exp(startdate, enddate, vehicle_id, 0, transporter_id , e.affiliateid , exceptiontype_id)),0))
			
			as exceptionvalue
			
	FROM exception e
	INNER JOIN customeraffiliate c ON c.affiliateid = e.affiliateid
	WHERE c.status=1
		AND e.exceptiontype = exceptiontype_id
		AND e.level =ANY(exception_levels)
		AND (DATE(e.enddatetime::timestamp at time zone c.afftimezone) between startdate and enddate)
	GROUP BY e.affiliateid,c.name 
	ORDER BY exceptionvalue DESC;
END IF ;

	IF customer_id>0  AND affiliate_id = 0 AND transporter_id=0 AND vehicle_id=0 THEN
		RETURN query

	SELECT DISTINCT  e.affiliateid as vehicleid, c.name as vehicledesc,
		e.affiliateid as driverid, c.name as drivername,
		e.affiliateid as affiliateid, c.name AS affiliatename,
		e.affiliateid as transporterid, c.name AS transportername,
		
		(coalesce((
			SELECT sum(ex.timeexceeded)
			FROM exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid
			WHERE ex.level=ANY(exception_levels) AND ex.level=1
				AND "exceptiontype" = exceptiontype_id
				AND ex.affiliateid = e.affiliateid
				AND DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between startdate AND enddate
			)*100/(select totdurn from sp_get_totduration_exp(startdate, enddate, vehicle_id, 0, transporter_id , e.affiliateid , exceptiontype_id)),0)+
			
		coalesce((
			SELECT sum(ex.timeexceeded)
			FROM exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid
			WHERE ex.level=ANY(exception_levels) AND ex.level=2
				AND "exceptiontype" = exceptiontype_id
				AND ex.affiliateid = e.affiliateid
				AND DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between startdate AND enddate
			)*100/(select totdurn from sp_get_totduration_exp(startdate, enddate, vehicle_id, 0, transporter_id , e.affiliateid , exceptiontype_id)),0)+
			
		coalesce((
			SELECT sum(ex.timeexceeded)
			FROM exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid
			WHERE ex.level=ANY(exception_levels) AND ex.level=3
				AND "exceptiontype" = exceptiontype_id
				AND ex.affiliateid = e.affiliateid
				AND DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between startdate AND enddate
			)*100/(select totdurn from sp_get_totduration_exp(startdate, enddate, vehicle_id, 0, transporter_id , e.affiliateid , exceptiontype_id)),0))
			
			as exceptionvalue
			
	FROM exception e
	INNER JOIN customeraffiliate c ON c.affiliateid = e.affiliateid
	WHERE c.customerid = customer_id 
		AND c.status=1
		AND e.exceptiontype = exceptiontype_id
		AND e.level =ANY(exception_levels)
		AND (DATE(e.enddatetime::timestamp at time zone c.afftimezone) between startdate and enddate)
	GROUP BY e.affiliateid,c.name 
	ORDER BY exceptionvalue DESC;
END

IF ;

	IF customer_id>0  AND affiliate_id > 0 AND transporter_id=0 AND vehicle_id=0 THEN
		RETURN query

	SELECT DISTINCT  e.transporterid as vehicleid, tr.name as vehicledesc,
		e.transporterid as driverid, tr.name as drivername,
		e.affiliateid as affiliateid, c.name AS affiliatename,
		e.transporterid as transporterid, tr.name AS transportername,
		
		(coalesce((
			SELECT sum(ex.timeexceeded)
			FROM exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid
			WHERE ex.level=ANY(exception_levels) AND ex.level=1
				AND "exceptiontype" = exceptiontype_id
				AND ex.transporterid = e.transporterid
				AND DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between startdate AND enddate
			)*100/(select totdurn from sp_get_totduration_exp(startdate, enddate, vehicle_id, 0, e.transporterid , e.affiliateid , exceptiontype_id)),0)+
			
		coalesce((
			SELECT sum(ex.timeexceeded)
			FROM exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid
			WHERE ex.level=ANY(exception_levels) AND ex.level=2
				AND "exceptiontype" = exceptiontype_id
				AND ex.transporterid = e.transporterid
				AND DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between startdate AND enddate
			)*100/(select totdurn from sp_get_totduration_exp(startdate, enddate, vehicle_id, 0, e.transporterid , e.affiliateid , exceptiontype_id)),0)+
			
		coalesce((
			SELECT sum(ex.timeexceeded)
			FROM exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid
			WHERE ex.level=ANY(exception_levels) AND ex.level=3
				AND "exceptiontype" = exceptiontype_id
				AND ex.transporterid = e.transporterid
				AND DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between startdate AND enddate
			)*100/(select totdurn from sp_get_totduration_exp(startdate, enddate, vehicle_id, 0, e.transporterid , e.affiliateid , exceptiontype_id)),0))
			
			as exceptionvalue
			
	FROM exception e
	INNER JOIN customeraffiliate c ON c.affiliateid = e.affiliateid
	INNER JOIN transporter tr ON tr.transporterid = e.transporterid
	WHERE e.affiliateid = affiliate_id
		AND tr.status=1
		AND e.exceptiontype = exceptiontype_id
		AND e.level =ANY(exception_levels)
		AND (DATE(e.enddatetime::timestamp at time zone c.afftimezone) between startdate and enddate)
	GROUP BY e.affiliateid,c.name,e.transporterid,tr.name 
	ORDER BY exceptionvalue DESC;
END

IF ;

	IF customer_id>0  AND affiliate_id > 0 AND transporter_id>0 AND vehicle_id=0 THEN
		RETURN query

	SELECT DISTINCT e.vehicleid as vehicleid, veh.vehicledesc as vehicledesc,
		e.driverid as driverid, dr.name as drivername,
		e.affiliateid as affiliateid, c.name AS affiliatename,
		e.transporterid as transporterid, tr.name AS transportername,
		
		(coalesce((
			SELECT sum(ex.timeexceeded)
			FROM exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid
			WHERE ex.level=ANY(exception_levels) AND ex.level=1
				AND "exceptiontype" = exceptiontype_id
				AND ex.driverid = e.driverid
				AND ex.vehicleid = e.vehicleid
				AND DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between startdate AND enddate
			)*100/(select totdurn from sp_get_totduration_exp(startdate, enddate, e.vehicleid, e.driverid, e.transporterid , e.affiliateid , exceptiontype_id)),0)+
		
		coalesce((
			SELECT sum(ex.timeexceeded)
			FROM exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid
			WHERE ex.level=ANY(exception_levels) AND ex.level=2
				AND "exceptiontype" = exceptiontype_id
				AND ex.driverid = e.driverid
				AND ex.vehicleid = e.vehicleid
				AND DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between startdate AND enddate
			)*100/(select totdurn from sp_get_totduration_exp(startdate, enddate, e.vehicleid, e.driverid, e.transporterid , e.affiliateid , exceptiontype_id)),0)+
		
		coalesce((
			SELECT sum(ex.timeexceeded)
			FROM exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid
			WHERE ex.level=ANY(exception_levels) AND ex.level=3
				AND "exceptiontype" = exceptiontype_id
				AND ex.driverid = e.driverid
				AND ex.vehicleid = e.vehicleid
				AND DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between startdate AND enddate
			)*100/(select totdurn from sp_get_totduration_exp(startdate, enddate, e.vehicleid, e.driverid, e.transporterid , e.affiliateid , exceptiontype_id)),0))
			
			as exceptionvalue
			
	FROM exception e
	INNER JOIN customeraffiliate c ON c.affiliateid = e.affiliateid
	INNER JOIN transporter tr ON tr.transporterid = e.transporterid
	INNER JOIN driver dr ON dr.driverid = e.driverid
	INNER JOIN vehicle veh ON veh.vehicleid = e.vehicleid
	WHERE e.transporterid = transporter_id
		AND dr.status=1
		AND e.exceptiontype = exceptiontype_id
		AND e.level =ANY(exception_levels)
		AND (DATE(e.enddatetime::timestamp at time zone c.afftimezone) between startdate and enddate)
	GROUP BY e.affiliateid,c.name,e.transporterid,tr.name ,e.driverid,dr.name,e.vehicleid,veh.vehicledesc
	ORDER BY exceptionvalue DESC;
END

IF ;
	IF customer_id>0  AND affiliate_id > 0 AND transporter_id>0 AND vehicle_id>0 THEN
		RETURN query

SELECT DISTINCT e.vehicleid as vehicleid, veh.vehicledesc as vehicledesc,
		e.driverid as driverid, dr.name as drivername,
		e.affiliateid as affiliateid, c.name AS affiliatename,
		e.transporterid as transporterid, tr.name AS transportername,
		
		(coalesce((
			SELECT sum(ex.timeexceeded)
			FROM exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid
			WHERE ex.level=ANY(exception_levels) AND ex.level=1
				AND "exceptiontype" = exceptiontype_id
				AND ex.driverid = e.driverid
				AND ex.vehicleid = e.vehicleid
				AND DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between startdate AND enddate
			)*100/(select totdurn from sp_get_totduration_exp(startdate, enddate, e.vehicleid, e.driverid, e.transporterid , e.affiliateid, exceptiontype_id)),0)+
			
		coalesce((
			SELECT sum(ex.timeexceeded)
			FROM exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid
			WHERE ex.level=ANY(exception_levels) AND ex.level=2
				AND "exceptiontype" = exceptiontype_id
				AND ex.driverid = e.driverid
				AND ex.vehicleid = e.vehicleid
				AND DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between startdate AND enddate
			)*100/(select totdurn from sp_get_totduration_exp(startdate, enddate, e.vehicleid, e.driverid, e.transporterid , e.affiliateid, exceptiontype_id)),0)+
			
		coalesce((
			SELECT sum(ex.timeexceeded)
			FROM exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid
			WHERE ex.level=ANY(exception_levels) AND ex.level=3
				AND "exceptiontype" = exceptiontype_id
				AND ex.driverid = e.driverid
				AND ex.vehicleid = e.vehicleid
				AND DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between startdate AND enddate
			)*100/(select totdurn from sp_get_totduration_exp(startdate, enddate, e.vehicleid, e.driverid, e.transporterid , e.affiliateid, exceptiontype_id)),0))
			
			as exceptionvalue
			
	FROM exception e
	INNER JOIN customeraffiliate c ON c.affiliateid = e.affiliateid
	INNER JOIN transporter tr ON tr.transporterid = e.transporterid
	INNER JOIN driver dr ON dr.driverid = e.driverid
	INNER JOIN vehicle veh ON veh.vehicleid = e.vehicleid
	WHERE e.vehicleid = vehicle_id
		AND dr.status=1
		AND e.exceptiontype = exceptiontype_id
		AND e.level =ANY(exception_levels)
		AND (DATE(e.enddatetime::timestamp at time zone c.afftimezone) between startdate and enddate)
	GROUP BY e.affiliateid,c.name,e.transporterid,tr.name ,e.driverid,dr.name,e.vehicleid,veh.vehicledesc
	ORDER BY exceptionvalue DESC;
	
	END IF ;
END;$function$
