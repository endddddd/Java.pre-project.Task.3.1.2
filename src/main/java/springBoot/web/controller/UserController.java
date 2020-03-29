package springBoot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springBoot.web.model.Role;
import springBoot.web.model.User;
import springBoot.web.service.UserService;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin/table", method = RequestMethod.GET)
    public String getTable(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("users", userService.getAllUsers());
        return "table";
    }

    @RequestMapping(value = "/admin/table", method = RequestMethod.POST)
    public String addUser(@RequestParam String firstName, String password, String lastName, String email, int age, String role) {
        userService.addUser(new User(firstName, password, lastName, email, age), role);
        return "redirect:/admin/table";
    }

    @RequestMapping(value = "/admin/remove", method = RequestMethod.POST)
    public String removeUser(@RequestParam Long id) {
        userService.removeUser(id);
        return "redirect:/admin/table";
    }

    @RequestMapping(value = "/admin/update", method = RequestMethod.POST)
    public String updateUser(@RequestParam Long id, String firstName, String password, String lastName, String email, int age, String role) {
        password = userService.ifPasswordNull(id, password);
        userService.updateUser(new User(id, firstName, lastName, age, email, password), role);
        return "redirect:/admin/table";
    }

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String loginPage() {
        userService.addAdminAndUserPanel();
        return "login";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String userPage(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        return "user";
    }

    @RequestMapping(value = "/admin/newUser", method = RequestMethod.GET)
    public String newUser(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        return "/newUser";
    }

}