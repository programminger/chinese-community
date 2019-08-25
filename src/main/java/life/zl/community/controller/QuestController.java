package life.zl.community.controller;

import life.zl.community.dto.QuestDTO;
import life.zl.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("question/{id}")
    public String question(@PathVariable(name = "id")Integer id, Model model){

        QuestDTO questDTO = questionService.getByID(id);
        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question",questDTO);

     return "question";
    }



}
