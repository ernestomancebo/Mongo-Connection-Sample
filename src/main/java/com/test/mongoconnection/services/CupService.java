package com.test.mongoconnection.services;

import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.test.mongoconnection.dataaccess.CupDao;
import com.test.mongoconnection.dataaccess.MongoConnection;
import com.test.mongoconnection.models.CupDO;

import spark.servlet.SparkApplication;

public class CupService implements SparkApplication {

	Gson gson = new Gson();
	CupDao dao = null;
	
	@Override
	public void init() {
		MongoConnection conn = MongoConnection.getInstance();
		dao = new CupDao(conn.getDatastore());
		
		get("/api/getAll", (req, res) -> {
			res.header("content-type", "application/json");
			
			return dao.find().asList();
		}, gson::toJson);
		
		post("/api/insert", (req, res) -> {			
			CupDO cup = gson.fromJson(req.body(), CupDO.class);
			DBObject tmp = conn.getMorphia().toDBObject(cup);
			
			WriteResult wResult = dao.getCollection().insert(tmp);
			res.header("content-type", "application/json");

			return wResult.getUpsertedId();
		}, gson::toJson);
	}

}
