package app.service;

public interface DeviceService {

	public String encryptPasscode(String passcode) throws Exception;
	
	public void storeSecretKeyAES() throws Exception;
	
	public void storePublicKeyRSA() throws Exception;
	
	public void storeBcryptSalt() throws Exception;
}
