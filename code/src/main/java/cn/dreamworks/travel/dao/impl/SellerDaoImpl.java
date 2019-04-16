package cn.dreamworks.travel.dao.impl;

import cn.dreamworks.travel.dao.SellerDao;
import cn.dreamworks.travel.domain.Route;
import cn.dreamworks.travel.domain.Seller;
import cn.dreamworks.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author 林木旺
 * @version v1.0
 * @date 2019/4/15
 * @description
 */
public class SellerDaoImpl implements SellerDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据旅游路线的旅行社供应商sid查询该旅游商的所有信息
     * @param sid 旅游路线的旅行社供应商的sid
     * @return 该旅游商的所有信息
     */
    @Override
    public Seller findByRid(int sid) {
        //1.定义sql语句
        String sql = "SELECT * FROM tab_seller WHERE sid = ?";
        //2.执行sql，返回一个Seller的所有信息
        return template.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class), sid);
    }
}
