package com.zkcm.problemtool.web.controller;

import com.zkcm.problemtool.common.domain.FebsConstant;
import com.zkcm.problemtool.common.domain.FebsResponse;
import com.zkcm.problemtool.common.exception.ProblemToolException;
import com.zkcm.problemtool.common.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@Slf4j
@Validated
@RestController
@RequestMapping("movie")
public class MovieController {

    private String message;

    @GetMapping("hot")
    public FebsResponse getMoiveHot() throws ProblemToolException {
        try {
            String data = HttpUtil.sendSSLPost(FebsConstant.TIME_MOVIE_HOT_URL, "locationId=328");
            return new FebsResponse().data(data);
        } catch (Exception e) {
            message = "获取热映影片信息失败";
            log.error(message, e);
            throw new ProblemToolException(message);
        }
    }

    @GetMapping("coming")
    public FebsResponse getMovieComing() throws ProblemToolException {
        try {
            String data = HttpUtil.sendSSLPost(FebsConstant.TIME_MOVIE_COMING_URL, "locationId=328");
            return new FebsResponse().data(data);
        } catch (Exception e) {
            message = "获取即将上映影片信息失败";
            log.error(message, e);
            throw new ProblemToolException(message);
        }
    }

    @GetMapping("detail")
    public FebsResponse getDetail(@NotBlank(message = "{required}") String id) throws ProblemToolException {
        try {
            String data = HttpUtil.sendSSLPost(FebsConstant.TIME_MOVIE_DETAIL_URL, "locationId=328&movieId=" + id);
            return new FebsResponse().data(data);
        } catch (Exception e) {
            message = "获取影片详情失败";
            log.error(message, e);
            throw new ProblemToolException(message);
        }
    }

    @GetMapping("comments")
    public FebsResponse getComments(@NotBlank(message = "{required}") String id) throws ProblemToolException {
        try {
            String data = HttpUtil.sendSSLPost(FebsConstant.TIME_MOVIE_COMMENTS_URL, "movieId=" + id);
            return new FebsResponse().data(data);
        } catch (Exception e) {
            message = "获取影片评论失败";
            log.error(message, e);
            throw new ProblemToolException(message);
        }
    }
}
