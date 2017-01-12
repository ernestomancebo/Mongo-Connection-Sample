package com.test.mongoconnection.dataaccess;

import static java.lang.String.format;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.test.mongoconnection.models.BaseMongoDO;

public class MongoConnection {

	private static Logger logger = Logger.getLogger(MongoConnection.class);
	private static MongoConnection instance = new MongoConnection();
	
	private MongoClient mongo = null;
	private Datastore dataStore = null;
	private Morphia morphia = null;

	private MongoConnection() {}
	
	public MongoClient getMongo() throws RuntimeException {
		if (mongo == null) {
			logger.debug("Starting Mongo");
			MongoClientOptions.Builder options = MongoClientOptions.builder()
													.connectionsPerHost(4)
													.maxConnectionIdleTime((60 * 1_000))
													.maxConnectionLifeTime((120 * 1_000));
													;

			MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017/test", options);
			
			logger.info("About to connect to MongoDB @ " + uri.toString());
			
			try {
				mongo = new MongoClient(uri);
				mongo.setWriteConcern(WriteConcern.ACKNOWLEDGED);
			} catch (MongoException ex) {
				logger.error("An error occoured when connecting to MongoDB", ex);
			} catch (Exception ex) {
				logger.error("An error occoured when connecting to MongoDB", ex);
			}
			
			// To be able to wait for confirmation after writing on the DB
			mongo.setWriteConcern(WriteConcern.ACKNOWLEDGED);
		}

		return mongo;
	}

	public Morphia getMorphia() {
		if (morphia == null) {
			logger.debug("Starting Morphia");
			morphia = new Morphia();

			logger.debug(format("Mapping packages for clases within %s", BaseMongoDO.class.getName()));
			morphia.mapPackageFromClass(BaseMongoDO.class);
		}

		return morphia;
	}

	public Datastore getDatastore() {
		if (dataStore == null) {
			String dbName = "testdb";
			logger.debug(format("Starting DataStore on DB: %s", dbName));
			dataStore = getMorphia().createDatastore(getMongo(), dbName);
		}

		return dataStore;
	}

	public void init() {
		logger.debug("Bootstraping");
		getMongo();
		getMorphia();
		getDatastore();
	}
	
	public void close() {
		logger.info("Closing MongoDB connection");
		if (mongo != null) {
			try {
				mongo.close();
				logger.debug("Nulling the connection dependency objects");
				mongo = null;
				morphia = null;
				dataStore = null;
			} catch (Exception e) {
				logger.error(format("An error occurred when closing the MongoDB connection\n%s", e.getMessage()));
			}
		} else {
			logger.warn("mongo object was null, wouldn't close connection");
		}
	}
	
	public static MongoConnection getInstance() {
		return instance;
	}
}
