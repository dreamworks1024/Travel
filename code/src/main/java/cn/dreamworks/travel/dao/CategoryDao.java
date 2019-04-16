package cn.dreamworks.travel.dao;

import cn.dreamworks.travel.domain.Category;

import java.util.List;

/**
 * @author 林木旺
 * @version v1.0
 * @date 2019/4/13
 * @description
 */
public interface CategoryDao {

    /**
     * 查询所有分类名称
     * @return 分类列表集合
     */
    List<Category> findAll();
}
