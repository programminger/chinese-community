package life.zl.community.controller;

import life.zl.community.dto.CommentDto;
import life.zl.community.dto.ResultDto;
import life.zl.community.exception.CustomizeErrorCode;
import life.zl.community.model.Comment;
import life.zl.community.model.User;
import life.zl.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    @ResponseBody
    public Object comment(@RequestBody CommentDto commentDto,
                          HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParent_id(commentDto.getParent_id());
        comment.setType(commentDto.getType());
        comment.setContent(commentDto.getContent());
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modified(comment.getGmt_create());
        comment.setCommentator(1);
        comment.setLike_count(0);
        commentService.insert(comment);
        return ResultDto.okOf();
    }

}
