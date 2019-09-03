package life.zl.community.dto;

import java.io.Serializable;

public class CommentDto implements Serializable {

    private Integer parent_id;
    private String content;
    private Integer type;

    @Override
    public String toString() {
        return "CommentDto{" +
                "parent_id=" + parent_id +
                ", content='" + content + '\'' +
                ", type=" + type +
                '}';
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
