package cn.dreamworks.travel.dao;

import cn.dreamworks.travel.domain.Route;

import java.util.List;

/**
 * @author 林木旺
 * @version v1.0
 * @date 2019/4/13
 * @description 路线dao
 */
public interface RouteDao {

    /**
     * 根据cid和rname查询总记录数
     * @param cid 标题分类id
     * @param rname 旅游路线名称
     * @return 总记录数
     */
    int findTotalCount(int cid, String rname);

    /**
     * 根据cid, start, pageSize, rname查询当前页数的数据集合
     * @param cid 标题分类id
     * @param start 页面开始位置
     * @param pageSize 页面大小
     * @param rname 旅游线路名称
     * @return
     */
    List<Route> findByPage(int cid, int start, int pageSize, String rname);

    /**
     * 根据rid查询tab_route的所有信息
     * @param rid
     * @return 单一路线的tab_route的所有信息
     */
    Route findOne(int rid);
}
