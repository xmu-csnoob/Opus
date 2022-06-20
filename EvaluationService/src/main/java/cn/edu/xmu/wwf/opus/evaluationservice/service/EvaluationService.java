package cn.edu.xmu.wwf.opus.evaluationservice.service;

import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnNo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import cn.edu.xmu.wwf.opus.evaluationservice.dao.EvaluationDao;
import cn.edu.xmu.wwf.opus.evaluationservice.model.po.CommentPo;
import cn.edu.xmu.wwf.opus.evaluationservice.model.po.StarPo;
import cn.edu.xmu.wwf.opus.evaluationservice.model.vo.GetStarInfoVo;
import cn.edu.xmu.wwf.opus.evaluationservice.model.vo.PostCommentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {
    @Autowired
    EvaluationDao evaluationDao;
    public ReturnObject addCommentToArtwork(int userId, PostCommentVo postCommentVo){
        CommentPo commentPo=new CommentPo();
        BeanUtils.copyProperties(postCommentVo,commentPo);
        commentPo.setUserId(userId);
        evaluationDao.addCommentIntoDB(commentPo);
        return new ReturnObject(postCommentVo);
    }
    public ReturnObject delCommentForArtwork(int userId,int id){
        ReturnObject<CommentPo> returnObject=evaluationDao.getCommentPoFromDB(id);
        if(returnObject.returnNo!= ReturnNo.OK){
            return returnObject;
        }
        CommentPo origin=returnObject.data;
        if(origin.getUserId()!=userId){
            return new ReturnObject(ReturnNo.FORBIDDEN,"您没有权限删除该评论！");
        }
        evaluationDao.deleteCommentFromDB(id);
        return new ReturnObject(id);
    }
    public ReturnObject addStarToArtwork(int userId, int artworkId){
        StarPo thumbUpPo=new StarPo();
        thumbUpPo.setUserId(userId);
        thumbUpPo.setArtworkId(artworkId);
        evaluationDao.addStarIntoDB(thumbUpPo);
        return new ReturnObject(thumbUpPo);
    }
    public ReturnObject delStarForArtwork(int userId,int artworkId){
        ReturnObject<StarPo> returnObject=evaluationDao.getStarPoFromDB(userId,artworkId);
        if(returnObject.returnNo!= ReturnNo.OK){
            return returnObject;
        }
        StarPo origin=returnObject.data;
        if(origin.getUserId()!=userId){
            return new ReturnObject(ReturnNo.FORBIDDEN,"您没有权限取消该点赞！");
        }
        evaluationDao.deleteStarFromDB(userId,artworkId);
        return new ReturnObject(artworkId);
    }
    public ReturnObject getStarInfoForArtwork(int userId,int artworkId){
        ReturnObject ret=evaluationDao.getStarPoFromDB(userId, artworkId);
        GetStarInfoVo getStarInfoVo=new GetStarInfoVo();
        getStarInfoVo.setStarCount(evaluationDao.getStarCountFromDB(artworkId));
        if(ret.returnNo!=ReturnNo.OK){
            getStarInfoVo.setHasStarred(false);
        }else{
            getStarInfoVo.setHasStarred(true);
        }
        return new ReturnObject(getStarInfoVo);
    }
}
