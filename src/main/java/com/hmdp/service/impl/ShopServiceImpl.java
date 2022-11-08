package com.hmdp.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hmdp.dto.Result;
import com.hmdp.entity.Shop;
import com.hmdp.mapper.ShopMapper;
import com.hmdp.service.IShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.hmdp.utils.RedisConstants.CACHE_SHOP_KEY;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {
    @Resource
    StringRedisTemplate stringRedisTemplate;
    
    @Override
    public Result queryShopById(Long id) {
        //1.从redis中查询
        String key = CACHE_SHOP_KEY + id;
        String shopJson = stringRedisTemplate.opsForValue().get(key);

        //2.判断有无商铺
        if(StrUtil.isNotBlank(shopJson)) {
            //3.有就转化成JavaBean, 直接返回
            Shop bean = JSONUtil.toBean(shopJson, Shop.class);
            return Result.ok(bean);
        }

        //4.无就查询数据库
        Shop shop = getById(id);

        //5.判断数据库有无商铺
        if(shop == null) {
            //6.无就返回错误信息, 商铺id不存在
            return Result.fail("商铺id不存在！");
        }

        //7.有就存入redis中
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(shop));

        //8.返回
        return Result.ok(shop);
    }
}
