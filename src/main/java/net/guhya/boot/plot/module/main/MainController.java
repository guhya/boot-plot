package net.guhya.boot.plot.module.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	private static final Logger log = LoggerFactory.getLogger(MainController.class);
		
	@RequestMapping("/")
    public String home(Model model) {
		log.info("Home");
		return "user/main/datatables";
    }
		
	
} 
