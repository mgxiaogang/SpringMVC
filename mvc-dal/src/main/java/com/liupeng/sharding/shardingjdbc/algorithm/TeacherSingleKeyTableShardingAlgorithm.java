package com.liupeng.sharding.shardingjdbc.algorithm;

import java.util.Collection;
import java.util.LinkedHashSet;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;

/**
 * user表分表逻辑，因为t_student实际表在每个库中只有3个，所以 %3
 *
 * t_user_00
 * t_user_01
 * t_user_02
 *
 * @author fengdao.lp
 * @date 2018/8/9
 */
public class TeacherSingleKeyTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Integer> {

    private int dbCount = 1;

    /**
     * sql 中 = 操作时，table的映射
     */
    @Override
    public String doEqualSharding(Collection<String> tableNames, ShardingValue<Integer> shardingValue) {
        for (String each : tableNames) {
            if (each.endsWith("0".concat(String.valueOf(shardingValue.getValue() % dbCount)))) {
                return each;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * sql 中 in 操作时，table的映射
     */
    @Override
    public Collection<String> doInSharding(Collection<String> tableNames, ShardingValue<Integer> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(tableNames.size());
        for (Integer value : shardingValue.getValues()) {
            for (String tableName : tableNames) {
                if (tableName.endsWith(("0".concat(String.valueOf(value % dbCount))))) {
                    result.add(tableName);
                }
            }
        }
        return result;
    }

    /**
     * sql 中 between 操作时，table的映射
     */
    @Override
    public Collection<String> doBetweenSharding(Collection<String> tableNames, ShardingValue<Integer> shardingValue) {
        Collection<String> result = new LinkedHashSet<>(tableNames.size());
        Range<Integer> range = shardingValue.getValueRange();
        for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String each : tableNames) {
                if (each.endsWith(("0".concat(String.valueOf(i % dbCount))))) {
                    result.add(each);
                }
            }
        }
        return result;
    }

    /**
     * 设置database分库的个数
     *
     * @param dbCount DB数量
     */
    public void setDbCount(int dbCount) {
        this.dbCount = dbCount;
    }
}
