package cn.dreamworks.travel.dao.impl;

import cn.dreamworks.travel.dao.RouteImgDao;
import cn.dreamworks.travel.domain.RouteImg;
import cn.dreamworks.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author 林木旺
 * @version v1.0
 * @date 2019/4/15
 * @description
 */
public class RouteImgDaoImpl implements RouteImgDao {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据旅游路线的rid去查询该路线的所有图片
     * @param rid 旅游路线的rid
     * @return 该rid路线的所有图片
     */
    @Override
    public List<RouteImg> findByRid(int rid) {
        //1.定义sql
        String sql = "SELECT * FROM tab_route_img WHERE rid = ?";
        return template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
    }
}
