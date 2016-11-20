package app.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

public interface KeyLoader {
	
	public void reloadKeystore() throws FileNotFoundException, KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, IOException;
	public KeyStore load();
	public void store(KeyStore keyStore) throws Exception;
	
}
