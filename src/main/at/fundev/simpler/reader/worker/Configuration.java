package at.fundev.simpler.reader.worker;

import at.fundev.simpler.reader.worker.exceptions.SimplerReaderException;

public interface Configuration {
	public void setInt(String key, int value);
	
	public void setDouble(String key, double value);
	
	public void setString(String key, String value);
	
	public int getInt(String key);
	
	public double getDouble(String key);
	
	public String getString(String key);
	
	public boolean hasKey(String key);
	
	public void load() throws SimplerReaderException;
	
	public void save() throws SimplerReaderException;
}
