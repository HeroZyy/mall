package com.imooc.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.CategoryMapper;
import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.request.AddCategoryReq;
import com.imooc.mall.model.vo.CategoryVO;
import com.imooc.mall.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加目录实现类
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public void add(AddCategoryReq addCategoryReq) throws RuntimeException {
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq,category);
        Category categoryOld = categoryMapper.selectByName(addCategoryReq.getName());
        if(categoryOld != null) throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        int count = categoryMapper.insertSelective(category);
        if(count==0) { throw new ImoocMallException(ImoocMallExceptionEnum.CREATE_FAILED);}
    }

    @Override
    public void update(Category updateCategory){
        if(updateCategory.getName() != null){
            Category category = new Category();
            BeanUtils.copyProperties(updateCategory,category);
            Category categoryOld = categoryMapper.selectByName(updateCategory.getName());
            if(categoryOld != null && !categoryOld.getId().equals(updateCategory.getId())){
                throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
            }
            int count = categoryMapper.updateByPrimaryKeySelective(updateCategory);
            if(count == 0){
                throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
            }

        }
    }

    @Override
    public void delete(Integer id){
        Category categoryOld = categoryMapper.selectByPrimaryKey(id);
        if(categoryOld == null){
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }
        int count = categoryMapper.deleteByPrimaryKey(id);
        if(count == 0){
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum , pageSize , "type,order_num");
        List<Category> categoryList = categoryMapper.selectlist();
        PageInfo pageInfo = new PageInfo(categoryList);
        return pageInfo;
    }

    @Override
    @Cacheable(value = "listCategoryForCustomer")
    public List<CategoryVO> listCategoryForCustomer(Integer parentId){
        ArrayList<CategoryVO> categoryVOArrayList = new ArrayList<>();
        recursivelyFindCategories(categoryVOArrayList,parentId);
        return categoryVOArrayList;
    }

    private void recursivelyFindCategories(List<CategoryVO> categoryVOArrayList , Integer parentId){
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(parentId);
        //递归获取所有子类别，并合成一个“目录树”
        if(!CollectionUtils.isEmpty(categoryList)){
            for (Category a : categoryList) {
                CategoryVO categoryVO = new CategoryVO();
                BeanUtils.copyProperties(a,categoryVO);
                categoryVOArrayList.add(categoryVO);
                recursivelyFindCategories(categoryVO.getChildCategory(),categoryVO.getId());
            }
        }
    }
}
