# Plotting Application

This application will plot all things (i.e : scooter) registered all over
Singapore. 

It has a search function where you enter search coordinate and then specify
the search radius to search all scooters within that radius.  

All scooters found then shown on the map with radius circle overlaid on them.

The initial data used here is gathered from : <https://simplemaps.com/data/sg-cities>.  
This data is saved in Apache Derby embedded database. You can add and modify the
data when application starts but it won't be saved when the application stops.

Client side for this application is implemented using Datatables framework.  

This is meant to be for educational purpose only.

## Search algorithm
________________________________________________________________________________

We simply calculate the distance between center point of the search coordinate 
and the location of all scooters. Since calculating all distances in large datasets
require is not scalable not efficient, we first narrow down the search query by
excluding all scooters located too far away from the radius. 
 

To narrow it down :  
1. Get the search radius in meters from user
2. Convert that radius into degree (lat and lon)
3. Our scooters should be inside the search boundary, so we need to calculate
   minimum latitude and longitude, and maximum latitude and longitude, this will
   result in a square search area
4. Query the result
5. Now we got the result, but some of the data is actually outside of radius
   since our search area is a circle and not a square boundary
6. Calculate all scooters distance with the search coordinate
7. Discard scooters from search result if distance is larger than the radius
8. Return the result to user

Some of the calculation (1, 2, 3, 6, 7) can be done in client side to ease the burden of our
application server. But in this case, we simply calculate all of them in our
backend.


## Installation
________________________________________________________________________________

### Clone and checkout the project

Clone this repo into your local repo and setup local development environment using 
your favorite IDE

### Run the executable war file

Download **plot-0.0.1-SNAPSHOT.war** file and execute it in command prompt :
```
java -jar plot-0.0.1-SNAPSHOT.war
```

## How to use
________________________________________________________________________________

1. Open <http://localhost:8080/> on the browser, it will display all scooters  
   located all over Singapore.
   ![image](https://raw.githubusercontent.com/guhya/boot-plot/master/src/main/webapp/resources/1.png)  

2. Click one of the scooter to copy it's coordinate to the search form and show]
   its location on the map.      
   ![image](https://raw.githubusercontent.com/guhya/boot-plot/master/src/main/webapp/resources/2.png)  
   ![image](https://raw.githubusercontent.com/guhya/boot-plot/master/src/main/webapp/resources/3.png)  
   
3. Increase search radius to expand the search area
   ![image](https://raw.githubusercontent.com/guhya/boot-plot/master/src/main/webapp/resources/4.png)  
   
4. The resulting API calls and response is can be checked on your browser debug windows
   ![image](https://raw.githubusercontent.com/guhya/boot-plot/master/src/main/webapp/resources/5.png)  

5. To show all scooters again, simply refresh the browser
