--All the countries in the world organised by largest population to smallest:
SELECT Code, Name, Continent, Region, Population, Capital FROM country
ORDER BY Population DESC

--All the countries in a continent organised by largest population to smallest:
SELECT Code, Name, Continent, Region, Population, Capital FROM country
ORDER BY Continent, Population DESC

--All the countries in a region organised by largest population to smallest:
SELECT Code, Name, Continent, Region, Population, Capital FROM country
ORDER BY Region, Population DESC

--The top N populated countries in the world where N is provided by the user:
SELECT Code, Name, Continent, Region, Population, Capital FROM country
ORDER BY Population DESC
LIMIT 

--The top N populated countries in a continent where N is provided by the user:
SELECT Code, Name, Continent, Region, Population, Capital FROM country
ORDER BY Continent, Population DESC
LIMIT 

--The top N populated countries in a region where N is provided by the user:
SELECT Code, Name, Continent, Region, Population, Capital FROM country
ORDER BY Region, Population DESC
LIMIT 

--All the cities in the world organised by largest population to smallest:
SELECT Name, country.Name, District, Population FROM city
JOIN country ON CountryCode = country.Code 
ORDER BY Population DESC

--All the cities in a continent organised by largest population to smallest:
SELECT Name, country.Name, District, Population FROM city
JOIN country ON CountryCode = country.Code
ORDER BY Continent, Population DESC

--All the cities in a region organised by largest population to smallest:
SELECT Name, country.Name, District, Population FROM city
JOIN country ON CountryCode = country.Code
ORDER BY Region, Population DESC

--All the cities in a country organised by largest population to smallest:
SELECT Name, country.Name, District, Population FROM city
JOIN country ON CountryCode = country.Code 
ORDER BY country.Name, Population DESC

--All the cities in a district organised by largest population to smallest:
SELECT Name, country.Name, District, Population FROM city
JOIN country ON CountryCode = country.Code 
ORDER BY District, Population DESC

--The top N populated cities in the world where N is provided by the user:
SELECT Name, country.Name, District, Population FROM city
JOIN country ON CountryCode = country.Code 
ORDER BY Population DESC
LIMIT 

--The top N populated cities in a continent where N is provided by the user:
SELECT Name, country.Name, District, Population FROM city
JOIN country ON CountryCode = country.Code 
ORDER BY Continent, Population DESC
LIMIT 

--The top N populated cities in a region where N is provided by the user:
SELECT Name, country.Name, District, Population FROM city
JOIN country ON CountryCode = country.Code 
ORDER BY Region, Population DESC
LIMIT 

--The top N populated cities in a country where N is provided by the user:
SELECT Name, country.Name, District, Population FROM city
JOIN country ON CountryCode = country.Code 
ORDER BY country.Name, Population DESC
LIMIT 

--The top N populated cities in a district where N is provided by the user:
SELECT Name, country.Name, District, Population FROM city
ORDER BY District, Population DESC
LIMIT 

--All the capital cities in the world organised by largest population to smallest:
SELECT Capital, Name, Population FROM country
ORDER BY Population DESC

--All the capital cities in a continent organised by largest population to smallest:
SELECT Capital, Name, Population FROM country
ORDER BY Continent, Population DESC

--All the capital cities in a region organised by largest to smallest:
SELECT Capital, Name, Population FROM country
ORDER BY Region, Population DESC

--The top N populated capital cities in the world where N is provided by the user:
SELECT Capital, Name, Population FROM country
ORDER BY Population DESC
LIMIT 

--The top N populated capital cities in a continent where N is provided by the user:
SELECT Capital, Name, Population FROM country
ORDER BY Continent, Population DESC
LIMIT 

--The top N populated capital cities in a region where N is provided by the user:
SELECT Capital, Name, Population FROM country
ORDER BY Region, Population DESC
LIMIT 

--The population of people, people living in cities, and people not living in cities in each continent:
SELECT Continent, Population, city.Population, SUM(Population - city.Population) FROM country
JOIN city ON Code = city.CountryCode
GROUP BY Continent

--The population of people, people living in cities, and people not living in cities in each region:--
SELECT Region, Population, city.Population, SUM(Population - city.Population) FROM country
JOIN city ON Code = city.CountryCode
GROUP BY Region

--The population of people, people living in cities, and people not living in cities in each country:
SELECT Name, Population, city.Population, SUM(Population - city.Population) FROM country
JOIN city ON Code = city.CountryCode
GROUP BY Name

--Populations
SELECT SUM(Population) FROM country

SELECT Continent, SUM(Population) FROM country
GROUP BY Continent
ORDER BY SUM(Population) DESC

SELECT Region, SUM(Population) FROM country
GROUP BY Region
ORDER BY SUM(Population) DESC

SELECT Name, Population FROM country
ORDER BY Population DESC

SELECT District, SUM(Population) FROM city
GROUP BY District
ORDER BY SUM(Population) DESC

SELECT Name, Population FROM city
ORDER BY Population DESC

--Language Report
SELECT countrylanguage.Language, Population, SUM(countrylanguage.Percentage) FROM country
JOIN countrylanguage ON Code = countrylanguage.CountryCode
WHERE countrylanguage.Language = "Chinese"
ORDER BY Population DESC

SELECT countrylanguage.Language, Population, SUM(countrylanguage.Percentage) FROM country
JOIN countrylanguage ON Code = countrylanguage.CountryCode
WHERE countrylanguage.Language = "English"
ORDER BY Population DESC

SELECT countrylanguage.Language, Population, SUM(countrylanguage.Percentage) FROM country
JOIN countrylanguage ON Code = countrylanguage.CountryCode
WHERE countrylanguage.Language = "Hindi"
ORDER BY Population DESC

SELECT countrylanguage.Language, Population, SUM(countrylanguage.Percentage) FROM country
JOIN countrylanguage ON Code = countrylanguage.CountryCode
WHERE countrylanguage.Language = "Spanish"
ORDER BY Population DESC

SELECT countrylanguage.Language, population, SUM(countrylanguage.Percentage) FROM country
JOIN countrylanguage ON Code = countrylanguage.CountryCode
WHERE countrylanguage.Language = "Arabic"
ORDER BY population DESC