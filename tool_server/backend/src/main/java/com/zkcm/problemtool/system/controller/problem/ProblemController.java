package com.zkcm.problemtool.system.controller.problem;

import com.zkcm.problemtool.common.controller.BaseController;
import com.zkcm.problemtool.common.domain.QueryRequest;
import com.zkcm.problemtool.response.ResultResponse;
import com.zkcm.problemtool.system.controller.problem.facade.ProblemFacade;
import com.zkcm.problemtool.system.domain.ProblemInfo;
import com.zkcm.problemtool.system.service.problem.IProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/problem")
public class ProblemController extends BaseController {

    @Resource
    private IProblemService problemService;
    @Autowired
    private ProblemFacade problemFacade;
    /**
     * @desc 分页
     * @author zzh
     * @date 2020/09/15
     */
    @GetMapping("/page")
    public ResultResponse page(QueryRequest request, ProblemInfo problemInfo){
        return ResultResponse.page(problemService.page(request,problemInfo,getCurrentUser()));
    }

    /**
     * @desc save
     * @author zzh
     * @date 2020/09/16
     */
    @PostMapping("/save")
    public ResultResponse save(@RequestBody List<ProblemInfo> problemInfo) {
        return problemFacade.save(problemInfo,getCurrentUser());
    }


    /**
     * 详情查看
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResultResponse get(@PathVariable long id) {
        return problemFacade.get(id);
    }

    /**
     * @desc 编辑
     * @author zzh
     * @date 2020/09/16
     */
    @PutMapping("/update")
    public ResultResponse update(@RequestParam("problem")  ProblemInfo problemInfo){
        return problemFacade.update(problemInfo);
    }


    /**
     * @desc 删除
     * @author zzh
     * @date 2020/09/16
     */
    @DeleteMapping("/{ids}")
    public ResultResponse del(@PathVariable  String ids){
        return problemFacade.del(ids);
    }

    /**
     * @desc 发布
     * @author zzh
     * @date 2020/09/16
     */
    @PutMapping("/publish")
    public ResultResponse publish(@RequestParam("id")  long id){
        return problemFacade.publish(id);
    }


}
