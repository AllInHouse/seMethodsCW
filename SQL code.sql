--All the countries in the world organised by largest population to smallest:
SELECT name FROM country
ORDER BY population DESC

--All the countries in a continent organised by largest population to smallest:
SELECT name FROM country
ORDER BY continent, population DESC

--All the countries in a region organised by largest population to smallest:
SELECT name FROM country
ORDER BY region, population DESC

--The top N populated countries in the world where N is provided by the user:


--The top N populated countries in a region where N is provided by the user:


--All the cities in the world organised by largest population to smallest:
SELECT name FROM city
ORDER BY population DESC

--All the cities in a continent organised by largest population to smallest:
SELECT name FROM city
JOIN country ON CountryCode = country.code
ORDER BY continent, population DESC

--All the cities in a region organised by largest population to smallest:
SELECT name FROM city
JOIN country ON CountryCode = country.code
ORDER BY region, population DESC

--All the cities in a country organised by largest population to smallest:
SELECT name FROM city
JOIN country ON CountryCode = country.code
ORDER BY country.name, population DESC

--All the cities in a district organised by largest population to smallest:
SELECT name FROM city
ORDER BY district, population DESC

--The top N populated cities in the world where N is provided by the user:


--The top N populated cities in a continent where N is provided by the user:


--The top N populated cities in a region where N is provided by the user:


--The top N populated cities in a country where N is provided by the user:


--The top N populated cities in a district where N is provided by the user:


--All the capital cities in the world organised by largest population to smallest:
SELECT capital FROM country
ORDER BY population DESC

--All the capital cities in a continent organised by largest population to smallest:
SELECT capital FROM country
ORDER BY continent, population DESC

--All the capital cities in a region organised by largest to smallest:
SELECT capital FROM country
ORDER BY region, population DESC

--The top N populated capital cities in the world where N is provided by the user:


--The top N populated capital cities in a continent where N is provided by the user:


--The top N populated capital cities in a region where N is provided by the user:


--The population of people, people living in cities, and people not living in cities in each continent:
SELECT population, city.population, SUM(population - city.population) FROM country
JOIN city ON code = city.CountryCode
GROUP BY continent

--The population of people, people living in cities, and people not living in cities in each region:--
SELECT population, city.population, SUM(population - city.population) FROM country
JOIN city ON code = city.CountryCode
GROUP BY region

--The population of people, people living in cities, and people not living in cities in each country:
SELECT population, city.population, SUM(population - city.population) FROM country
JOIN city ON code = city.CountryCode
GROUP BY population

--Populations
SELECT SUM(population) FROM country

SELECT name, population FROM country
ORDER BY continent, population DESC

SELECT name, population FROM country
ORDER BY region, population DESC

SELECT name, population FROM country
ORDER BY population DESC

SELECT name, population FROM city
ORDER BY district, population DESC

SELECT name, population FROM city
ORDER BY population DESC

--Language Report