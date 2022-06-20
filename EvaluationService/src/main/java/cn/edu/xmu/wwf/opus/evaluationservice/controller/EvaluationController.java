package cn.edu.xmu.wwf.opus.evaluationservice.controller;

import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import cn.edu.xmu.wwf.opus.evaluationservice.model.vo.PostCommentVo;
import cn.edu.xmu.wwf.opus.evaluationservice.service.EvaluationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@ApiOperation("评价模块")
@RequestMapping("/evaluation")
public class EvaluationController {
    @Autowired
    EvaluationService evaluationService;
    @ApiOperation("增加评价")
    @PostMapping("/comment")
    public ReturnObject postCommentsOnArtwork(@RequestHeader("token")String token, @RequestBody PostCommentVo postCommentVo){
        return evaluationService.addCommentToArtwork(0,postCommentVo);
    }
    @ApiOperation("获取作品的评价（分页，定义两种模式，一种按时间排序，一种按热门度排序）")
    @GetMapping("/comment/artwork/{id}")
    public ReturnObject getComments(@RequestHeader("token")String token,@PathVariable int id,@RequestParam int pageNum,@RequestParam int pageSize){
        return null;
    }
    @ApiOperation("删除评论")
    @DeleteMapping("/comment/{id}")
    public ReturnObject deleteComment(@RequestHeader("token")String token,@PathVariable int id){
        return evaluationService.delCommentForArtwork(0,id);
    }
    @ApiOperation("点赞作品")
    @PostMapping("/star/artwork/{id}")
    public ReturnObject thumbUp(@RequestHeader("token")String token,@PathVariable int id){
        return evaluationService.addStarToArtwork(0,id);
    }
    @ApiOperation("取消点赞")
    @PostMapping("/star/artwork/{id}/cancel")
    public ReturnObject cancelThumbUp(@RequestHeader("token")String token,@PathVariable int id) {
        return evaluationService.delStarForArtwork(0, id);
    }
    @ApiOperation("获取点赞数")
    @GetMapping("/star/artwork/{id}")
    public ReturnObject getStarNum(@RequestHeader("token")String token,@PathVariable int id){
        int userId=0;
        return evaluationService.getStarInfoForArtwork(userId,id);
    }
}
