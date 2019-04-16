package cn.dreamworks.travel.service;

import cn.dreamworks.travel.domain.User;

/**
 * @author 林木旺
 * @version v1.0
 * @date 2019/4/10
 * @description
 */
public interface UserService {
    /**
     * 注册用户
     * @param user
     * @return true 注册成功    false 注册失败
     */
    boolean register(User user);

    /**
     * 激活用户
     * @param code
     * @return true 激活成功    fasle 激活失败
     */
    boolean active(String code);

    /**
     * 用户登陆
     * @param user
     * @return 完整的用户信息
     */
    User login(User user);
}
