package cn.dreamworks.travel.web.servlet;

import cn.dreamworks.travel.domain.ResultInfo;
import cn.dreamworks.travel.domain.User;
import cn.dreamworks.travel.service.UserService;
import cn.dreamworks.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    //声明UserService业务对象
    private UserService service = new UserServiceImpl();

    /**
     * 注册功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从前台获取校验码
        String check = request.getParameter("check");

        //从session中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//为了保证验证码只能有效一次
        //比较
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            // 验证码错误
            ResultInfo info = new ResultInfo();
            // 注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            // 将info对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            //将json数据写回客户端
            //设置content-type
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }

        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用service完成注册
        boolean flag = service.register(user);
        ResultInfo info = new ResultInfo();
        if (flag) {
            // 注册成功
            info.setFlag(true);
        } else {
            // 注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败");
        }

        //将info对象序列化为json
        /*ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);*/
        String json = writeValueAsString(info);

        //将json数据写回客户端
        //设置content-type
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /**
     * 激活验证码功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取激活码
        String code = request.getParameter("code");
        //判断激活码不为空
        if (code != null){
            //2.调用service完成激活
            Boolean flag = service.active(code);

            //3. 判断标记
            String msg = null;
            if (flag) {
                //激活成功
                msg = "激活成功，请<a href='http://localhost/travel/login.html'>登陆</a>";
            } else {
                //激活失败
                msg = "激活失败，请联系管理员";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    /**
     * 登陆功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从前台获取验证码
        String check = request.getParameter("check");
        //从session中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");    //保证session只有一次有效
        //校验验证码
        if (checkcode_server == null || !check.equalsIgnoreCase(checkcode_server)){
            // 验证码错误，校验不通过
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //将info对象序列化为json
            /*ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json;charset=utf-8");
            //将json数据写回客户端
            //设置content-type
            mapper.writeValue(response.getOutputStream(), info);*/
            writeValue(info, response);
            return;
        }

        //1.获取用户名和密码数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装User对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用service查询，返回一个完整的User对象u
        User u = service.login(user);

        ResultInfo info = new ResultInfo();
        //4.判断用户对象是否为null
        if (u == null){
            //用户名或密码错误
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }

        //5.判断用户是否激活
        if (u != null && !u.getStatus().equals("Y")){
            // 用户尚未激活
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，请登陆邮箱激活账号");
        }

        //6.判断登陆成功
        if (u != null && u.getStatus().equals("Y")){
            //将用户信息写进session中
            request.getSession().setAttribute("user", u);
            //登陆成功
            info.setFlag(true);
        }

        //响应数据
        /*ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        //将json数据写回客户端
        //设置content-type
        mapper.writeValue(response.getOutputStream(), info);*/
        writeValue(info, response);
    }

    /**
     * 退出功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.销毁session
        request.getSession().invalidate();
        //2.跳转到登陆页面
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    /**
     * 查询单个用户功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从servlet中获取登陆用户
        Object user = request.getSession().getAttribute("user");
        /*//将用户user写回客户端
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), user);*/
        writeValue(user, response);
    }
}
