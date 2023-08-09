CREATE OR REPLACE FUNCTION public.sp_weeklyexceptionreport(transporter_id integer,vehicle_id integer, driver_id integer, exception_levels  integer[], 
		start_date date, end_date date, recording_weight numeric, alert_weight numeric,alarm_weight numeric)
 RETURNS TABLE(istotal integer,ovrspdcount numeric,ovrspdduration numeric,accbrkcount numeric,accbrkduration numeric,ngtdcount numeric,ngtdduration numeric,contdrvecount numeric,contdrveduration numeric,
	dailydrivecount numeric,dailydriveduration numeric,dailyrestcount numeric,dailyrestduration numeric,totalduration numeric,totaldistance numeric)
 LANGUAGE plpgsql
AS $function$

BEGIN
RETURN query
	 select 0 as istotal,
	 sum(coalesce(weeklyresult.ovrspdcount,0.0))::NUMERIC as ovrspdcount,
	 sum(coalesce(weeklyresult.ovrspdduration,0.0))::NUMERIC as ovrspdduration,
	 sum(coalesce(weeklyresult.accbrkcount,0.0))::NUMERIC as accbrkcount,
	 sum(coalesce(weeklyresult.accbrkduration,0.0))::NUMERIC as accbrkduration,
	 sum(coalesce(weeklyresult.ngtdcount,0.0))::NUMERIC as ngtdcount,
	 sum(coalesce(weeklyresult.ngtdduration,0.0))::NUMERIC as ngtdduration,
	 sum(coalesce(weeklyresult.contdrvecount,0.0))::NUMERIC as contdrvecount,
	 sum(coalesce(weeklyresult.contdrveduration,0.0))::NUMERIC as contdrveduration,
	 sum(coalesce(weeklyresult.dailydrivecount,0.0))::NUMERIC as dailydrivecount,
	 sum(coalesce(weeklyresult.dailydriveduration,0.0))::NUMERIC as dailydriveduration,
	 sum(coalesce(weeklyresult.dailyrestcount,0.0))::NUMERIC as dailyrestcount,
	 sum(coalesce(weeklyresult.dailyrestduration,0.0))::NUMERIC as dailyrestduration,
	 sum(coalesce(weeklyresult.totalduration,0.0))::NUMERIC as totalduration,
	 sum(coalesce(weeklyresult.totaldistance,0.0))::NUMERIC as totaldistance 
	 from (
	 select CASE WHEN (e.exceptiontype=1) THEN 
	 0+(recording_weight*coalesce((SELECT count(ex.exceptionid) FROM exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid WHERE 
	 ex.level=1  AND ex.level =ANY(exception_levels) AND ex.exceptiontype = 1 AND ex.driverid = e.driverid AND ex.vehicleid = e.vehicleid 
	 AND DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between start_date and end_date)*100/
	 (select totdist from sp_get_totdistance(start_date , end_date, e.vehicleid, e.driverid, e.transporterid , e.affiliateid )),0.0))+
	 (alert_weight*coalesce((SELECT count(ex.exceptionid) FROM exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid WHERE 
	 ex.level=2  AND ex.level =ANY(exception_levels) AND ex.exceptiontype = 1 AND ex.driverid = e.driverid AND ex.vehicleid = e.vehicleid AND 
	 DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between start_date and end_date)*100/
	 (select totdist from sp_get_totdistance(start_date , end_date, e.vehicleid, e.driverid, e.transporterid , e.affiliateid )),0.0))+
	 (alarm_weight*coalesce((SELECT count(ex.exceptionid) FROM exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid 
	 WHERE ex.level=3  AND ex.level =ANY(exception_levels) AND ex.exceptiontype = 1 AND ex.driverid = e.driverid AND ex.vehicleid = e.vehicleid AND 
	 DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between start_date and end_date)*100/
	 (select totdist from sp_get_totdistance(start_date , end_date, e.vehicleid, e.driverid, e.transporterid , e.affiliateid )),0.0)) END 
	 AS ovrspdcount,CASE WHEN (e.exceptiontype=1) THEN SUM(extract(epoch from e.enddatetime::timestamp at time zone 
	 c.afftimezone - e.startdatetime::timestamp at time zone c.afftimezone)/3600) END AS ovrspdduration,CASE WHEN (e.exceptiontype=2 OR 
	 e.exceptiontype=3) THEN 0+(recording_weight*coalesce((SELECT count(ex.exceptionid)FROM exception ex INNER JOIN customeraffiliate caf ON 
	 caf.affiliateid = ex.affiliateid WHERE ex.level=1  AND ex.level =ANY(exception_levels) AND ex.exceptiontype in (2,3) AND 
	 ex.driverid = e.driverid AND ex.vehicleid = e.vehicleid AND DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) 
	 between start_date and end_date)*100/(select totdist from sp_get_totdistance(start_date , end_date, e.vehicleid, e.driverid, 
	 e.transporterid , e.affiliateid )),0.0))+(alert_weight*coalesce((SELECT count(ex.exceptionid)FROM exception ex INNER JOIN customeraffiliate caf ON 
	 caf.affiliateid = ex.affiliateid WHERE ex.level=2  AND ex.level =ANY(exception_levels) AND ex.exceptiontype in (2,3) AND ex.driverid = e.driverid AND 
	 ex.vehicleid = e.vehicleid AND DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between start_date and end_date)*100/
	 (select totdist from sp_get_totdistance(start_date , end_date, e.vehicleid, e.driverid, e.transporterid , e.affiliateid )),0.0))+
	 (alarm_weight*coalesce((SELECT count(ex.exceptionid)FROM exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid WHERE 
	 ex.level=3  AND ex.level =ANY(exception_levels) AND ex.exceptiontype in (2,3) AND ex.driverid = e.driverid AND ex.vehicleid = e.vehicleid AND 
	 DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between start_date and end_date)*100/
	 (select totdist from sp_get_totdistance(start_date , end_date, e.vehicleid, e.driverid, e.transporterid , e.affiliateid )),0.0)) 
	 END AS accbrkcount,CASE  WHEN (e.exceptiontype=2 OR e.exceptiontype=3) THEN SUM(extract(epoch from e.enddatetime::timestamp at time zone 
	 c.afftimezone - e.startdatetime::timestamp at time zone c.afftimezone)/3600) END AS accbrkduration, CASE  WHEN (e.exceptiontype=4) THEN  
	 (SELECT count(ex.exceptionid) FROM exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid WHERE 
	 ex.level =ANY(exception_levels) AND ex.exceptiontype = e.exceptiontype AND ex.driverid = e.driverid AND ex.vehicleid = e.vehicleid AND 
	 DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between start_date and end_date)  END AS ngtdcount, 
	 CASE  WHEN (e.exceptiontype=4) THEN  (SELECT SUM(ex.timeexceeded) FROM exception ex INNER JOIN customeraffiliate caf ON 
	 caf.affiliateid = ex.affiliateid WHERE ex.level =ANY(exception_levels) AND ex.exceptiontype = e.exceptiontype AND ex.driverid = e.driverid AND 
	 ex.vehicleid = e.vehicleid AND DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between start_date and end_date)  
	 END AS ngtdduration, CASE  WHEN (e.exceptiontype=5) THEN  (SELECT count(ex.exceptionid) FROM exception ex INNER JOIN 
	 customeraffiliate caf ON caf.affiliateid = ex.affiliateid WHERE ex.level =ANY(exception_levels) AND ex.exceptiontype = e.exceptiontype AND 
	 ex.driverid = e.driverid AND ex.vehicleid = e.vehicleid AND DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between 
	 start_date and end_date)  END AS contdrvecount, CASE  WHEN (e.exceptiontype=5) THEN  (SELECT SUM(ex.timeexceeded) FROM 
	 exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid WHERE ex.level =ANY(exception_levels) AND 
	 ex.exceptiontype = e.exceptiontype AND ex.driverid = e.driverid AND ex.vehicleid = e.vehicleid AND DATE(ex.enddatetime::timestamp at 
	 time zone caf.afftimezone) between start_date and end_date)  END AS contdrveduration,CASE  WHEN (e.exceptiontype=6) THEN  
	 (SELECT count(ex.exceptionid) FROM exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid WHERE 
	 ex.level =ANY(exception_levels) AND ex.exceptiontype = e.exceptiontype AND ex.driverid = e.driverid AND ex.vehicleid = e.vehicleid AND 
	 DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between start_date and end_date)  END AS dailydrivecount, 
	 CASE  WHEN (e.exceptiontype=6) THEN  (SELECT SUM(ex.timeexceeded) FROM exception ex INNER JOIN customeraffiliate caf ON 
	 caf.affiliateid = ex.affiliateid WHERE ex.level =ANY(exception_levels) AND ex.exceptiontype = e.exceptiontype AND ex.driverid = e.driverid AND 
	 ex.vehicleid = e.vehicleid AND DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) between start_date and end_date)  
	 END AS dailydriveduration, CASE  WHEN (e.exceptiontype=7) THEN  (SELECT count(ex.exceptionid) FROM exception ex INNER JOIN 
	 customeraffiliate caf ON caf.affiliateid = ex.affiliateid WHERE ex.level =ANY(exception_levels) AND ex.exceptiontype = e.exceptiontype AND 
	 ex.driverid = e.driverid AND ex.vehicleid = e.vehicleid AND DATE(ex.enddatetime::timestamp at time zone caf.afftimezone) 
	 between start_date and end_date)  END AS dailyrestcount, CASE  WHEN (e.exceptiontype=7) THEN  (SELECT SUM(ex.timeexceeded) 
	 FROM exception ex INNER JOIN customeraffiliate caf ON caf.affiliateid = ex.affiliateid WHERE ex.level =ANY(exception_levels) AND 
	 ex.exceptiontype = e.exceptiontype AND ex.driverid = e.driverid AND ex.vehicleid = e.vehicleid AND DATE(ex.enddatetime::timestamp at 
	 time zone caf.afftimezone) between start_date and end_date)  END AS dailyrestduration, 0.0 as totalduration ,0.0 as totaldistance 
	 FROM "exception" as e left join "customeraffiliate" as c on c.affiliateid=e.affiliateid where 1=1  and e.transporterid=transporter_id and 
	 (e.vehicleid=vehicle_id or vehicle_id=0) and (e.driverid=driver_id or driver_id=0) and e.level =ANY(exception_levels)   and date(e.enddatetime::timestamp at time zone c.afftimezone) between 
	 start_date and end_date  group by e.exceptiontype,e.affiliateid,e.transporterid,e.driverid,e.vehicleid) as weeklyresult group by 
	 istotal;
	
	
	
	
	
END;

$function$
