package app.utils.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import app.constanst.ApplicationConstanst;
import app.utils.FileLoader;

@Service("localLoader")
public class LocalLoader implements FileLoader {
	
	@Autowired
	private ResourceLoader resourceLoader;

	public InputStream getFileInputStream() throws IOException {
		InputStream is = new FileInputStream(this.resourceLoader.getResource(ApplicationConstanst.PathConstanst.PATH).getFile());
		return is;
	}

	@Override
	public File getFile() throws IOException {
		return this.resourceLoader.getResource(ApplicationConstanst.PathConstanst.PATH).getFile();
	}

}
