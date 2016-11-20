package app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.service.DeviceService;

@RestController
@RequestMapping("/service")
public class DeviceController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired private DeviceService deviceService;
	
    @GetMapping("/encrypt/{passcode}")
    public String encrypt(@PathVariable String passcode) throws Exception {
    	this.logger.info("Passcode being encrypt : "+passcode);
    	String passcodeEncrypt = deviceService.encryptPasscode(passcode);
        return passcodeEncrypt;
    }
    
    @GetMapping("/genSubKey")
    public void genSubKey() throws Exception {
    	this.logger.info("Generating sub key");
    	deviceService.storeSecretKeyAES();
    	deviceService.storePublicKeyRSA();
    	deviceService.storeBcryptSalt();
    }
    
}
