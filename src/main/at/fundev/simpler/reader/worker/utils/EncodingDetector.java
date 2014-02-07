package at.fundev.simpler.reader.worker.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import at.fundev.simpler.reader.worker.exceptions.SimplerReaderException;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

public class EncodingDetector {
	public static final String DEFAULT_ENCODING = "UTF-8";
	private InputStream input;
	private String encoding;
	
	public EncodingDetector(InputStream input) {
		this.input = input;
	}
	
	public void detectEncoding() throws SimplerReaderException {
		CharsetDetector detector = new CharsetDetector();
		BufferedInputStream stream = new BufferedInputStream(input);
		
		try {
			detector.setText(stream);
			CharsetMatch match = detector.detect();
			
			encoding = (match != null) ? match.getName() : DEFAULT_ENCODING;
		} catch (IOException e) {
			throw new SimplerReaderException(e);
		}
	}

	public String getEncoding() {
		return encoding;
	}
}
