-- script: 02-cube operator
-- Assignment 2
-- COSC 5376 
-- Data Warehouses
-- by Gaurav Deshmukh

select sum(hours11), county, district, campus
from database
group by cube(county,district,campus);
