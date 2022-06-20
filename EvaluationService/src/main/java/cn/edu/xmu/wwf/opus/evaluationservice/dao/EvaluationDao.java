package cn.edu.xmu.wwf.opus.evaluationservice.dao;

import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnNo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import cn.edu.xmu.wwf.opus.evaluationservice.mapper.EvaluationMapper;
import cn.edu.xmu.wwf.opus.evaluationservice.model.po.CommentPo;
import cn.edu.xmu.wwf.opus.evaluationservice.model.po.StarPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EvaluationDao {
    @Autowired
    EvaluationMapper evaluationMapper;

    public ReturnObject getCommentPoFromDB(int id){
        CommentPo commentPo=evaluationMapper.selectCommentPoById(id);
        if(commentPo==null){
            return new ReturnObject(ReturnNo.RESOURCE_NOT_FOUND,"评论不存在");
        }
        return new ReturnObject(commentPo);
    }
    public int addCommentIntoDB(CommentPo commentPo){
        return evaluationMapper.insertCommentPo(commentPo);
    }
    public int deleteCommentFromDB(int id){
        return evaluationMapper.deleteCommentPoById(id);
    }
    public int addStarIntoDB(StarPo thumbUpPo){
        return evaluationMapper.insertStarPo(thumbUpPo);
    }
    public int deleteStarFromDB(int userId,int artworkId){
        return evaluationMapper.deleteStarPoById(userId,artworkId);
    }
    public ReturnObject getStarPoFromDB(int userId,int artworkId){
        StarPo thumbUpPo=evaluationMapper.selectStarPoByUserIdAndArtworkId(userId,artworkId);
        if(thumbUpPo==null){
            return new ReturnObject(ReturnNo.RESOURCE_NOT_FOUND,"点赞记录不存在");
        }
        return new ReturnObject(thumbUpPo);
    }
    public int getStarCountFromDB(int artworkId){
        return evaluationMapper.selectStarCountByArtworkId(artworkId);
    }
}
