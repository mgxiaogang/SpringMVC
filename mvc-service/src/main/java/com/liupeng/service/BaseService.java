package com.liupeng.service;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liupeng.BaseMapper;
import com.liupeng.dto.BaseDO;
import com.liupeng.dto.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author fengdao.lp
 * @date 2018/2/15
 */
public abstract class BaseService<M extends BaseMapper<T>, T extends BaseDO> {

    @Autowired
    private M mapper;

    /**
     * insert方法：
     * <pre>
     *  insert                 : 如果Id为Null则用数据库的自增Id,如果Id不为Null, 则插入自定义的Id的值
     *  insertUseGeneratedKeys : 无论Id值Null或者NotNull都用数据库自增Id做插入
     *  insertList             : 批量插入
     *
     * </pre>
     *
     * update方法：
     * <pre>
     *  updateByPrimaryKey          : 根据主键更新所有字段, 字段值为Null的, 更新为Null
     *  updateByPrimaryKeySelective : 根据主键选择性更新, 字段值为Null的不更新
     * </pre>
     *
     * select方法:
     * <pre>
     *  selectAll : 获取全部列表, 适用于固定的配置或静态数据查询
     *  selectOne : 查询单条记录, 适用于有Unique key的记录, 用Unique做查询
     *  select    : 根据字段值做条件批量查询
     *  selectByPrimaryKey : 根据Id主键获取, 如果根据Id和未删除标记获取,则用SelectOne
     *  selectByIds : 根据Ids批量查询ids 如 "1,2,3,4", 再封装成List<String>
     *  selectCount : 根据条件获取符合的条数
     *  selectByRowBounds : 字段查询并做内存分页
     * </pre>
     *
     * delete方法:
     * <pre>
     *  deleteByPrimaryKey          : 根据Ids物理删除(deleteByIds)
     *  updateByPrimaryKeySelective : 逻辑删除, 加一个删除的标记
     * </pre>
     *
     * exists方法:
     * <pre>
     *  existsWithPrimaryKey : 根据主键判断值是否存在
     * </pre>
     */
    public void insert(T user) {
        mapper.insertUseGeneratedKeys(user);
    }

    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }

    public void deleteByPrimaryKey(Long key) {
        mapper.deleteByPrimaryKey(key);
    }

    public PageInfo<User> selectByPage() {
        PageHelper.startPage(1, 2);
        List<User> list = (List<User>)mapper.selectAll();
        System.out.println(list);
        return new PageInfo<>(list);
    }
}
