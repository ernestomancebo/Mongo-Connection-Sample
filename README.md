# Mongo-Connection-Sample
Sample project for propperly connect to MongoDB from a Java Web App.

To build the project run:

`~ mvn clean install`
  
To test the API which interacts with MongoDB, call the routes:

- /api/insert
  
  Is a POST method that requires a body as follow:
  
  `{"ounces": 8, "name": "small"}`

- /api/getAll
  
  Is a GET method that returns all Cups documents registred in the database.

Keep in mind that the project has **localhost** and **27017** as default host and port.