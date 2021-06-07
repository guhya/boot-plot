# Problem statement :
Plot all the scooters in Singapore on a map (hundreds of thousands) for our operations team to understand where all the scooters currently are. 

## Key points :
- Usability 
- Performance 
- Scalability

# Solution :

## Architecture
First of all, we will design our application as a monolithic 3-tiered architecture. 

1. Data tier, which holds all our scooter data
   - Speed of the query is important, and all our data are structured, so in this case a relational database is more appropriate
   - Some of databases are built wit GIS function to speedup GIS related data storage and query
   - Database should be separated into 2 types, 
     * master database where it should serve all POST/PUT/DELETE operation and 
	 * slave database where it should serve all GET (read only) operation 
   - Database should be mirrored into active-standby mode to increase availability
   - All data should be properly indexed
   - Some of the non crucial information such as memory intensive sorting, filtering can be offloaded to business tier to further ease
     the burden of slave database
   
2. Business tier where business logic of our application resides
   - Multiple light-weight application server such as Apache Tomcat can be deployed here, which then fronted with a load balancer
     with a simple round-robin algorithm to evenly spread out the load
   - Whenever the traffic spiked, we should scale out this tier by adding more application server, can be done automatically if 
     we are developing this system in the cloud
   - Some of of operation and data transformation should be done in this tier before the result is delivered to the client
   - Caching strategy can also be used here

3. Presentation tier, where the client of our application are located.
   - Website application
   - Javascript single page application
   - Mobile/table application
   
   The nature of communication between presentation tier and business tier are RESTful, using JSON as data exchange format.
   Since the client is operation team, the authentication between client and server (presentation tier and business tier) are
   done using light-weight JWT authentication method.
   
## Plotting data :	

Rendering thousands of data onto the map is expensive, time consuming and unusable, so we just render the subset of necessary data within
viewing radius. When the map is zoomed out and all part of the country is visible, the data should be summarized and then displayed. 

Instead of displaying all data points and in result cluttering the map, the total count of scooters is displayed (ex. by city). 
And when the map is zoomed in, the next summary of data is shown (ex. by district). And so on and so forth until the zoom in is maxed out.

The same strategy of displaying only the data within visible view is also applied when we drag the map. We should only display data within certain radius
of center coordinate in the visible map.

To support all this, city, district, block need to be included in our scooter location data. And the composite index should roughly look like this :
- cityID->districtID->blockID->scooterID. 
That index should effectively cover our scooter search query.


   