package dk.kea.stud.dls.schoolprotocol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Main {

    @RequestMapping({"", "index", "index.html", "/"})
    public String getIndex(){ //add model to load repos
        return "index";
    }
}