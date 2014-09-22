-- script: 06
-- Assignment 2
-- COSC 5376 
-- Data Warehouses
-- by Gaurav Deshmukh
create or replace view v06_a as
select district,districtname,avg11,avg13,(avg13 - avg11)change
from
(select district,districtname,avg(items1) avg11,avg(items3)avg13
from database
group by district,districtname);

create or replace view v06_b as
select districtname,district,max(change)maxchange
from v06_a
group by districtname,district
order by maxchange;

create or replace view v06_c as
select districtname,district,
rank() over (order by maxchange) as district_max_change_rank
from v06_b;

select district,districtname
from v06_c
where district_max_change_rank between 1 and 5
order by district_max_change_rank;