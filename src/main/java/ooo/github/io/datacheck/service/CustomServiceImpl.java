package ooo.github.io.datacheck.service;

import cn.hutool.core.collection.ListUtil;
import lombok.extern.slf4j.Slf4j;
import ooo.github.io.datacheck.config.ApplicationConfigSql;
import ooo.github.io.datacheck.dto.*;
import ooo.github.io.datacheck.util.TableRetrieveUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

/**
 * @author kaiqin
 */
@Service
@Slf4j
public class CustomServiceImpl {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ApplicationConfigSql applicationConfigSql;

    public CheckOutput list(CheckInputDTO inputDTO) {
        Long pageNo = inputDTO.getPageNo();
        Long pageSize = inputDTO.getPageSize();

        CheckConfig[] configSql = applicationConfigSql.getList();
        CheckOutput result = new CheckOutput();
        if (configSql == null || configSql.length == 0) {
            result.setData(Collections.emptyList());
            return CheckOutput.EMPTY;
        }
        List<ArchiveCheckResult> archiveCheckResults = new ArrayList<>();
        Set<String> checkTables = new HashSet<>();
        for (int i = 1; i <= configSql.length; i++) {
            CheckConfig checkConfig = configSql[i - 1];
            String sql = checkConfig.getSql();
            String desc = checkConfig.getDesc();
            checkTables.addAll(TableRetrieveUtil.retrieveTableName(sql));
            Query nativeQuery = entityManager.createNativeQuery(sql);
            List<Map<String, Object>> resultList = Collections.emptyList();
            try {
                resultList = nativeQuery.getResultList();
            } catch (Exception ignored) {
                log.info(sql + ":执行失败！");
                ArchiveCheckResult checkResult = recordFailureSql(sql, desc);
                archiveCheckResults.add(checkResult);
            }

            if (resultList.size() > 0) {
                //存在异常数据
                ArchiveCheckResult checkResult = recordFailureSql(sql, desc);
                archiveCheckResults.add(checkResult);
            }
        }
        result.setMsg("校验的表有：" + String.join(", ", checkTables));

        List<List<ArchiveCheckResult>> partition = ListUtil.partition(archiveCheckResults, Math.toIntExact(pageSize));
        if (partition.size() >= pageNo) {
            archiveCheckResults = partition.get(Math.toIntExact(pageNo) - 1);
        } else {
            return CheckOutput.EMPTY;
        }
        result.setData(archiveCheckResults);
        return result;
    }

    private ArchiveCheckResult recordFailureSql(String sql, String desc) {
        ArchiveCheckResult checkResult = new ArchiveCheckResult();
        checkResult.setSql(sql);
        checkResult.setDesc(desc);
        return checkResult;
    }

    public CheckOutput query(SqlQueryInputDTO inputDTO) {
        Long pageNo = inputDTO.getPageNo();
        Long pageSize = inputDTO.getPageSize();


        CheckConfig[] configSql = applicationConfigSql.getList();
        CheckOutput result = new CheckOutput();

        if (configSql == null || configSql.length == 0) {
            result.setData(Collections.emptyList());
            return CheckOutput.EMPTY;
        }
        result.setTotal((long) configSql.length);
        List<List<CheckConfig>> partition = ListUtil.partition(Arrays.asList(configSql), Math.toIntExact(pageSize));
        List<CheckConfig> checkConfigs;
        if (partition.size() >= pageNo) {
            checkConfigs = partition.get(Math.toIntExact(pageNo) - 1);
        } else {
            return CheckOutput.EMPTY;
        }

        List<ArchiveCheckResult> archiveCheckResults = new ArrayList<>();
        Set<String> checkTables = new HashSet<>();
        for (int i = 1; i <= checkConfigs.size(); i++) {
            CheckConfig checkConfig = checkConfigs.get(i - 1);
            String sql = checkConfig.getSql();
            String desc = checkConfig.getDesc();
            checkTables.addAll(TableRetrieveUtil.retrieveTableName(sql));
            ArchiveCheckResult checkResult = recordFailureSql(sql, desc);
            archiveCheckResults.add(checkResult);
        }
        result.setMsg("校验的表有：" + String.join(", ", checkTables));
        result.setData(archiveCheckResults);
        return result;
    }

}
