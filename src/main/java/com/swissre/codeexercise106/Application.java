package com.swissre.codeexercise106;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) {
	
		logger.info("-->Start");
		if (args.length != 1) {
			throw new IllegalArgumentException("The filepath to the csv is required");
		}
        CodeExercise106 ce = new CodeExercise106();
        ce.execute(args[0]);
        logger.info("<--End");
    }

}
