package cn.dreamworks.travel.dao;

import cn.dreamworks.travel.domain.User;

/**
 * @author 林木旺
 * @version v1.0
 * @date 2019/4/10
 * @description
 */
public interface UserDao {
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return 单个用户
     */
    User findByUsername(String username);

    /**
     * 保存用户
     * @param user
     */
    void save(User user);

    /**
     * 根据激活码查询用户对象
     * @param code
     * @return user对象
     */
    User findByCode(String code);

    /**
     * 修改指定用户激活状态
     * @param user
     */
    void updateStatus(User user);

    /**
     * 根据用户名和密码查询用户所有信息
     * @param username
     * @param password
     * @return 指定用户的所有信息user
     */
    User findByUsernameAndPassword(String username, String password);
}
