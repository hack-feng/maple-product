package com.maple.dynamic.test2.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.maple.dynamic.test2.entity.Product;
import com.maple.dynamic.test2.mapper.ProductMapper;
import com.maple.dynamic.test2.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author 笑小枫
 * @since 2023-08-21
 */
@Service
@DS("test2")
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
