package cn.dreamworks.travel.service;

/**
 * @author 林木旺
 * @version v1.0
 * @date 2019/4/16
 * @description
 */
public interface FavoriteService {

    /**
     * 根据路线rid和当前用户uid判断是否已经收藏该路线
     * @param rid 当前路线id
     * @param uid 当前用户id
     * @return ture 已经收藏该路线 || false 没有收藏该路线
     */
    boolean isFavorite(String rid, int uid);

    /**
     * 根据路线rid和当前用户uid去添加收藏信息
     * @param rid 该路线id
     * @param uid 当前用户id
     */
    void addFavorite(String rid, int uid);
}
