package life.zl.community.controller;

import life.zl.community.dto.AccessTokenDto;
import life.zl.community.dto.GitHubUser;
import life.zl.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${GitHub.client.id}")
    private String clientID;
    @Value("${GitHub.client.secret}")
    private String clientSecret;
    @Value("${GitHub.redirect.uri}")
    private String redirectURI;
    @GetMapping("/callback")
    public String Authorize(@RequestParam(name = "code")String code,
                            @RequestParam(name = "state")String state){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(clientID);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirectURI);
        accessTokenDto.setState(state);

        String accessToken = gitHubProvider.getAccessToken(accessTokenDto);

        GitHubUser user = gitHubProvider.getUser(accessToken);
        System.out.println(user.getId()+user.getName()+user.getBio());
        return "index";
    }
}
