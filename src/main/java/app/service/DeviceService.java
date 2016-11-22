package app.service;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

public interface DeviceService {

	public String encryptPasscode(String passcode) throws Exception;
	
	public void createKeystoreLocalFile() throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, IOException ;
	
	public void storeSecretKeyAES() throws Exception;
	
	public void storePublicKeyRSA() throws Exception;
	
	public void storeBcryptSalt() throws Exception;
}
