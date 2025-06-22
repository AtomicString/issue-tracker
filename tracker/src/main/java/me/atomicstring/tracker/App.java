package me.atomicstring.tracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.javalin.Javalin;

/**
 * Hello world!
 *
 */
public class App {
	
	static final Logger logger = LoggerFactory.getLogger(App.class);
	

	public static void main(String[] args) {
		Javalin.create().get("/", ctx -> ctx.result("Hello World")).start(8000);
	}
}
