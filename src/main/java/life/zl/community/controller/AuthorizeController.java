package life.zl.community.controller;

import life.zl.community.dto.AccessTokenDto;
import life.zl.community.dto.GitHubUser;
import life.zl.community.mapper.UserMapper;
import life.zl.community.model.User;
import life.zl.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Autowired
    private UserMapper userMapper;
    @Value("${GitHub.client.id}")
    private String clientID;
    @Value("${GitHub.client.secret}")
    private String clientSecret;
    @Value("${GitHub.redirect.uri}")
    private String redirectURI;
    @GetMapping("/callback")
    public String Authorize(@RequestParam(name = "code")String code,
                            @RequestParam(name = "state")String state,
                            HttpServletRequest request){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(clientID);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirectURI);
        accessTokenDto.setState(state);

        String accessToken = gitHubProvider.getAccessToken(accessTokenDto);
        GitHubUser githubuser = gitHubProvider.getUser(accessToken);
        System.out.println(githubuser);

        if(githubuser!=null){
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setAccount_id(String.valueOf(githubuser.getId()));
            user.setName(githubuser.getName());
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userMapper.insert(user);
            request.getSession().setAttribute("user",githubuser);
            return "redirect:/";
        }else {
            return "redirect:/";
        }


    }
}
