package at.fundev.simpler.reader.worker.impl;

import java.io.IOException;
import java.util.Properties;

import at.fundev.simpler.reader.worker.exceptions.SimplerReaderException;

public class DropboxConfig {
	private Properties props;
	
	public void init() throws SimplerReaderException {
		try {
			props = new Properties();
			props.load(DropboxConfig.class.getClassLoader().getResourceAsStream("simpler-reader-worker.properties"));
		} catch (IOException e) {
			throw new SimplerReaderException(e);
		}		
	}
}
