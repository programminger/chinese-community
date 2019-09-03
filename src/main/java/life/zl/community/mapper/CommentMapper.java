package life.zl.community.mapper;

import life.zl.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment (parent_id,content,type,gmt_create,gmt_modified,commentator) values(#{parent_id},#{content},#{type},#{gmt_create},#{gmt_modified},#{commentator})")
    void insert(Comment comment);

    @Select("select * from comment where parent_id = #{parent_id}")
    Comment findByType(@Param("parent_id") Integer parent_id);
}
