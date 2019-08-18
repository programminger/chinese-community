package life.zl.community.controller;

import life.zl.community.mapper.UserMapper;
import life.zl.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/index")
    public String login(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if("token".equals(cookie.getName())){
                User user = userMapper.findToken(cookie.getValue());
                if(user!=null){
                    request.getSession().setAttribute("user",user);
                    break;
                }

            }

        }

        return "index";
    }
}
