package com.fline.form.controller;

import com.fline.form.mgmt.service.PostInfoMgmtService;
import com.fline.form.vo.PostInfo;
import com.fline.form.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 政务服务网快递服务
 *
 */
@RestController
@RequestMapping("/postInfo")
public class PostInfoController {

    @Autowired
    private PostInfoMgmtService postInfoMgmtService;

    @PostMapping("/{formBusiCode}/add")
    public ResponseResult<Long> addPostInfo(@PathVariable("formBusiCode") String formBusiCode
            , @RequestBody PostInfo postInfo) {
        long id = postInfoMgmtService.addPostInfo(formBusiCode, postInfo);
        return ResponseResult.success(id);
    }

    @PostMapping("/{formBusiCode}/update")
    public ResponseResult<Long> updatePostInfo(@PathVariable("formBusiCode") String formBusiCode
            , @RequestBody PostInfo postInfo) {
        long id = postInfoMgmtService.updatePostInfo(formBusiCode, postInfo);
        return ResponseResult.success(id);
    }

    @PostMapping("/{formBusiCode}/delete/{id}")
    public ResponseResult<Long> deletePostInfo(@PathVariable("formBusiCode") String formBusiCode
            , @PathVariable("id") String id) {
        long result = postInfoMgmtService.deletePostInfo(formBusiCode, id);
        return ResponseResult.success(result);
    }
}
