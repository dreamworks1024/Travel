package cn.dreamworks.travel.dao.impl;

import cn.dreamworks.travel.dao.FavoriteDao;
import cn.dreamworks.travel.domain.Favorite;
import cn.dreamworks.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

/**
 * @author 林木旺
 * @version v1.0
 * @date 2019/4/16
 * @description
 */
public class FavoriteDaoImpl implements FavoriteDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 通过路线rid和当前用户uid查询tab_favorite的单条记录所有信息
     *
     * @param rid 路线id
     * @param uid 当前用户id
     * @return 单条记录所有信息
     */
    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        Favorite favorite = null;
        //因为当uid不存在（用户没登陆时），会使sql执行时抛出异常，在此捕抓异常
        try {
            //1.定义sql
            String sql = "SELECT * FROM tab_favorite WHERE rid = ? AND uid = ?";
            //2.执行sql并返回一个Favorite对象
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {

        }
        return favorite;
    }

    /**
     * 根据rid查询该路线收藏的次数
     *
     * @param rid 路线id
     * @return 该路线收藏的次数
     */
    @Override
    public int findByRid(int rid) {
        //1.定义sql
        String sql = "SELECT COUNT(1) FROM tab_favorite WHERE rid = ?";
        //2.执行sql并返回rid路线的所有收藏记录总数
        return template.queryForObject(sql, Integer.class, rid);
    }

    /**
     * 根据路线rid和当前用户uid添加收藏
     * @param rid 路线id
     * @param uid 当前用户id
     */
    @Override
    public void add(int rid, int uid) {
        //1.定义插入sql
        String sql = "INSERT INTO tab_favorite VALUES (?, ?, ?)";
        //2.执行插入sql
        template.update(sql, rid, new Date(), uid);
    }
}
