package com.test.mongoconnection.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.test.mongoconnection.dataaccess.MongoConnection;

public class MongoConnectionServlet implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		MongoConnection conn = MongoConnection.getInstance();
		conn.init();
	}

	public void contextDestroyed(ServletContextEvent sce) {
		MongoConnection conn = MongoConnection.getInstance();
		conn.close();
	}

}
