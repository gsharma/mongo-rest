# Mongo REST Data Service

## Overview
The Mongo Data Service is a RESTful data service intended for use in a distributed environment such as a large scale tiered services setup where the data tier can and often needs to be scaled out independent of the other services relying on the tier. A commonly used public cloud configuration is a multi-region multi-availability-zone AWS EC2 environment with the Mongo data tier secured by a VPN like AWS VPC or OpenSwan and RESTful data service access over HTTPS from a desktop or mobile client device on a public or a private network.  


## User Authentication
To use the API, you can use any HTTP client. All APIs use JSON as the data format. If you use Java, we recommend you use the Jersey client or HttpClient with Jackson for JSON support. All operations require HTTP Basic Authentication; service uses Apache Shiro underneath. I considered session leasing but the Basic Auth overhead is just not enough to justify lease management on the server-side.  


## Status Codes and Error Handling
When you call the REST APIs, the following HTTP status codes are returned:  

| Status      | Description |  
|:------------|:------------|  
| 200         | The operation was successful.|  
| 201         | The entity was created. The Location header will contain the URL to query the entity.|  
| 400         | There was bad input data submitted as part of the request.|  
| 401         | User credentials could not be authenticated.|  
| 404         | The entity was not found.|  
| 405         | The HTTP method is not supported.|  
| 409         | When creating an entity, an entity with that name already exists.|  
| 500         | The operation was unsuccessful. See the HTTP body for details.|  
| 503         | You have made too many requests to the Mongo Data Service and have presently been rate limited.|  
| 503         | Service is unavailable or performing in a degraded fashion.|  

When errors occur (for example, a 500 status code), the HTTP response contains a JSON response with an error message.  


## Service API
### 1. Create a database (POST <host:port>/api/mongo/databases)

### 2. Retrieve a database (GET <host:port>/api/mongo/databases/<dbName>)

### 3. Update a database (PUT <host:port>/api/mongo/databases/<dbName>)

### 4. Delete a database (DELETE <host:port>/api/mongo/databases/<dbName>)

### 5. Retrieve all databases (GET <host:port>/api/mongo/databases)

### 6. Delete all databases (DELETE <host:port>/api/mongo/databases)

### 7. Create a collection (POST <host:port>/api/mongo/databases/<dbName>/collections)

### 8. Retrieve a collection (GET <host:port>/api/mongo/databases/<dbName>/collections/<collName>)

### 9. Update a collection (PUT <host:port>/api/mongo/databases/<dbName>/collections/<collName>)

### 10. Delete a collection (DELETE <host:port>/api/mongo/databases/<dbName>/collections/<collName>)

### 11. Retrieve all collections (GET <host:port>/api/mongo/databases/<dbName>/collections)

### 12. Delete all collections (DELETE <host:port>/api/mongo/databases/<dbName>/collections)

### 13. Create an index (POST <host:port>/api/mongo/databases/<dbName>/collections/<collName>/indexes)

### 14. Retrieve an index (GET <host:port>/api/mongo/databases/<dbName>/collections/<collName>/indexes/<index>)

### 15. Delete an index (DELETE <host:port>/api/mongo/databases/<dbName>/collections/<collName>/indexes/<index>)

### 16. Retrieve all indexes (GET <host:port>/api/mongo/databases/<dbName>/collections/<collName>/indexes)

### 17. Delete all indexes (DELETE <host:port>/api/mongo/databases/<dbName>/collections/<collName>/indexes)

### 18. Create a document (POST <host:port>/api/mongo/databases/<dbName>/collections/<collName>/documents)

### 19. Retrieve a document (GET <host:port>/api/mongo/databases/<dbName>/collections/<collName>/documents/<docName>)

### 20. Update a document (PUT <host:port>/api/mongo/databases/<dbName>/collections/<collName>/documents/<docName>)

### 21. Delete a document (DELETE <host:port>/api/mongo/databases/<dbName>/collections/<collName>/documents/<docName>)

### 22. Retrieve all documents (GET <host:port>/api/mongo/databases/<dbName>/collections/<collName>/documents)

### 23. Delete all documents (DELETE <host:port>/api/mongo/databases/<dbName>/collections/<collName>/documents)

### 24. Ping Service (GET <host:port>/api/mongo/ping)


## Deployment
The service is deployable as a WAR on Jetty or Tomcat. If you have to, feel free to drop the WAR on a beefier app-server like JBoss/Weblogic/Websphere. The service does expect a MongoDB installation. Download and install your platform [MongoDB Release](http://www.mongodb.org/downloads)  


## Testing
The JUnit tests run against the service deployed on Jetty.  


## License
MIT License - Copyright (c) 2012 Gaurav Sharma  
