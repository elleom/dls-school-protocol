package dk.kea.stud.dls.schoolprotocol.controller;

import dk.kea.stud.dls.schoolprotocol.model.Subject;
import dk.kea.stud.dls.schoolprotocol.model.Teacher;
import dk.kea.stud.dls.schoolprotocol.repository.SubjectRepository;
import dk.kea.stud.dls.schoolprotocol.repository.TeacherRepository;
import dk.kea.stud.dls.schoolprotocol.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    RequestService requestService;
    @Autowired
    TeacherRepository teacherRepository;



    @RequestMapping({"", "index", "index.html", "/"})
    public String getIndex(HttpServletRequest request, Model model){ //add model to load repos

        String userName = request.getRemoteUser();
        Teacher teacher = teacherRepository.findById(1L).get();


        String clientIp = requestService.getClientIp(request);
        String clientMAC = requestService.getClientMac(clientIp);
        model.addAttribute("clientIp", clientIp);
        model.addAttribute("clientMAC", clientMAC);
        model.addAttribute("user", teacher);

        return "index";
    }




}
