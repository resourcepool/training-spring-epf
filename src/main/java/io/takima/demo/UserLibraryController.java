package io.takima.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/")
@Controller
public class UserLibraryController {

    private final UserDAO userDAO;

    @Autowired
    public UserLibraryController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @RequestMapping("/")
    @GetMapping
    public String getDashboard(Model m) {
        m.addAttribute("users", userDAO.findAll());
        return "index";
    }

    @GetMapping("/new")
    public String getNewUserPage(Model m) {
        m.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/new")
    public ModelAndView createUser(@ModelAttribute User u) {
        List<String> errors = checkUser(u);
        if (errors.isEmpty()) {
            userDAO.save(u);
            ModelAndView mav = new ModelAndView("redirect:/");
            return mav;
        } else {
            ModelAndView mav = new ModelAndView("/new");
            mav.addObject("errors", errors);
            return mav;
        }
    }

    private List<String> checkUser(User u) {
        List<String> errors = new ArrayList<>();
        if (u.getFirstName() == null || u.getFirstName().length() < 2) {
            errors.add("firstName.invalid");
        }
        if (u.getLastName() == null || u.getLastName().length() < 2) {
            errors.add("lastName.invalid");
        }
        if (u.getAge() == null || u.getAge() < 18) {
            errors.add("age.invalid");
        }
        return errors;
    }
}
