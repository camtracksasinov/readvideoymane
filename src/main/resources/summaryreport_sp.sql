CREATE OR REPLACE FUNCTION public.sp_summaryreport(customer_id integer,affiliate_id integer,transporter_id integer,
exception_type integer, exception_level  integer, start_date date, end_date date)
 RETURNS TABLE(exceptioncount bigint)
 LANGUAGE plpgsql
AS $function$

BEGIN
RETURN query

SELECT count(exceptionid) as exceptioncount FROM "exception" as e, customeraffiliate as aff  where e.affiliateid=aff.affiliateid 
and (aff.customerid=customer_id OR customer_id=0)
and (e.affiliateid=affiliate_id OR affiliate_id=0) 
and (e.transporterid=transporter_id OR transporter_id=0) 
and (e.exceptiontype=exception_type OR exception_type=0)
and (e.level=exception_level OR exception_level=0)
and DATE(e.enddatetime::timestamp at time zone aff.afftimezone) between start_date and end_date;
	
END;

$function$