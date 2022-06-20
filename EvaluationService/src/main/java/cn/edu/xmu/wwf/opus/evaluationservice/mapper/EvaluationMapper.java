package cn.edu.xmu.wwf.opus.evaluationservice.mapper;

import cn.edu.xmu.wwf.opus.evaluationservice.model.po.CommentPo;
import cn.edu.xmu.wwf.opus.evaluationservice.model.po.StarPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EvaluationMapper {
    CommentPo selectCommentPoById(int id);
    int insertCommentPo(CommentPo commentPo);
    int deleteCommentPoById(int id);
    int insertStarPo(StarPo thumbUpPo);
    int deleteStarPoById(int userId,int artworkId);
    StarPo selectStarPoByUserIdAndArtworkId(int userId, int artworkId);
    int selectStarCountByArtworkId(int artworkId);
}
