CREATE OR REPLACE FUNCTION public.sp_weeklyrestdetails(vehicle_id integer,driver_id integer, start_date date, end_date date)
 RETURNS TABLE(exceptiontype character varying, levellabel text,totalduration numeric,threshold numeric,
 timeexceeded numeric,startgps character varying,endgps character varying,clientname character varying,affiliatename character varying,transportername character varying,
 vehiclename character varying,drivername character varying,startdatetime text,enddatetime text,parameter character varying,
 parameterlabel character varying)
 LANGUAGE plpgsql
AS $function$

BEGIN
RETURN query

SELECT e.exceptiontype as exceptiontype, null::text as levellabel, coalesce(e.totalduration,0) as totalduration,
coalesce(e.threshold,0) as threshold,e.timeexceeded,e.startposition as startgps,e.endpostion as endgps, 
cust.name as clientname,c.name as affiliatename,t.name as transportername,v.vehicledesc as vehiclename,
d.name as drivername,
CONCAT( to_char(e.startdatetime::timestamp at time zone c.afftimezone, 'YYYY-MM-DD'),' ', to_char(e.startdatetime::timestamp at time zone c.afftimezone,'HH24:MI:SS') ) as startdatetime,
CONCAT( to_char(e.enddatetime::timestamp at time zone c.afftimezone, 'YYYY-MM-DD'),' ', to_char(e.enddatetime::timestamp at time zone c.afftimezone,'HH24:MI:SS') ) as enddatetime,
p.name as parameter, p.label as parameterlabel 
FROM "continuousdrive" as e, "customer" as cust,"customeraffiliate" as c,
"transporter" as t,"vehicle" as v,"driver" as d,"parametertype" as p 
where c.customerid=cust.customerid and e.affiliateid=c.affiliateid and v.vehicleid=e.vehicleid 
and v.transporterid=t.transporterid and d.driverid=e.driverid and e.exceptiontype=concat('',p.parametertypeid) 
and e.vehicleid=vehicle_id and e.driverid=driver_id and e.exceptiontype='8' 
and DATE(e.enddatetime::timestamp at time zone c.afftimezone) between start_date and end_date
order by e.enddatetime at time zone c.afftimezone asc;
	
	
END;

$function$
