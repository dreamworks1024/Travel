package cn.dreamworks.travel.web.servlet;

import cn.dreamworks.travel.domain.PageBean;
import cn.dreamworks.travel.domain.Route;
import cn.dreamworks.travel.domain.User;
import cn.dreamworks.travel.service.FavoriteService;
import cn.dreamworks.travel.service.RouteService;
import cn.dreamworks.travel.service.impl.FavoriteServiceImpl;
import cn.dreamworks.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    // 声明一个routeService对象
    RouteService routeService = new RouteServiceImpl();
    // 声明一个favoriteService对象
    FavoriteService favoriteService = new FavoriteServiceImpl();

    /**
     * 查询分页信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");

        //接受rname 路线名称
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"), "UTF-8");

        //2.处理参数
        //声明一个当前页数的参数,默认值为1
        int currentPage = 1;    //当前页码，如果不传递，则默认为第一页
        if (currentPageStr != null && currentPageStr.length() != 0) {
            //传入参数有效,将传输的参数字符串转化为int类型，否则为默认参数1
            currentPage = Integer.parseInt(currentPageStr);

        }
        //声明一个每页显示的条数参数,默认值为5
        int pageSize = 5;  //每页显示条数，如果不传递，默认每页显示5条记录
        if (pageSizeStr != null && pageSizeStr.length() != 0) {
            //传入参数有效,将传输的参数字符串转化为int类型，否则为默认参数5
            pageSize = Integer.parseInt(pageSizeStr);

        }
        //声明一个分类标题码cid的参数,默认值为0
        int cid = 0;    //类别id
        if (cidStr != null && cidStr.length() != 0 && !"null".equals(cidStr)) {
            //传入参数有效,将传输的参数字符串转化为int类型，否则为默认参数0
            cid = Integer.parseInt(cidStr);

        }

        //3.调用service查询pageBean对象
        PageBean<Route> routePageBean = routeService.pageQuery(cid, currentPage, pageSize, rname);

        //4.将pageBean对象序列化为json并返回前台页面
        writeValue(routePageBean, response);
    }

    /**
     * 根据路线rid查询路线所有的信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收rid
        String rid = request.getParameter("rid");
        //2.调用service查询一个路线的所有详情信息
        Route route = routeService.findOne(Integer.parseInt(rid));
        //3.将得到的路线详情转化为json数据并返回到前台
        writeValue(route, response);
    }

    /**
     * 根据路线id查询当前用户是否已经收藏该路线
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收当前路线的rid
        String rid = request.getParameter("rid");
        //2.获取当前用户的uid
        User user = (User) request.getSession().getAttribute("user");
        int uid;    //当前用户id
        if (user != null) {
            //用户已登陆
            uid = user.getUid();
        } else {
            //用户尚未登录
            //uid = 0;
            return;
        }
        //3.调用favoriteService查询是否已收藏该线路
        boolean flag = favoriteService.isFavorite(rid, uid);
        //4.将结果返回到前台页面
        writeValue(flag, response);
    }

    /**
     * 添加收藏rid路线
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收路线的rid
        String rid = request.getParameter("rid");
        //2.获取当前用户
        User user = (User) request.getSession().getAttribute("user");
        //3.调用favoriteService去添加收藏
        int uid;    //当前用户id
        if (user != null) {
            //用户已登陆
            uid = user.getUid();
        } else {
            //用户尚未登录
            return;
        }
        //3. 调用service添加
        favoriteService.addFavorite(rid, uid);
    }
}