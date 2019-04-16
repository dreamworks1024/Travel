package cn.dreamworks.travel.dao.impl;

import cn.dreamworks.travel.dao.CategoryDao;
import cn.dreamworks.travel.domain.Category;
import cn.dreamworks.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author 林木旺
 * @version v1.0
 * @date 2019/4/13
 * @description
 */
public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询所有分类名称
     *
     * @return 分类列表集合
     */
    @Override
    public List<Category> findAll() {
        String sql = "SELECT * FROM tab_category";
        return template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
    }
}
