package com.wt.adminvue.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class LoginPageConfig {

	@RequestMapping("/")
	public RedirectView loginPage() {
		return new RedirectView("/login.html");
	}
}
