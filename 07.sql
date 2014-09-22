-- script: 07
-- Assignment 2
-- COSC 5376 
-- Data Warehouses
-- by Gaurav Deshmukh

select region,items1,hours11,avg(items1)
over (partition by region order by hours11 rows between 3 preceding and 3 following) movingAverage
from database;
