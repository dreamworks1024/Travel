package cn.dreamworks.travel.dao.impl;

import cn.dreamworks.travel.dao.UserDao;
import cn.dreamworks.travel.domain.User;
import cn.dreamworks.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * @author 林木旺
 * @version v1.0
 * @date 2019/4/10
 * @description
 */
public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUsername(String username) {
        User user = null;
        // 定义异常，因为当数据库没有相关的username时，执行sql返回的结果会抛出异常
        try {
            //1.定义sql
            String sql = "SELECT * FROM tab_user WHERE username = ?";
            //2.执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (DataAccessException e) {

        }
        return user;
    }

    @Override
    public User findByCode(String code) {
        User user = null;
        //捕捉查询不到用户抛出异常的异常
        try {
            //1.根据激活码查询指定用户的所有信息
            String sql = "SELECT * FROM tab_user WHERE code = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {

        }
        return user;
    }

    @Override
    public void updateStatus(User user) {
        //1.定义sql，更新指定用户的激活状态为Y
        String sql = "UPDATE tab_user SET status = 'Y' WHERE uid = ?";
        //2.执行sql
        template.update(sql, user.getUid());
    }

    @Override
    public void save(User user) {
        //1.定义sql
        String sql = "INSERT INTO tab_user(username, password, name, birthday, sex, telephone, email, status, code)" +
                " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        //2.执行sql
        template.update(sql, user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        // 定义异常，因为当数据库没有相关的username时，执行sql返回的结果会抛出异常
        try {
            //1.定义sql
            String sql = "SELECT * FROM tab_user WHERE username = ? AND password = ?";
            //2.执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        } catch (DataAccessException e) {

        }
        return user;
    }


}
