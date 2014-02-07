package at.fundev.simpler.reader.worker;
import java.io.File;

import at.fundev.simpler.reader.worker.exceptions.SimplerReaderException;

public interface FileHandler {
	public String handle(File file) throws SimplerReaderException;
}
