--All the countries in the world organised by largest population to smallest:
SELECT code, name, continent, region, population, capital FROM country
ORDER BY population DESC

--All the countries in a continent organised by largest population to smallest:
SELECT code, name, continent, region, population, capital FROM country
ORDER BY continent, population DESC

--All the countries in a region organised by largest population to smallest:
SELECT code, name, continent, region, population, capital FROM country
ORDER BY region, population DESC

--The top N populated countries in the world where N is provided by the user:
SELECT code, name, continent, region, population, capital FROM country LIMIT 
ORDER BY population DESC

--The top N populated countries in a region where N is provided by the user:
SELECT code, name, continent, region, population, capital FROM country LIMIT 
ORDER BY region, population DESC

--All the cities in the world organised by largest population to smallest:
SELECT name, country.name, district, population FROM city
JOIN country ON CountryCode = country.code 
ORDER BY population DESC

--All the cities in a continent organised by largest population to smallest:
SELECT name, country.name, district, population FROM city
JOIN country ON CountryCode = country.code
ORDER BY continent, population DESC

--All the cities in a region organised by largest population to smallest:
SELECT name, country.name, district, population FROM city
JOIN country ON CountryCode = country.code
ORDER BY region, population DESC

--All the cities in a country organised by largest population to smallest:
SELECT name, country.name, district, population FROM city
JOIN country ON CountryCode = country.code 
ORDER BY country, population DESC

--All the cities in a district organised by largest population to smallest:
SELECT name, country.name, district, population FROM city
JOIN country ON CountryCode = country.code 
ORDER BY district, population DESC

--The top N populated cities in the world where N is provided by the user:
SELECT name, country.name, district, population FROM city LIMIT 
JOIN country ON CountryCode = country.code 
ORDER BY population DESC

--The top N populated cities in a continent where N is provided by the user:
SELECT name, country.name, district, population FROM city LIMIT
JOIN country ON CountryCode = country.code 
ORDER BY continent, population DESC

--The top N populated cities in a region where N is provided by the user:
SELECT name, country.name, district, population FROM city LIMIT
JOIN country ON CountryCode = country.code 
ORDER BY region, population DESC

--The top N populated cities in a country where N is provided by the user:
SELECT name, country.name, district, population FROM city LIMIT
JOIN country ON CountryCode = country.code 
ORDER BY country, population DESC

--The top N populated cities in a district where N is provided by the user:
SELECT name, country.name, district, population FROM city LIMIT 
ORDER BY district, population DESC

--All the capital cities in the world organised by largest population to smallest:
SELECT capital, name, population FROM country
ORDER BY population DESC

--All the capital cities in a continent organised by largest population to smallest:
SELECT capital, name, population FROM country
ORDER BY continent, population DESC

--All the capital cities in a region organised by largest to smallest:
SELECT capital, name, population FROM country
ORDER BY region, population DESC

--The top N populated capital cities in the world where N is provided by the user:
SELECT capital, name, population FROM country LIMIT 
ORDER BY population DESC

--The top N populated capital cities in a continent where N is provided by the user:
SELECT capital, name, population FROM country LIMIT 
ORDER BY continent, population DESC

--The top N populated capital cities in a region where N is provided by the user:
SELECT capital, name, population FROM country LIMIT 
ORDER BY region, population DESC

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

SELECT name, SUM(population) FROM country
GROUP BY continent
ORDER BY population DESC

SELECT name, SUM(population) FROM country
GROUP BY region
ORDER BY population DESC

SELECT name, population FROM country
ORDER BY population DESC

SELECT name, SUM(population) FROM city
GROUP BY district
ORDER BY population DESC

SELECT name, population FROM city
ORDER BY population DESC

--Language Report
SELECT language, population, SUM(countrylanguage.percentage) FROM country
JOIN countrylanguage ON code = countrylanguage.CountryCode
WHERE language = "Chinese"
ORDER BY population DESC

SELECT language, population, SUM(countrylanguage.percentage) FROM country
JOIN countrylanguage ON code = countrylanguage.CountryCode
WHERE language = "English"
ORDER BY population DESC

SELECT language, population, SUM(countrylanguage.percentage) FROM country
JOIN countrylanguage ON code = countrylanguage.CountryCode
WHERE language = "Hindi"
ORDER BY population DESC

SELECT language, population, SUM(countrylanguage.percentage) FROM country
JOIN countrylanguage ON code = countrylanguage.CountryCode
WHERE language = "Spanish"
ORDER BY population DESC

SELECT language, population, SUM(countrylanguage.percentage) FROM country
JOIN countrylanguage ON code = countrylanguage.CountryCode
WHERE language = "Arabic"
ORDER BY population DESC