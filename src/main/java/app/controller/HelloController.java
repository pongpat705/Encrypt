package app.controller;

import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @RequestMapping("/")
    public String index() {
    	this.logger.info("Greetings from Spring Boot!");
        return "Greetings from Spring Boot!";
    }
    
    @RequestMapping(value = "/available")
    public String available() {
      return "Spring in Action";
    }

    @RequestMapping(value = "/checked-out")
    public String checkedOut() {
      return "Spring Boot in Action";
    }
    
}
