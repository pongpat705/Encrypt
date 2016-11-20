package app.service.impl;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStore.SecretKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableEntryException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import app.constanst.ApplicationConstanst.KEYSTORE_ALIAS;
import app.service.DeviceService;
import app.service.KeyLoader;

@Service
public class DeviceServiceImpl implements DeviceService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired private KeyLoader keyLoader;
	
	public String encryptPasscode(String passcode) throws Exception {
		this.logger.info("encryptPasscode");
		KeyStore keystore = keyLoader.load();
		
		SecretKey secretKeyRSA = null;
		SecretKey secretKeyBcrypt = null;
		String cipherText = null;
		this.logger.info("encryptPasscode get keys from keystore");
		try {
			KeyStore.SecretKeyEntry secretKeyEntryRSA = (SecretKeyEntry) keystore.getEntry(KEYSTORE_ALIAS.PUBLIC_KEY_ALIAS.getAlias(), KEYSTORE_ALIAS.PUBLIC_KEY_ALIAS.getProtection());
			KeyStore.SecretKeyEntry secretKeyEntryBcrypt = (SecretKeyEntry) keystore.getEntry(KEYSTORE_ALIAS.BCRYPT_SALT_ALIAS.getAlias(), KEYSTORE_ALIAS.BCRYPT_SALT_ALIAS.getProtection());
			secretKeyRSA = secretKeyEntryRSA.getSecretKey();
			secretKeyBcrypt = secretKeyEntryBcrypt.getSecretKey();
			
		} catch (NoSuchAlgorithmException | UnrecoverableEntryException | KeyStoreException e) {
			throw new Exception(e);
		}
		
		String secretKeyRSABase64 = Base64Utils.encodeToString(secretKeyRSA.getEncoded());
		byte[] secretKeyByteRSAArr = Base64Utils.decodeFromString(secretKeyRSABase64);
		
		this.logger.info("encryptPasscode continue encrypt");
		try {
			PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(secretKeyByteRSAArr));
		    Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
		    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		    this.logger.info("encryptPasscode by RSA public key");
		    cipherText = Base64Utils.encodeToString(cipher.doFinal(passcode.getBytes()));
		    this.logger.info("encryptPasscode cipherText=" + cipherText);
		    
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidKeySpecException e) {
			throw new Exception(e);
		}
		
		this.logger.info("encryptPasscode continue hashing");
		String salt = new String(secretKeyBcrypt.getEncoded());
		this.logger.info("encryptPasscode by Bcrypt salt");
		String bcryptPassword = BCrypt.hashpw(cipherText, salt);
		this.logger.info("encryptPasscode bcryptPassword=" + bcryptPassword);
		
		return bcryptPassword;
	}
	
	public void storeSecretKeyAES() throws Exception {
		System.out.println("storeSecretKey");
		
		KeyStore keyStore = keyLoader.load();
		
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		generator.init(256, random);
		
		SecretKey secretKey = generator.generateKey();
		
		byte[] secretKeyByteArr = secretKey.getEncoded();
		System.out.println("secretKey=" + Base64Utils.encodeToString(secretKeyByteArr));
		
		KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(secretKey);
		keyStore.setEntry(KEYSTORE_ALIAS.SECRET_KEY_ALIAS.getAlias(), secretKeyEntry, KEYSTORE_ALIAS.SECRET_KEY_ALIAS.getProtection());
		
		keyLoader.store(keyStore);
		System.out.println("store secretKey success");
		
		System.out.println("continue get secretKey");
		secretKeyEntry = (SecretKeyEntry) keyStore.getEntry(KEYSTORE_ALIAS.SECRET_KEY_ALIAS.getAlias(), KEYSTORE_ALIAS.SECRET_KEY_ALIAS.getProtection());
		secretKey = secretKeyEntry.getSecretKey();
		
		System.out.println("encrypt token");
		Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	    String cipherText = Base64Utils.encodeToString(cipher.doFinal("token".getBytes()));
	    System.out.println("cipherText=" + cipherText);
	}
	
	public void storePublicKeyRSA() throws Exception {
		System.out.println("storePublicKey");
		
		KeyStore keyStore = keyLoader.load();
		
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		generator.initialize(2048, random);
		
		KeyPair keypair = generator.generateKeyPair();
		PublicKey publicKey = keypair.getPublic();
		
		byte[] publicKeyByteArr = publicKey.getEncoded();
		System.out.println("publicKey=" + Base64Utils.encodeToString(publicKeyByteArr));
		
		SecretKey secretKey = new SecretKeySpec(publicKeyByteArr, 0, publicKeyByteArr.length, "AES");
		
		KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(secretKey);
		keyStore.setEntry(KEYSTORE_ALIAS.PUBLIC_KEY_ALIAS.getAlias(), secretKeyEntry, KEYSTORE_ALIAS.PUBLIC_KEY_ALIAS.getProtection());
		
		keyLoader.store(keyStore);
		System.out.println("store publicKey success");
		
		System.out.println("continue get publicKey");
		secretKeyEntry = (SecretKeyEntry) keyStore.getEntry(KEYSTORE_ALIAS.PUBLIC_KEY_ALIAS.getAlias(), KEYSTORE_ALIAS.PUBLIC_KEY_ALIAS.getProtection());
		secretKey = secretKeyEntry.getSecretKey();
		
		String secretKeyBase64 = Base64Utils.encodeToString(secretKey.getEncoded());
		byte[] secretKeyByteArr = Base64Utils.decodeFromString(secretKeyBase64);
		
		publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(secretKeyByteArr));
		System.out.println("encrypt passcode");
		Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
	    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	    String cipherText = Base64Utils.encodeToString(cipher.doFinal("passcode".getBytes()));
	    System.out.println("cipherText=" + cipherText);
	}
	
	public void storeBcryptSalt() throws Exception  {
		System.out.println("storeBcryptSalt");
		
		KeyStore keyStore = keyLoader.load();
		
		String salt = BCrypt.gensalt(13);
		System.out.println("salt=" + salt);
		
		String saltBase64 = Base64Utils.encodeToString(salt.getBytes());
		System.out.println("saltBase64=" + saltBase64);
		
		byte[] saltByteArr = Base64Utils.decodeFromString(saltBase64);
		
		SecretKey secretKey = new SecretKeySpec(saltByteArr, 0, saltByteArr.length, "AES");

		KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(secretKey);
		keyStore.setEntry(KEYSTORE_ALIAS.BCRYPT_SALT_ALIAS.getAlias(), secretKeyEntry, KEYSTORE_ALIAS.BCRYPT_SALT_ALIAS.getProtection());
		
		keyLoader.store(keyStore);
		System.out.println("store bcrypt salt success");
		
		System.out.println("continue get bcrypt salt");
		secretKeyEntry = (SecretKeyEntry) keyStore.getEntry(KEYSTORE_ALIAS.BCRYPT_SALT_ALIAS.getAlias(), KEYSTORE_ALIAS.BCRYPT_SALT_ALIAS.getProtection());
		secretKey = secretKeyEntry.getSecretKey();
		
		salt  = new String(secretKey.getEncoded());
		System.out.println("hashing passcode");
		String hashPasscode = BCrypt.hashpw("passcode", salt);
		System.out.println("hashPasscode=" + hashPasscode);
	}

}
