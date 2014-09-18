package com.tsystems.javaschool.vm.web;

import com.tsystems.javaschool.vm.exception.LoginAlreadyExistException;
import com.tsystems.javaschool.vm.exception.SBBException;
import com.tsystems.javaschool.vm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user")
    public String showUsers(Map<String, Object> map) {
        map.put("userList", userService.getAllUsers());
        map.put("roleList", userService.getAllRoles());
        return "user";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public
    @ResponseBody
    String addUser(@RequestParam(value = "login") String login, @RequestParam(value = "roleId") Long roleId) {

        try {
            return userService.addUser(login, roleId);
        } catch (LoginAlreadyExistException e) {
            return "error " + e;
        } catch (SBBException e) {
            return "error " + e;
        }
    }
    
}
