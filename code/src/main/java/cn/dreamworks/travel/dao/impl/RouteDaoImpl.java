package cn.dreamworks.travel.dao.impl;

import cn.dreamworks.travel.dao.RouteDao;
import cn.dreamworks.travel.domain.Route;
import cn.dreamworks.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 林木旺
 * @version v1.0
 * @date 2019/4/13
 * @description
 */
public class RouteDaoImpl implements RouteDao {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据cid, rname查询总记录数
     *
     * @param cid 标题分类id
     * @param rname 旅游线路名称
     * @return 总记录数
     */
    @Override
    public int findTotalCount(int cid, String rname) {
        //1.定义sql模板
        String sql = "SELECT COUNT(1) FROM tab_route WHERE 1 = 1";
        StringBuilder stringBuilder = new StringBuilder(sql);
        List params = new ArrayList();
        //2.判断参数是否有值
        if (cid != 0) {
            stringBuilder.append(" AND cid = ?");
            params.add(cid);
        }
        if (rname != null && rname.length() != 0 && !"null".equals(rname)) {
            stringBuilder.append(" AND rname LIKE ?");
            params.add("%" + rname + "%");
        }
        //更新sql
        sql = stringBuilder.toString();
        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    /**
     * 根据cid, start, pageSize, rname查询当前页数的数据集合
     *
     * @param cid      标题分类id
     * @param start    页面开始位置
     * @param pageSize 页面大小
     * @param rname    旅游线路名称
     * @return 当前页的数据集合
     */
    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        //1.定义sql模板
        String sql = "SELECT * FROM tab_route WHERE 1 = 1";
        StringBuilder stringBuilder = new StringBuilder(sql);//定义StringBuilder对象添加sql条件
        List params = new ArrayList();//定义ArrayList加入条件信息
        //2.判断参数是否有值
        if (cid != 0) {
            stringBuilder.append(" AND cid = ?");
            params.add(cid);
        }
        if (rname != null && rname.length() != 0 && !"null".equals(rname)) {
            stringBuilder.append(" AND rname LIKE ?");
            params.add("%" + rname + "%");
        }
        stringBuilder.append(" LIMIT ?, ?");
        params.add(start);
        params.add(pageSize);
        //更新sql
        sql = stringBuilder.toString();
        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
    }

    /**
     * 根据rid查询tab_route的所有信息
     * @param rid
     * @return 单一路线的tab_route的所有信息
     */
    @Override
    public Route findOne(int rid) {
        //1.定义sql语句
        String sql = "SELECT * FROM tab_route WHERE rid = ?";
        //2.执行sql，返回一个Route的所有信息
        return template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
    }
}
