package cn.dreamworks.travel.service;

import cn.dreamworks.travel.domain.PageBean;
import cn.dreamworks.travel.domain.Route;

/**
 * @author 林木旺
 * @version v1.0
 * @date 2019/4/13
 * @description 路线service
 */
public interface RouteService {
    /**
     * 查询分页信息
     * @param cid 标题分类id
     * @param currentPage 当前页面
     * @param pageSize 页面大小
     * @param rname 旅游线路名称
     * @return 该页的所有数据信息
     */
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    /**
     * 查询一个路线的详情信息
     * @param rid 路线的id
     * @return 一个路线的详情信息
     */
    Route findOne(int rid);
}
