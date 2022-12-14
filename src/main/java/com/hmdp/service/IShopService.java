package com.hmdp.service;

import com.hmdp.dto.Result;
import com.hmdp.entity.Shop;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author SleepWalker
 * @since 2022-11-8
 */
public interface IShopService extends IService<Shop> {
    public Result queryShopById(Long id);
    public Result update(Shop shop);
}
