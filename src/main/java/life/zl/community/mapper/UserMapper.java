package life.zl.community.mapper;

import life.zl.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,avatar_url) values (#{account_id},#{name},#{token},#{gmt_create},#{gmt_modified},#{avatar_url})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(Integer id);

    @Select("select * from user where account_id = #{account_id}")
    User findByAccountId(@Param("account_id")String account_id);

    @Update("update user set name=#{name},token=#{token},avatar_url=#{avatar_url},gmt_modified=#{gmt_modified} where id = #{id}")
    void update(User user);
}
