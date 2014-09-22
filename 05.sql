-- script: 05
-- Assignment 2
-- COSC 5376 
-- Data Warehouses
-- by Gaurav Deshmukh

create or replace view v05_a as
select sum(items1) totitem11,sum(items2) totitem12,sum(items3) totitem13,district,campusname
from database
group by district,campusname
order by totitem11,totitem12,totitem13;

create or replace view v05_b as
select campusname,district,
rank() over (partition by district order by totitem11,totitem12,totitem13) as district_sum_rank,totitem11,totitem12,totitem13
from v05_a
order by district;

select district,campusname,district_sum_rank rank
from v05_b
where district_sum_rank between 1 and 5
order by district;