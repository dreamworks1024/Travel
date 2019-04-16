package cn.dreamworks.travel.service.impl;

import cn.dreamworks.travel.dao.UserDao;
import cn.dreamworks.travel.dao.impl.UserDaoImpl;
import cn.dreamworks.travel.domain.User;
import cn.dreamworks.travel.service.UserService;
import cn.dreamworks.travel.util.MailUtils;
import cn.dreamworks.travel.util.UuidUtil;

/**
 * @author 林木旺
 * @version v1.0
 * @date 2019/4/10
 * @description
 */
public class UserServiceImpl implements UserService {


    private UserDao dao = new UserDaoImpl();

    /**
     * 注册用户
     *
     * @param user
     * @return 是否注册成功
     */
    @Override
    public boolean register(User user) {
        //1.根据用户名查询用户对象
        User u = dao.findByUsername(user.getUsername());
        //判断u是否为空
        if (u != null) {
            //用户名存在，注册失败
            return false;
        }
        //2.保存用户信息
        //2.1 设置激活码：唯一的字符串
        user.setCode(UuidUtil.getUuid());
        //2.2 设置状态码
        user.setStatus("N");
        dao.save(user);

        // 发送邮箱验证激活的内容
        String content = "<a href='http://localhost/travel/user/active?code=" + user.getCode() + "'>点击激活【梦想旅游网】</a>";
        MailUtils.sendMail(user.getEmail(), content, "邮箱登陆验证");

        return true;
    }

    /**
     * 激活用户
     *
     * @param code
     * @return true 激活成功  ||  fasle 激活失败
     */
    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user = dao.findByCode(code);
        if (user != null) {
            //2.调用dao修改激活码状态为激活
            dao.updateStatus(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 用户登陆
     * @param user
     * @return 完整的用户信息 user
     */
    @Override
    public User login(User user) {
        return dao.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }
}
