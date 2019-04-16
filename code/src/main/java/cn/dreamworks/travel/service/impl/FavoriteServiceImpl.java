package cn.dreamworks.travel.service.impl;

import cn.dreamworks.travel.dao.FavoriteDao;
import cn.dreamworks.travel.dao.impl.FavoriteDaoImpl;
import cn.dreamworks.travel.domain.Favorite;
import cn.dreamworks.travel.service.FavoriteService;

/**
 * @author 林木旺
 * @version v1.0
 * @date 2019/4/16
 * @description
 */
public class FavoriteServiceImpl implements FavoriteService {

    //创建一个FavoriteDao的对象
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     * 根据路线rid和当前用户uid判断是否已经收藏该路线
     *
     * @param rid 当前路线id
     * @param uid 当前用户id
     * @return ture 已经收藏该路线 || false 没有收藏该路线
     */
    @Override
    public boolean isFavorite(String rid, int uid) {
        //1.调用favoriteDao对象根据路线rid和当前用户uid查询Favorite对象信息
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
        //2.返回对象是否为空
        return favorite != null;
    }

    /**
     * 根据路线rid和当前用户uid去添加收藏信息
     * @param rid 该路线id
     * @param uid 当前用户id
     */
    @Override
    public void addFavorite(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid), uid);
    }
}
