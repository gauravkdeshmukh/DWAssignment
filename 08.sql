-- script: 08
-- Assignment 2
-- COSC 5376 
-- Data Warehouses
-- by Gaurav Deshmukh

create or replace view v08_a as
select items2,district,districtname,campus,
(100 * percent_rank() over (order by items2)) item_percent
from database
order by items2;

select distinct districtname
from v08_a
where item_percent > 90;