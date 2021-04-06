package dk.kea.stud.dls.schoolprotocol.controller;

import dk.kea.stud.dls.schoolprotocol.service.RequestService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Main {

    @Autowired
    private RequestService requestService;

    @RequestMapping({"", "index", "index.html", "/"})
    public String getIndex(HttpServletRequest request, Model model){ //add model to load repos

        String clientIp = requestService.getClientIp(request);
        model.addAttribute("clientIp", clientIp);

        return "index";
    }
}
