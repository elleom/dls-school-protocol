package dk.kea.stud.dls.schoolprotocol.controller;

import dk.kea.stud.dls.schoolprotocol.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Main {

    private RequestService requestService;

    public Main(RequestService requestService) {
        this.requestService = requestService;
    }

    @RequestMapping({"", "index", "index.html", "/"})
    public String getIndex(HttpServletRequest request, Model model){ //add model to load repos

        String clientIp = requestService.getClientIp(request);
        String clientMAC = requestService.getClientMac(clientIp);
        model.addAttribute("clientIp", clientIp);
        model.addAttribute("clientMAC", clientMAC);

        return "index";
    }
}
