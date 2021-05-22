package dk.kea.stud.dls.schoolprotocol.controller;

import dk.kea.stud.dls.schoolprotocol.model.Student;
import dk.kea.stud.dls.schoolprotocol.model.Subject;
import dk.kea.stud.dls.schoolprotocol.model.Teacher;
import dk.kea.stud.dls.schoolprotocol.repository.StudentRepository;
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
    @Autowired
    StudentRepository studentRepository;



    @RequestMapping({"", "index", "index.html", "/"})
    public String getIndex(HttpServletRequest request, Model model){ //add model to load repos

        String userName = request.getRemoteUser();
        Teacher teacher = teacherRepository.findByUserName(userName);
        Student student = studentRepository.findByUserName(userName);

        //String clientIp = requestService.getClientIp(request);
        //String clientMAC = requestService.getClientMac(clientIp);
        //model.addAttribute("clientIp", clientIp);
        //model.addAttribute("clientMAC", clientMAC);
        //push
        /*
        checks if logged in user is stud or teach
        add to model as user.
         */
        if (teacher != null) {
            model.addAttribute("user", teacher);
        } else {
            model.addAttribute("user", student);
        }

        return "index";
    }






}
