package life.zl.community.service;

import life.zl.community.mapper.UserMapper;
import life.zl.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void creatOrUpdate(User user) {

        User userdb = userMapper.findByAccountId(user.getAccount_id());
        if(userdb!=null){
            userdb.setAvatar_url(user.getAvatar_url());
            userdb.setName(user.getName());
            userdb.setToken(user.getToken());
            userdb.setGmt_modified(System.currentTimeMillis());
            userMapper.update(userdb);
        }else {
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userMapper.insert(user);
        }


    }
}
