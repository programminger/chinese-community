package life.zl.community.mapper;

import life.zl.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,tag,creator)values(#{title},#{description},#{gmt_create},#{gmt_modified},#{tag},#{creator})")
    void create(Question question);

    @Select("select * from QUESTION limit #{offset},#{size}")
    List<Question> findAll(@Param(value = "offset")Integer offset, @Param(value = "size")Integer size);

    @Select("select count(1) from question")
    Integer count();
    @Select("select * from QUESTION where creator = #{userID} limit #{offset},#{size}")
    List<Question> listByuserID(@Param(value = "userID") Integer userID, @Param(value = "offset")Integer offset, @Param(value = "size")Integer size);

    @Select("select count(1) from question where creator = #{userID}")
    Integer countByuserID(@Param(value = "userID") Integer userID);

    @Select("select * from question where id = #{id}")
    Question getById(@Param(value = "id")Integer id);

    @Update("update question set title = #{title},description = #{description},gmt_modified = #{gmt_modified},tag = #{tag} where id = #{id}")
    int update(Question question);

    @Update("update question set view_count = #{view_count} where id = #{id}")
    int updateViewCount(Question question);
}
