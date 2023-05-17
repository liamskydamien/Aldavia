package org.hbrs.se2.project.aldavia.control;

import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.aldavia.dtos.UserDTO;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Route("user")
@Controller
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @GetMapping
    public List<User> getUsers(){
        return userRepository.findAll();
    }
}
