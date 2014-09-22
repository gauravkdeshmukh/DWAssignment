-- script: 09
-- Assignment 2
-- COSC 5376 
-- Data Warehouses
-- by Gaurav Deshmukh

create or replace view v09_a as
select countyname,count(campus) cnt
from
(select countyname,campus
from
(select county,countyname,hours11,campus,
(100 * percent_rank() over (order by hours11)) hours11_perc_rank
from database)
where hours11_perc_rank > 95)
group by countyname;

select countyname as countyname11
from v09_a
where cnt in (select max(cnt) cnt from v09_a);

create or replace view v09_b as
select countyname,count(campus) cnt
from
(select countyname,campus
from
(select county,countyname,hours12,campus,
(100 * percent_rank() over (order by hours12)) hours12_perc_rank
from database)
where hours12_perc_rank > 95)
group by countyname;


select countyname  as countyname12
from v09_b
where cnt in (select max(cnt) cnt from v09_b);

create or replace view v09_c as
select countyname,count(campus) cnt
from
(select countyname,campus
from
(select county,countyname,hours13,campus,
(100 * percent_rank() over (order by hours13)) hours13_perc_rank
from database)
where hours13_perc_rank > 95)
group by countyname;


select countyname  as countyname13
from v09_c
where cnt in (select max(cnt) cnt from v09_c);