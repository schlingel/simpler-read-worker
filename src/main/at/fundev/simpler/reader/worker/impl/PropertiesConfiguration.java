package at.fundev.simpler.reader.worker.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import at.fundev.simpler.reader.worker.Configuration;
import at.fundev.simpler.reader.worker.exceptions.SimplerReaderException;

public class PropertiesConfiguration implements Configuration {
	private static final String WORKER_PROPERTIES_NAME = "worker.properties";
	
	private Properties props;

	@Override
	public void setInt(String key, int value) {
		props.setProperty(key, Integer.toString(value));
	}

	@Override
	public void setDouble(String key, double value) {
		props.setProperty(key, Double.toString(value));
	}

	@Override
	public void setString(String key, String value) {
		props.setProperty(key, value);
	}

	@Override
	public int getInt(String key) {
		return Integer.parseInt(props.getProperty(key));
	}

	@Override
	public double getDouble(String key) {
		return Double.parseDouble(props.getProperty(key));
	}

	@Override
	public String getString(String key) {
		return props.getProperty(key);
	}

	@Override
	public void load() throws SimplerReaderException {
		if(hasConfigInClassPath()) {
			loadConfigFromClassPath();
		} else if(hasConfigInUserHome()) {
			loadConfigFromUserHome();
		} else {
			throw new IllegalStateException("No loadable configuration found!");
		}
	}
	
	private boolean hasConfigInClassPath() {
		return getClass().getClassLoader().getResourceAsStream(WORKER_PROPERTIES_NAME) != null;
	}
	
	private void loadConfigFromClassPath() throws SimplerReaderException {
		try {
			InputStream input = getClass().getClassLoader().getResourceAsStream(WORKER_PROPERTIES_NAME);
			props = new Properties();
			props.load(input);
		} catch (IOException e) {
			throw new SimplerReaderException(e);
		}
	}
	
	private boolean hasConfigInUserHome() {
		return getPropsFileInHomeDir().exists();
	}

	private void loadConfigFromUserHome() throws SimplerReaderException {
		try {
			File propsFile = getPropsFileInHomeDir();
			
			props = new Properties();
			props.load(new FileInputStream(propsFile));
		} catch (FileNotFoundException e) {
			throw new SimplerReaderException(e);
		} catch (IOException e) {
			throw new SimplerReaderException(e);
		}
	}

	private File getSimplerReaderWorkerDir() {
		String homeDir = System.getProperty("user.home");
		File configDir = new File(homeDir, ".config");
		File workerDir = new File(configDir, "simpler-reader-worker");
		
		return workerDir;
	}
	
	private File getPropsFileInHomeDir() {
		File workerDir = getSimplerReaderWorkerDir();
		File propsFile = new File(workerDir, WORKER_PROPERTIES_NAME);
		
		return propsFile;
	}
	
	@Override
	public void save() throws SimplerReaderException {
		try {
			props.store(new FileOutputStream(getPropsFileInHomeDir()), "");
		} catch (IOException e) {
			throw new SimplerReaderException(e);
		}
	}

	@Override
	public boolean hasKey(String key) {
		return props.containsKey(key);
	}

}
