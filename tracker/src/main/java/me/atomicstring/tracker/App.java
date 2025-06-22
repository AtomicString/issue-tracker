package me.atomicstring.tracker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.javalin.Javalin;

/**
 * Hello world!
 *
 */
public class App {
	
	static final Logger logger = LogManager.getLogger(App.class);
	

	public static void main(String[] args) {
		Javalin.create().get("/", ctx -> ctx.result("Hello World")).start(8000);
	}
}
