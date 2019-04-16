package cn.dreamworks.travel.dao;

import cn.dreamworks.travel.domain.RouteImg;

import java.util.List;

/**
 * @author 林木旺
 * @version v1.0
 * @date 2019/4/15
 * @description 路线图片dao
 */
public interface RouteImgDao {

    /**
     * 根据旅游路线的rid去查询该路线的所有图片
     * @param rid 旅游路线的rid
     * @return 该rid路线的所有图片
     */
    List<RouteImg> findByRid(int rid);
}
