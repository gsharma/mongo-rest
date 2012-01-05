# Mongo REST Data Service

## Overview
The Mongo Data Service is a RESTful data service intended for use in a distributed environment such as a large scale tiered services setup where the data tier can and often needs to be scaled out independently of the other services relying on the tier. A commonly used public cloud configuration is a multi-region multi-availability-zone AWS EC2 environment with the Mongo data tier secured by a VPN like AWS VPC or OpenSwan and RESTful data service access over HTTPS from a desktop or mobile client device on a public or a private network.  


## User Authentication
To use the API, you can use any HTTP client. All APIs use JSON as the data format. If you use Java, recommend you use the Jersey client or HttpClient with Jackson for JSON support. All operations require HTTP Basic Authentication; service uses Apache Shiro underneath. I considered session leasing but the Basic Auth overhead is just not enough to justify lease management on the server-side.  


## Status Codes and Error Handling
When you call the REST APIs, the following HTTP status codes are returned:  

<table>
  <tr><th>Status</th><th>Description</th></tr>
  <tr><td>200</td><td>The operation was successful.</td></tr>
  <tr><td>201</td><td>The entity was created. The Location header will contain the URL to query the entity.</td></tr>
  <tr><td>400</td><td>There was bad input data submitted as part of the request.</td></tr>
  <tr><td>401</td><td>User credentials could not be authenticated.</td></tr>
  <tr><td>404</td><td>The entity was not found.</td></tr>
  <tr><td>405</td><td>The HTTP method is not supported.</td></tr>
  <tr><td>409</td><td>When creating an entity, an entity with that name already exists.</td></tr>
  <tr><td>500</td><td>The operation was unsuccessful. See the HTTP body for details.</td></tr>
  <tr><td>503</td><td>You have made too many requests to the Mongo Data Service and have presently been rate limited.</td></tr>
  <tr><td>503</td><td>Service is unavailable or performing in a degraded fashion.</td></tr>
</table>

When errors occur (for example, a 500 status code), the HTTP response contains a JSON response with an error message.  


## Service API
### 1. Create a database (POST host:port/api/mongo/databases)
Example Request:  
	POST http://localhost:9002/api/mongo/databases  
	Content-Type: application/json  
	{"name":"mongo-rest-test"}  

Example Response:  
	201  
	Content-Length: 0  
	Location: http://localhost:9002/api/mongo/databases/mongo-rest-test  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  

### 2. Retrieve a database (GET host:port/api/mongo/databases/*dbName*)
Example Request:  
	GET http://localhost:9002/api/mongo/databases/mongo-rest-test  
	Content-Type: application/json  

Example Response:  
	200  
	Transfer-Encoding: chunked  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  
	{"name":"mongo-rest-test",  
	"writeConcern":"SAFE",  
	"collections":null,  
	"stats":{"serverUsed":"127.0.0.1:27017","collections":"3","objects":"6","avgObjSize":"69.33333333333333","dataSize":"416","storageSize":"20480","numExtents":"3","indexes":"2","indexSize":"16352","fileSize":"67108864","nsSizeMB":"16","ok":"1.0"}}  

### 3. Update a database (PUT host:port/api/mongo/databases/*dbName*)
Example Request:  
	PUT http://localhost:9002/api/mongo/databases/mongo-rest-test  
	Content-Type: application/json  
	{"name":"mongo-rest-test","writeConcern":"FSYNC_SAFE"}  

Example Response:  
	200  
	Transfer-Encoding: chunked  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  
	"http://localhost:9002/api/mongo/databases/mongo-rest-test"  

### 4. Delete a database (DELETE host:port/api/mongo/databases/*dbName*)
Example Request:  
	DELETE http://localhost:9002/api/mongo/databases/mongo-rest-test  

Example Response:  
	200  
	Content-Length: 0  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  

### 5. Retrieve all databases (GET host:port/api/mongo/databases)
Example Request:  
	GET http://localhost:9002/api/mongo/databases  
	Content-Type: application/json  

Example Response:  
	200  
	Transfer-Encoding: chunked  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  
	[{"name":"mongo-rest-test1",  
	"writeConcern":"SAFE",  
	"collections":null,  
	"stats":{"serverUsed":"127.0.0.1:27017","collections":"3","objects":"6","avgObjSize":"70.0","dataSize":"420","storageSize":"20480","numExtents":"3","indexes":"2","indexSize":"16352","fileSize":"201326592","nsSizeMB":"16","ok":"1.0"}},  
	{"name":"mongo-rest-test2",  
	"writeConcern":"SAFE",  
	"collections":null,  
	"stats":{"serverUsed":"127.0.0.1:27017","collections":"3","objects":"6","avgObjSize":"70.0","dataSize":"420","storageSize":"20480","numExtents":"3","indexes":"2","indexSize":"16352","fileSize":"67108864","nsSizeMB":"16","ok":"1.0"}}]  

### 6. Delete all databases (DELETE host:port/api/mongo/databases)
Example Request:  
	DELETE http://localhost:9002/api/mongo/databases  

Example Response:  
	200  
	Transfer-Encoding: chunked  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  
	Deleted databases: [mongo-rest-test1, mongo-rest-test2]  

### 7. Create a collection (POST host:port/api/mongo/databases/*dbName*/collections)
Example Request:  
	POST http://localhost:9002/api/mongo/databases/mongo-rest-test/collections  
	Content-Type: application/json  
	{"name":"mongo-collection"}  

Example Response:  
	201  
	Content-Length: 0  
	Location: http://localhost:9002/api/mongo/databases/mongo-rest-test/collections/mongo-collection  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  

### 8. Retrieve a collection (GET host:port/api/mongo/databases/*dbName*/collections/*collName*)
Example Request:  
	GET http://localhost:9002/api/mongo/databases/mongo-rest-test/collections/mongo-test-collection  
	Content-Type: application/json  

Example Response:  
	200  
	Transfer-Encoding: chunked  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  
	{"name":"mongo-test-collection",  
	"writeConcern":"SAFE",  
	"indexes":[{"name":null,"dbName":"mongo-rest-test","keys":["_id"],"unique":false,"collectionName":"mongo-test-collection"}],  
	"documents":[],  
	"dbName":"mongo-rest-test"}  

### 9. Update a collection (PUT host:port/api/mongo/databases/*dbName*/collections/*collName*)
Example Request:  
	PUT http://localhost:9002/api/mongo/databases/mongo-rest-test/collections/mongo-test-collection  
	Content-Type: application/json  
	{"name":"mongo-test-collection","writeConcern":"FSYNC_SAFE"}  

Example Response:  
	200  
	Transfer-Encoding: chunked  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  
	"http://localhost:9002/api/mongo/databases/mongo-rest-test/collections/mongo-test-collection"  

### 10. Delete a collection (DELETE host:port/api/mongo/databases/*dbName*/collections/*collName*)
Example Request:  
	DELETE http://localhost:9002/api/mongo/databases/mongo-rest-test/collections/mongo-test-collection  

Example Response:  
	200  
	Content-Length: 0  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  

### 11. Retrieve all collections (GET host:port/api/mongo/databases/*dbName*/collections)
Example Request:  
	GET http://localhost:9002/api/mongo/databases/mongo-rest-test/collections  
	Content-Type: application/json  

Example Response:  
	200  
	Transfer-Encoding: chunked  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  
	[{"name":"mongo-test-collection-1",  
	"writeConcern":"SAFE",  
	"indexes":[{"name":null,"dbName":"mongo-rest-test","keys":["_id"],"unique":false,"collectionName":"mongo-test-collection-1"}],  
	"documents":[],  
	"dbName":"mongo-rest-test"},  
	{"name":"mongo-test-collection-2",  
	"writeConcern":"SAFE",  
	"indexes":[{"name":null,"dbName":"mongo-rest-test","keys":["_id"],"unique":false,"collectionName":"mongo-test-collection-2"}],  
	"documents":[],  
	"dbName":"mongo-rest-test"}]  

### 12. Delete all collections (DELETE host:port/api/mongo/databases/*dbName*/collections)
Example Request:  
	DELETE http://localhost:9002/api/mongo/databases/mongo-rest-test/collections

Example Response:  
	200  
	Transfer-Encoding: chunked  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  
	Deleted collections: [mongo-test-collection-1, mongo-test-collection-2]  

### 13. Create an index (POST host:port/api/mongo/databases/*dbName*/collections/*collName*/indexes)
Example Request:  
	POST http://localhost:9002/api/mongo/databases/mongo-rest-test/collections/mongo-test-collection/indexes  
	Content-Type: application/json  
	{"name":"simple-index","keys":["name","age"],"unique":true}  

Example Response:  
	201  
	Content-Length: 0  
	Location: http://localhost:9002/api/mongo/databases/mongo-rest-test/collections/mongo-test-collection/indexes/simple-index  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  

### 14. Retrieve an index (GET host:port/api/mongo/databases/*dbName*/collections/*collName*/indexes/*index*)
Example Request:  
	GET http://localhost:9002/api/mongo/databases/mongo-rest-test/collections/mongo-test-collection/indexes/stats-index  
	Content-Type: application/json  

Example Response:  
	200  
	Transfer-Encoding: chunked  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  
	{"name":"stats-index","dbName":"mongo-rest-test","keys":["name","age","height"],"unique":true,"collectionName":"mongo-test-collection"}  

### 15. Delete an index (DELETE host:port/api/mongo/databases/*dbName*/collections/*collName*/indexes/*index*)
Example Request:  
	DELETE http://localhost:9002/api/mongo/databases/mongo-rest-test/collections/mongo-test-collection/indexes/stats-index  

Example Response:  
	200  
	Content-Length: 0  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  

### 16. Retrieve all indexes (GET host:port/api/mongo/databases/*dbName*/collections/*collName*/indexes)
Example Request:  
	GET http://localhost:9002/api/mongo/databases/mongo-rest-test/collections/mongo-test-collection/indexes  
	Content-Type: application/json  

Example Response:  
	200  
	Transfer-Encoding: chunked  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  
	[{"name":"stats-index",  
	"dbName":"mongo-rest-test",  
	"keys":["name","age","height"],  
	"unique":true,  
	"collectionName":"mongo-test-collection"},  
	{"name":"location-index",  
	"dbName":"mongo-rest-test",  
	"keys":["address","phone"],  
	"unique":true,  
	"collectionName":"mongo-test-collection"}]  

### 17. Delete all indexes (DELETE host:port/api/mongo/databases/*dbName*/collections/*collName*/indexes)
Example Request:  
	DELETE http://localhost:9002/api/mongo/databases/mongo-rest-test/collections/mongo-test-collection/indexes  

Example Response:  
	200  
	Content-Length: 0  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  
	Deleted indexes: [stats-index]  

### 18. Create a document (POST host:port/api/mongo/databases/*dbName*/collections/*collName*/documents)
Example Request:  
	POST http://localhost:9002/api/mongo/databases/mongo-rest-test/collections/mongo-test-collection/documents  
	Content-Type: application/json  
	{"json":"{ \"state\" : \"california\" , \"tempMap\" : { \"december\" : \"45\" , \"january\" : \"50\"} , \"city\" : \"san francisco\"}"}  

Example Response:  
	201  
	Content-Length: 0
	Location: http://localhost:9002/api/mongo/databases/mongo-rest-test/collections/mongo-test-collection/documents/4f056f2b30041f7b09baac05  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  

### 19. Retrieve a document (GET host:port/api/mongo/databases/*dbName*/collections/*collName*/documents/*docId*)
Example Request:  
	GET http://localhost:9002/api/mongo/databases/mongo-rest-test/collections/mongo-test-collection/documents/4f056f843004d8fcde78a893  
	Content-Type: application/json  

Example Response:  
	200  
	Transfer-Encoding: chunked  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  
	{"json":"{ \"_id\" : { \"$oid\" : \"4f056f843004d8fcde78a893\"} , \"state\" : \"california\" , \"tempMap\" : { \"december\" : \"45\" , \"january\" : \"50\"} , \"city\" : \"san francisco\"}","locationUri":null}

### 20. Update a document (PUT host:port/api/mongo/databases/*dbName*/collections/*collName*/documents/*docId*)
Example Request:  
	PUT http://localhost:9002/api/mongo/databases/mongo-rest-test/collections/mongo-test-collection/documents/4f056fe4300467ed3870bd69  
	Content-Type: application/json  
	{"json":"{ \"_id\" : { \"$oid\" : \"4f056fe4300467ed3870bd69\"} , \"state\" : \"california\" , \"tempMap\" : { \"december\" : \"45\" , \"january\" : \"50\"} , \"city\" : \"fresno\"}"}  

Example Response:  
	200  
	Transfer-Encoding: chunked  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  
	"http://localhost:9002/api/mongo/databases/mongo-rest-test/collections/mongo-test-collection/documents/4f056fe4300467ed3870bd69"  

### 21. Delete a document (DELETE host:port/api/mongo/databases/*dbName*/collections/*collName*/documents/*docId*)
Example Request:  
	DELETE http://localhost:9002/api/mongo/databases/mongo-rest-test/collections/mongo-test-collection/documents/4f0570703004fcc3f0d6f072  

Example Response:  
	200  
	Content-Length: 0  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  

### 22. Retrieve all documents (GET host:port/api/mongo/databases/*dbName*/collections/*collName*/documents)
Example Request:  
	GET http://localhost:9002/api/mongo/databases/mongo-rest-test/collections/mongo-test-collection/documents  
	Content-Type: application/json  

Example Response:  
	200  
	Transfer-Encoding: chunked  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  
	[{"json":"{ \"_id\" : { \"$oid\" : \"4f0570ac3004ac6382ea93a3\"} , \"state\" : \"california\" , \"tempMap\" : { \"december\" : \"45\" , \"january\" : \"50\"} , \"city\" : \"san francisco\"}","locationUri":null}]

### 23. Delete all documents (DELETE host:port/api/mongo/databases/*dbName*/collections/*collName*/documents)
Example Request:  
	DELETE http://localhost:9002/api/mongo/databases/mongo-rest-test/collections/mongo-test-collection/documents  

Example Response:  
	200  
	Content-Length: 0  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  

### 24. Ping Service (GET host:port/api/mongo/ping)
Example Request:  
	GET http://localhost:9002/api/mongo/ping  
	Content-Type: application/json  

Example Response:  
	200  
	Content-Length: 0  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  
	Service is alive and well  

### 25. Shutdown Service (GET host:port/api/mongo/shutdown)
Example Request:  
	GET http://localhost:9002/api/mongo/shutdown  
	Content-Type: application/json  

Example Response:  
	200  
	Content-Length: 0  
	Content-Type: application/json  
	Server: Jetty(6.1.26)  


## Deployment
The service is deployable as a WAR on Jetty or Tomcat. If you have to, feel free to drop the WAR on a beefier app-server like JBoss/Weblogic/Websphere. The service does expect a MongoDB installation. Download and install your platform [MongoDB Release](http://www.mongodb.org/downloads). To get started, clone the project and 'mvn package'.  


## Testing
The JUnit tests run against the service deployed on Jetty.  


## Java Client
There is a simple Java Client/Proxy library jar that can be created using 'mvn package -Pclient-jar'. Look for the client jar in the target folder.   


## Scaling
I would recommend scaling out the backing MongoDB first instead of trying to scale out the conduit. To that effect, consider a configuration of Sharded ReplicaSets. You will also need to tweak MongoOptions. If there's still a need to throttle the data service, use separate request/response message pipes.  


## Contributions
Fork, spoon, knive the project as you see fit (: Pull requests with bug fixes are very welcome. If you encounter an issue and do not have the time to submit a patch, please log a Github issue against the project.  


## License
MIT License - Copyright (c) 2012 Gaurav Sharma  
