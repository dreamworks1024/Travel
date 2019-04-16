package cn.dreamworks.travel.service;

import cn.dreamworks.travel.domain.Category;

import java.util.List;

/**
 * @author 林木旺
 * @version v1.0
 * @date 2019/4/13
 * @description
 */
public interface CategoryService {
    /**
     * 查询所有分类标题
     * @return 所以标题列表集合
     */
    List<Category> findAll();

}
