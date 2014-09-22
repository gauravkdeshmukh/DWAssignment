-- script: 03
-- Assignment 2
-- COSC 5376 
-- Data Warehouses
-- by Gaurav Deshmukh

create or replace view v03_a as
select avg(hours11) avg,region,districtname
from database
group by region,districtname
order by region;

create or replace view v03_b as
select districtname,region,
rank() over (partition by region order by avg) as region_avg_rank,avg
from v03_a
order by region;

select districtname,region,region_avg_rank
from v03_b
where region_avg_rank between 1 and 3
order by region;