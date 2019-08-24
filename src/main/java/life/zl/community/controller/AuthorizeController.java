package life.zl.community.controller;

import life.zl.community.dto.AccessTokenDto;
import life.zl.community.dto.GitHubUser;
import life.zl.community.model.User;
import life.zl.community.provider.GitHubProvider;
import life.zl.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Autowired
    private UserService userService;

    @Value("${GitHub.client.id}")
    private String clientID;
    @Value("${GitHub.client.secret}")
    private String clientSecret;
    @Value("${GitHub.redirect.uri}")
    private String redirectURI;
    @GetMapping("/callback")
    public String Authorize(@RequestParam(name = "code")String code,
                            @RequestParam(name = "state")String state,
                            HttpServletResponse response){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(clientID);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirectURI);
        accessTokenDto.setState(state);

        String accessToken = gitHubProvider.getAccessToken(accessTokenDto);
        GitHubUser githubuser = gitHubProvider.getUser(accessToken);
        System.out.println(githubuser);

        if(githubuser!=null|githubuser.getId()!=null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccount_id(String.valueOf(githubuser.getId()));
            user.setName(githubuser.getName());

            user.setAvatar_url(githubuser.getAvatar_url());
            userService.creatOrUpdate(user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {
            return "redirect:/";
        }


    }

    @GetMapping("/logout")
    public String loginOut(HttpServletRequest request,
                           HttpServletResponse response){

        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }
}
