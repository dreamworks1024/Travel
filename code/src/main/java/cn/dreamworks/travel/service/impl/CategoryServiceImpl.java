package cn.dreamworks.travel.service.impl;

import cn.dreamworks.travel.dao.CategoryDao;
import cn.dreamworks.travel.dao.impl.CategoryDaoImpl;
import cn.dreamworks.travel.domain.Category;
import cn.dreamworks.travel.service.CategoryService;
import cn.dreamworks.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 林木旺
 * @version v1.0
 * @date 2019/4/13
 * @description
 */
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao dao = new CategoryDaoImpl();

    /**
     * 查询所有分类标题
     *
     * @return 所以标题列表集合
     */
    @Override
    public List<Category> findAll() {
        //1.从redis中查询
        //1.1获取jedis客户端
        Jedis jedis = JedisUtil.getJedis();
        //1.2可使用sortedset排序查询
        //Set<String> categorys = jedis.zrange("category", 0, -1);

        //1.3查询sortedset中的分数(cid)和值(cname)
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);


        List<Category> cs = null;
        //2.判断查询的集合是否为空
        if (categorys == null || categorys.size() == 0) {
            //3.如果为空，需要从数据库查询，在将数据存入redis
            //3.1从数据库查询
            cs = dao.findAll();
            //3.2将集合数据存储到redis中的category的key
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category", cs.get(i).getCid(), cs.get(i).getCname());
            }
        } else {//redis集合中有数据
            //4.如果不为空，将set的数据存入list
            cs = new ArrayList<Category>();
            for (Tuple name : categorys) {
                Category category = new Category();
                category.setCname(name.getElement());
                category.setCid((int) name.getScore());
                cs.add(category);
            }
        }

        return cs;
    }
}
