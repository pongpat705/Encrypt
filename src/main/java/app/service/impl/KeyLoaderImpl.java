package app.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import app.constanst.ApplicationConstanst;
import app.service.KeyLoader;
import app.utils.FileLoader;

@Service
public class KeyLoaderImpl implements KeyLoader {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("localLoader")
	private FileLoader fileLoader;
	
	private KeyStore keyStore;
	
	@PostConstruct
	public void reloadKeystore() throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, IOException {
		Security.addProvider(new BouncyCastleProvider());
		
		InputStream is = fileLoader.getFileInputStream();
		KeyStore keyStore = KeyStore.getInstance("BKS","BC");
		keyStore.load(is, ApplicationConstanst.KEYSTORE_FILE_PASSWORD.toCharArray());
		
		logger.info("Finsihed loading key store");
		
		this.keyStore = keyStore;
		
	}

	public KeyStore load() {
		return this.keyStore;
	}
	
	public void store(KeyStore keyStore) throws Exception {
		OutputStream os = null;

		try {
			os = new FileOutputStream(fileLoader.getFile());
			keyStore.store(os, ApplicationConstanst.KEYSTORE_FILE_PASSWORD.toCharArray());
			
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		} finally {
			IOUtils.closeQuietly(os);
		}
		
		this.keyStore = keyStore;
	}

}
