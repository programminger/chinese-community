package life.zl.community.controller;

import life.zl.community.dto.QuestDTO;
import life.zl.community.model.Question;
import life.zl.community.model.User;
import life.zl.community.service.ServiceQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {


    @Autowired
    private ServiceQuestion serviceQuestion;

    @GetMapping("/publish")
    public String publish(){

        return "publish";
    }
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Integer id,
                       Model model){

        QuestDTO question = serviceQuestion.getByID(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",id);

        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(name = "title",required = false) String title,
            @RequestParam(name = "description",required = false) String description,
            @RequestParam(name = "tag",required = false) String tag,
            @RequestParam(name = "id",required = false ) Integer id,
            HttpServletRequest request, Model model
            ){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title==null||title==""){
            model.addAttribute("errorMesg","标题不能为空");
            return "publish";
        }
        if(description==null||description==""){
            model.addAttribute("errorMesg","内容不能为空");
            return "publish";
        }
        if(tag==null||tag==""){
            model.addAttribute("errorMesg","标签不能为空");
            return "publish";
        }



        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("errorMesg","用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);


        serviceQuestion.createOrUpdate(question);

        return "redirect:/";


    }
}
