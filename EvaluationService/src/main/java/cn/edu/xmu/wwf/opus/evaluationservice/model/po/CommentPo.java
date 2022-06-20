package cn.edu.xmu.wwf.opus.evaluationservice.model.po;

import lombok.Data;

@Data
public class CommentPo {
    int id;
    String content;
    int artworkId;
    int userId;
}
