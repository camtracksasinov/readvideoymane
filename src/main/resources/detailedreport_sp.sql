CREATE OR REPLACE FUNCTION public.sp_detailedexceptionreport(customer_id integer,affiliate_id integer,transporter_id integer,
vehicle_id integer, exception_type integer, exception_levels  integer[], start_date date, end_date date)
 RETURNS TABLE(exceptiontype integer, clientname character varying, affiliatename character varying, transportername character varying,
 vehiclename character varying,drivername character varying ,startdatetime text,enddatetime text,
 parameter character varying,parameterlabel character varying ,totalduration numeric,
 totaldistance numeric,level  character varying,levellabel  character varying,threshold text,maxvalue  numeric,distanceunderexception  numeric,timeexceeded  numeric,
 requiredbreak  numeric,numberofbreak  numeric,
 maxbreak numeric, startgps character varying, endgps character varying)
 LANGUAGE plpgsql
AS $function$

BEGIN
RETURN query


SELECT e.exceptiontype as exceptiontype, cust.name as clientname,c.name as affiliatename,t.name as transportername,
v.vehicledesc as vehiclename,d.name as drivername,
CONCAT( to_char(e.startdatetime::timestamp at time zone c.afftimezone, 'YYYY-MM-DD'),' ', to_char(e.startdatetime::timestamp at time zone c.afftimezone,'HH24:MI:SS') ) as startdatetime,
CONCAT( to_char(e.enddatetime::timestamp at time zone c.afftimezone, 'YYYY-MM-DD'),' ', to_char(e.enddatetime::timestamp at time zone c.afftimezone,'HH24:MI:SS') ) as enddatetime,
p.name as parameter, p.label as parameterlabel,
CASE WHEN e.exceptiontype in (4) THEN null ELSE coalesce(e.totalduration,0) END as totalduration,
CASE WHEN e.exceptiontype in (2,3,4,7,8) THEN null ELSE coalesce(e.totaldistance,0) END as totaldistance,
el.name as level, el.label as levellabel,
CASE WHEN e.exceptiontype in (4) THEN (sp_getthresholdinterval(DATE(e.enddatetime::timestamp at time zone c.afftimezone),e.affiliateid,e.exceptiontype,c.afftimezone )) ELSE concat(round(coalesce(e.threshold,0),2),'') END as threshold,
CASE WHEN e.exceptiontype in (4,5,6,7,8) THEN null ELSE coalesce(e.maxvalue,0) END as maxvalue ,
CASE WHEN e.exceptiontype in (2,3,7,8) THEN null ELSE coalesce(e.distanceunderexception,0) END as distanceunderexception,
CASE WHEN e.exceptiontype in (2,3) THEN null ELSE coalesce(e.timeexceeded,0) END as timeexceeded,
CASE WHEN e.exceptiontype in (2,3,4,6,7,8) THEN null ELSE coalesce(e.requiredbreak,0) END as requiredbreak,
CASE WHEN e.exceptiontype in (2,3,6,7,8) THEN null ELSE coalesce(e.numberofbreak,0) END as numberofbreak,
CASE WHEN e.exceptiontype in (2,3,4,6,7,8) THEN null ELSE coalesce(e.maxbreak,0) END as maxbreak,
CASE WHEN e.startgps IS NULL THEN e.endgps ELSE e.startgps END as  startgps,e.endgps 
FROM "exception" as e, "customer" as cust,"customeraffiliate" as c,"transporter" as t,"vehicle" as v,"driver" as d,
"exceptionlevel" as el,"parametertype" as p 
where c.customerid=cust.customerid and e.affiliateid=c.affiliateid and e.transporterid=t.transporterid
and v.vehicleid=e.vehicleid and d.driverid=e.driverid and e.level=el.exceptionlevelid and e.exceptiontype=p.parametertypeid 
and e.exceptiontype not in (8,9)  
and (c.customerid=customer_id OR customer_id=0)
and (e.affiliateid=affiliate_id OR affiliate_id=0)
and (e.transporterid=transporter_id OR transporter_id=0)
and (e.vehicleid=vehicle_id OR vehicle_id=0) 
and (e.exceptiontype=exception_type OR exception_type=0)
and e.level= ANY(exception_levels)
and DATE(e.enddatetime::timestamp at time zone c.afftimezone) between start_date and end_date
order by e.startdatetime::timestamp at time zone c.afftimezone asc;


	
	
END;

$function$
