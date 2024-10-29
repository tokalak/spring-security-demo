package com.tklk;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class SecureResourceController {

  @GetMapping("/")
  public String index() {
    return "public";
  }

  @GetMapping("/secured")
  public String securedResource(Model model, Authentication auth) {
    model.addAttribute("name", auth.getName());

    return "secured";
  }
}
