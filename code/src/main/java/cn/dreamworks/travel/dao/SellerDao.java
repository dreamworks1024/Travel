package cn.dreamworks.travel.dao;

import cn.dreamworks.travel.domain.Seller;

/**
 * @author 林木旺
 * @version v1.0
 * @date 2019/4/15
 * @description 旅游商信息
 */
public interface SellerDao {

    /**
     * 根据旅游路线的rid查询该旅游商的所有信息
     * @param rid 旅游路线的rid
     * @return 该旅游商的所有信息
     */
    Seller findByRid(int rid);
}
