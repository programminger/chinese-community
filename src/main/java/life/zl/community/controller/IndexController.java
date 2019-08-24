package life.zl.community.controller;

import life.zl.community.dto.PaginationDTO;
import life.zl.community.service.ServiceQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {


    @Autowired
    private ServiceQuestion serviceQuestion;

    @GetMapping("/")
    public String login(Model model,
                        @RequestParam(name="page",defaultValue = "1")Integer page,
                        @RequestParam(name="size",defaultValue = "3")Integer size){

        PaginationDTO pagination = serviceQuestion.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
