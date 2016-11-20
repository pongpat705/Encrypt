package app.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface FileLoader {
	
	InputStream getFileInputStream() throws IOException;
	
	File getFile() throws IOException;
	
}
