package com.hmdp.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hmdp.dto.Result;
import com.hmdp.entity.Shop;
import com.hmdp.entity.ShopType;
import com.hmdp.mapper.ShopTypeMapper;
import com.hmdp.service.IShopTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.hmdp.utils.RedisConstants.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author SleepWalker
 * @since 2022-11-8
 */
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Result queryTypeList() {
        //1.从redis中查询
        String key = CACHE_ShopType_LIST_KEY;
        String shopTypeJson = stringRedisTemplate.opsForValue().get(key);

        //2.判断有无商铺类型
        if(StrUtil.isNotBlank(shopTypeJson)) {
            //3.有就转化成JavaBean, 直接返回
            List<ShopType> list = JSONUtil.toList(shopTypeJson, ShopType.class);
            return Result.ok(list);
        }

        //4.无就查询数据库
        List<ShopType> shopTypeList = query().orderByAsc("sort").list();

        //5.判断数据库有无商铺类型
        if(shopTypeList == null) {
            //6.无就返回错误信息
            return Result.fail("商铺列表错误！");
        }

        //7.有就存入redis中
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(shopTypeList));

        //8.返回
        return Result.ok(shopTypeList);
    }
}
