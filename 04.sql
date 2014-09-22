-- script: 04
-- Assignment 2
-- COSC 5376 
-- Data Warehouses
-- by Gaurav Deshmukh

create or replace view v04_a as
select campus, sum(hours11) sum11
from database
group by campus
order by sum11 asc;

create or replace view v04_b as
select campus, sum(hours12) sum12
from database
group by campus
order by sum12 asc;

create or replace view v04_c as
select campus, sum(hours13) sum13
from database
group by campus
order by sum13 asc;

create or replace view v04_d as
select campus,hours11,hours12,hours13
from database;


create or replace view v04_v as
select a.campus,sum11,sum12,sum13,hours11,hours12,hours13 
from
v04_a a full join v04_b b
on a.campus = b.campus
full join v04_c c
on b.campus = c.campus
full join v04_d d
on c.campus = d.campus;

select campus,
sum(hours11) over (order by sum11 rows between unbounded preceding and current row)runningSum11,
sum(hours12) over (order by sum12 rows between unbounded preceding and current row)runningSum12,
sum(hours13) over (order by sum13 rows between unbounded preceding and current row)runningSum13
from v04_v;
