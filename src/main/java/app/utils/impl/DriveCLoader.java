package app.utils.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import app.constanst.ApplicationConstanst;
import app.utils.FileLoader;

@Service("DriveCLoader")
public class DriveCLoader implements FileLoader{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public InputStream getFileInputStream() throws IOException {
		logger.info("DrivCloader");
		File file = new File(ApplicationConstanst.PathConstanst.EXPORT);
		InputStream is = new FileInputStream(file);
		return is;
	}

	public File getFile() throws IOException {
		logger.info("DrivCloader");
		File file = new File(ApplicationConstanst.PathConstanst.EXPORT);
		return file;
	}

}
