package life.zl.community.service;

import life.zl.community.dto.PaginationDTO;
import life.zl.community.dto.QuestDTO;
import life.zl.community.exception.CustomizeErrorCode;
import life.zl.community.exception.CustomizeException;
import life.zl.community.mapper.QuestMapper;
import life.zl.community.mapper.UserMapper;
import life.zl.community.model.Question;
import life.zl.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestMapper questMapper;

    public PaginationDTO list(Integer page, Integer size){
        Integer offset = size * (page-1);
        List<QuestDTO> questDTOList = null;
        PaginationDTO paginationDTO = new PaginationDTO();
        if(page<1){
            page = 1;
        }

        List<Question> questions = questMapper.findAll(offset,size);
        if(questions!=null){
            questDTOList = new ArrayList<>();
            for (Question question : questions) {
                User user = userMapper.findById(question.getCreator());
                QuestDTO questDTO = new QuestDTO();
                BeanUtils.copyProperties(question,questDTO);
                questDTO.setUser(user);
                questDTOList.add(questDTO);
            }
            paginationDTO.setQuestDTOList(questDTOList);
            Integer totalCount = questMapper.count();
            paginationDTO.setPagination(page,totalCount,size);
            if(page>paginationDTO.getTotalPage()){
                page = paginationDTO.getTotalPage();
            }
        }
        return paginationDTO;
    }

    public PaginationDTO list(Integer userID, Integer page, Integer size) {

        Integer offset = size * (page-1);
        List<QuestDTO> questDTOList = null;
        PaginationDTO paginationDTO = new PaginationDTO();
        if(page<1){
            page = 1;
        }

        List<Question> questions = questMapper.listByuserID(userID,offset,size);
        if(questions!=null){
            questDTOList = new ArrayList<>();
            for (Question question : questions) {
                User user = userMapper.findById(question.getCreator());
                QuestDTO questDTO = new QuestDTO();
                BeanUtils.copyProperties(question,questDTO);
                questDTO.setUser(user);
                questDTOList.add(questDTO);
            }

            paginationDTO.setQuestDTOList(questDTOList);
            Integer totalCount = questMapper.countByuserID(userID);
            paginationDTO.setPagination(page,totalCount,size);
            if(page>paginationDTO.getTotalPage()){
                page = paginationDTO.getTotalPage();
            }
        }
        return paginationDTO;
    }

    public QuestDTO getByID(Integer id) {
        Question question = questMapper.getById(id);
        if(question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestDTO questDTO = new QuestDTO();
        BeanUtils.copyProperties(question,questDTO);
        User user = userMapper.findById(question.getCreator());
        questDTO.setUser(user);
        return questDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            question.setGmt_create(System.currentTimeMillis());
            question.setGmt_modified(question.getGmt_create());
            question.setComment_count(0);
            question.setView_count(0);
            question.setLike_count(0);
            questMapper.create(question);
        }else {
            question.setGmt_modified(System.currentTimeMillis());
            int update = questMapper.update(question);
            if(update!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Integer id) {
        //乐观锁实现阅读数自增1
        while (true) {
            try {
                Question question1 = questMapper.getById(id);
                if(question1==null){
                    break;
                }
                Question question = new Question();
                question.setId(id);
                question.setView_count(question1.getView_count()+1);
                int updateCount = questMapper.updateViewCount(question);
                if(updateCount > 0) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void incCount(Integer id) {
        //乐观锁实现回复自增1
        while (true) {
            try {
                Question question1 = questMapper.getById(id);
                if(question1==null){
                    break;
                }
                Question question = new Question();
                question.setId(id);
                question.setComment_count(question1.getComment_count()+1);
                int updateCount = questMapper.updateCommentCount(question);
                if(updateCount > 0) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
