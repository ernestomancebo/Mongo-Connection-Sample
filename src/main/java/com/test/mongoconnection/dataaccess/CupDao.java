package com.test.mongoconnection.dataaccess;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

import com.test.mongoconnection.models.CupDO;

public class CupDao extends BasicDAO<CupDO, ObjectId> {

	public CupDao(Datastore ds) {
		super(ds);
	}

}
