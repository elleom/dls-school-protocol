package dk.kea.stud.dls.schoolprotocol.service;

import javax.servlet.http.HttpServletRequest;

public interface RequestService {

    String getClientIp(HttpServletRequest request);


}
