package com.fline.form.mgmt.service;

import com.alibaba.fastjson.JSONArray;
import com.fline.form.vo.PostInfo;

public interface PostInfoMgmtService {

    JSONArray getPostInfo(String userId);

    long addPostInfo(String formBusiCode, PostInfo postInfo);

    long updatePostInfo(String formBusiCode, PostInfo postInfo);

    long deletePostInfo(String formBusiCode, String id);
}
