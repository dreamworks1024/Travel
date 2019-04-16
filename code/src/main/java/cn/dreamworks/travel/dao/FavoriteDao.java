package cn.dreamworks.travel.dao;

import cn.dreamworks.travel.domain.Favorite;

/**
 * @author 林木旺
 * @version v1.0
 * @date 2019/4/16
 * @description
 */
public interface FavoriteDao {
    /**
     * 通过路线rid和当前用户uid查询tab_favorite的单条记录所有信息
     * @param rid 路线id
     * @param uid 当前用户id
     * @return 单条记录所有信息
     */
    Favorite findByRidAndUid(int rid, int uid);

    /**
     * 根据rid查询该路线收藏的次数
     * @param rid 路线id
     * @return 该路线收藏的次数
     */
    int findByRid(int rid);

    /**
     * 根据路线rid和当前用户uid添加收藏
     * @param rid 路线id
     * @param uid 当前用户id
     */
    void add(int rid, int uid);
}
