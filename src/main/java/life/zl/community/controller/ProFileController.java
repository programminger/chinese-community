package life.zl.community.controller;

import life.zl.community.dto.PaginationDTO;
import life.zl.community.model.User;
import life.zl.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProFileController {


    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          Model model, HttpServletRequest request,
                          @RequestParam(name="page",defaultValue = "1")Integer page,
                          @RequestParam(name="size",defaultValue = "3")Integer size){

        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }

        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");
        }
        if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("replies","最新回复");
        }

        PaginationDTO pagination = questionService.list(user.getId(), page, size);
        model.addAttribute("pagination",pagination);
        return "profile";
    }

}
