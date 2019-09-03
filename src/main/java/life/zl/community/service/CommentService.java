package life.zl.community.service;

import life.zl.community.Enums.CommentTypeEnum;
import life.zl.community.exception.CustomizeErrorCode;
import life.zl.community.exception.CustomizeException;
import life.zl.community.mapper.CommentMapper;
import life.zl.community.mapper.QuestMapper;
import life.zl.community.model.Comment;
import life.zl.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 评论回复
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestMapper questMapper;

    @Autowired
    private QuestionService questionService;

    @Transactional
    public void insert(Comment comment) {
        if(comment.getParent_id()==null||comment.getParent_id()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType()==null|| !CommentTypeEnum.isExit(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_ERROR);
        }
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbcomment = commentMapper.findByType(comment.getParent_id());
            if(dbcomment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else {
            //回复问题
            Question question = questMapper.getById(comment.getParent_id());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionService.incCount(question.getId());
        }

    }
}
