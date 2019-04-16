package cn.dreamworks.travel.service.impl;

import cn.dreamworks.travel.dao.FavoriteDao;
import cn.dreamworks.travel.dao.RouteDao;
import cn.dreamworks.travel.dao.RouteImgDao;
import cn.dreamworks.travel.dao.SellerDao;
import cn.dreamworks.travel.dao.impl.FavoriteDaoImpl;
import cn.dreamworks.travel.dao.impl.RouteDaoImpl;
import cn.dreamworks.travel.dao.impl.RouteImgDaoImpl;
import cn.dreamworks.travel.dao.impl.SellerDaoImpl;
import cn.dreamworks.travel.domain.PageBean;
import cn.dreamworks.travel.domain.Route;
import cn.dreamworks.travel.domain.RouteImg;
import cn.dreamworks.travel.domain.Seller;
import cn.dreamworks.travel.service.FavoriteService;
import cn.dreamworks.travel.service.RouteService;

import java.util.List;

/**
 * @author 林木旺
 * @version v1.0
 * @date 2019/4/13
 * @description 路线service
 */
public class RouteServiceImpl implements RouteService {

    //创建一个路线dao
    private RouteDao routeDao = new RouteDaoImpl();
    //创建一个路线信息dao
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    //创建一个路线旅游商dao
    private SellerDao sellerDao = new SellerDaoImpl();
    //创建一个路线收藏dao
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     * 查询分页信息
     * @param cid 标题分类id
     * @param currentPage 当前页面
     * @param pageSize 页面大小
     * @param rname 旅游线路名称
     * @return 该页的所有数据信息
     */
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        //封装pageBean
        PageBean<Route> routePageBean = new PageBean<>();
        //设置当前页码
        routePageBean.setCurrentPage(currentPage);
        //设置每页显示条数
        routePageBean.setPageSize(pageSize);
        //设置总记录数
        int totalCount = routeDao.findTotalCount(cid, rname);
        routePageBean.setTotalCount(totalCount);
        //设置当前页显示的数据集合
        int start = (currentPage - 1) * pageSize;
        List<Route> list = routeDao.findByPage(cid, start, pageSize, rname);
        routePageBean.setList(list);
        //设置总页数 = 总记录数 / 每页显示条数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        routePageBean.setTotalPage(totalPage);
        return routePageBean;
    }

    /**
     * 查询一个路线的详情信息
     * @param rid 路线的id
     * @return 一个路线的详情信息
     */
    @Override
    public Route findOne(int rid) {
        //1.根据rid去tab_route表查询route对象
        Route route = routeDao.findOne(rid);
        //2.1 根据route的rid去查询图片集合信息
        List<RouteImg> routeImg = routeImgDao.findByRid(route.getRid());
        //2.2 将集合设置到route对象
        route.setRouteImgList(routeImg);
        //3.1 根据route的sid（商家也是sid）查询商家对象
        Seller seller = sellerDao.findByRid(route.getSid());
        //3.2 将旅游商设置到route对象中
        route.setSeller(seller);
        //4.1 根据rid查询该路线被收藏的次数
        int count = favoriteDao.findByRid(rid);
        //4.2 将该路线的收藏次数设置到route对象中
        route.setCount(count);
        return route;
    }
}
