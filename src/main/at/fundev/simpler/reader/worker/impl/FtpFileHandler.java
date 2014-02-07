package at.fundev.simpler.reader.worker.impl;

import java.io.File;	
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import com.google.inject.Inject;

import at.fundev.simpler.reader.worker.Configuration;
import at.fundev.simpler.reader.worker.FileHandler;
import at.fundev.simpler.reader.worker.exceptions.SimplerReaderException;

public class FtpFileHandler implements FileHandler {
	private static final String DEFAULT_REMOTE_DIR = "sr";
	private static final String FTP_UPLOAD_DIR = "ftp.upload.dir";
	private static final int DEFAULT_FTP_PORT = 21;
	private static final String FTP_HOST = "ftp.host";
	private static final String FTP_USER = "ftp.user";
	private static final String FTP_PASSWORD = "ftp.password";
	private static final String FTP_PORT = "ftp.port";
	private Configuration config;
	
	@Inject
	public FtpFileHandler(Configuration config) {
		this.config = config;
	}
	
	@Override
	public String handle(File file) throws SimplerReaderException {
		if(isConfigValid()) {
			uploadFile(file);
			return getRelativePathOfUploadedFile(file);
		} else {
			throw new SimplerReaderException(new IllegalStateException(String.format("%s must not be used without valid configuration! Needs %s, %s, %s in configuration!", this.getClass().getSimpleName(), FTP_HOST, FTP_PASSWORD, FTP_USER)));
		}
	}
	
	private boolean isConfigValid() {
		boolean isInvalid = (config == null) || config.getString(FTP_HOST) == null || config.getString(FTP_USER) == null || config.getString(FTP_PASSWORD) == null;
		return !isInvalid;
	}
	
	private void uploadFile(File file) throws SimplerReaderException {
		FTPClient client = new FTPClient();
		client.configure(getFtpConfiguration());
		
		try {
			client.connect(config.getString(FTP_HOST), getPort());
			
			int replyCode = client.getReplyCode();
			
			if(!FTPReply.isPositiveCompletion(replyCode)) {
				client.disconnect();	
				throw new SimplerReaderException(String.format("FTP connection didn't succeeded. Got error code %d", replyCode));
			}
			
			client.login(config.getString(FTP_USER), config.getString(FTP_PASSWORD));
			client.setFileType(FTP.ASCII_FILE_TYPE);
			client.enterLocalPassiveMode();
			
			prepareDirectory(client);
			client.storeFile(file.getName(), new FileInputStream(file));
			client.disconnect();
		} catch (SocketException e) {
			throw new SimplerReaderException(e);
		} catch (IOException e) {
			throw new SimplerReaderException(e);
		}
	}

	private FTPClientConfig getFtpConfiguration() {
		FTPClientConfig ftpConf = new FTPClientConfig();
		return ftpConf;
	}
	
	private int getPort() {
		return config.hasKey(FTP_PORT) ? config.getInt(FTP_PORT) : DEFAULT_FTP_PORT;
 	}
	
	private void prepareDirectory(FTPClient client) throws SimplerReaderException {
		try {
			client.makeDirectory(getRemoteDir());
		} catch (IOException e) {
			throw new SimplerReaderException(e);
		}
	}
	
	private String getRemoteDir() {
		return config.hasKey(FTP_UPLOAD_DIR) ? config.getString(FTP_UPLOAD_DIR) : DEFAULT_REMOTE_DIR;
	}
	
	private String getRelativePathOfUploadedFile(File file) {
		String path = getRemoteDir();
		boolean hasSeparator = path.endsWith(File.separator) || path.endsWith("/") || path.endsWith("\\");
		
		return path + (hasSeparator ? "/" : "") + file.getName();
	}
}
