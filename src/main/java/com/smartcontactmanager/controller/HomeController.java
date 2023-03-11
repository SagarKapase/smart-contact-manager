package com.smartcontactmanager.controller;

import com.smartcontactmanager.dao.UserRepository;
import com.smartcontactmanager.helper.Message;
import com.smartcontactmanager.models.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepository;
    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("title","Home-Smart Contact Manager");
        return "home";
    }

    @RequestMapping("/about")
    public String about(Model model){
        model.addAttribute("title","About-Smart Contact Manager");
        return "about";
    }

    @RequestMapping("/signup")
    public String signup(Model model){
        model.addAttribute("title","Register-Smart Contact Manager");
        model.addAttribute("user",new User());
        return "signup";
    }

    //Handler for registering user
    @RequestMapping(value = "/do_register",method=RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") User user, @RequestParam(value = "agreement",defaultValue = "false") boolean agreement, Model model, HttpSession session){

            try{
                if (!agreement) {
                    System.out.println("You have not agreed the terms and conditions");
                    throw new Exception("You have not agreed the terms and conditions");
                }
                user.setRole("Role_User");
                user.setEnabled(true);
                user.setImageUrl("default.png");
                System.out.println("Agreeement :"+agreement);
                System.out.print("User : "+user);

                User result=this.userRepository.save(user);

                //when user successfully registered then the following message will display

                model.addAttribute("user",new User());

                session.setAttribute("message",new Message("Successfully Registered...","alert-success"));
                return "signup";
            }catch (Exception e)
            {
                e.printStackTrace();
                model.addAttribute("user",user);
                session.setAttribute("message",new Message("Something went wrong !! ","alert-danger"));
                return "signup";
            }


    }
}
