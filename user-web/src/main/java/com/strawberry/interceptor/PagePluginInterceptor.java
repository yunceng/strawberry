package com.strawberry.interceptor;

import com.strawberry.util.bean.PageViewBean;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 功能：基于Mybatis拦截器的分页插件
 *
 * @author alan.wang
 * @version 1.0.0
 * @since 2018.09.27
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class PagePluginInterceptor implements Interceptor {

    private static final Logger LOG = Logger.getLogger(PagePluginInterceptor.class);

    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        //通过MetaObject优雅访问对象的属性，这里是访问statementHandler的属性
        MetaObject metaObject = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        //先拦截到RoutingStatementHandler，里面有个StatementHandler类型的delegate变量，其实现类是BaseStatementHandler，然后就到BaseStatementHandler的成员变量mappedStatement
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        // 配置文件中SQL语句的ID
        String id = mappedStatement.getId();
        if (id.matches(".+ByPage$")) { //需要拦截的ID(正则匹配)
            BoundSql boundSql = statementHandler.getBoundSql();
            // 原始的SQL语句
            String sql = boundSql.getSql();
            // 查询总条数的SQL语句
            String countSql = "select count(*) from (" + sql + ")a";
            //执行总条数SQL语句的查询
            Connection connection = (Connection) invocation.getArgs()[0];
            PreparedStatement countStatement = connection.prepareStatement(countSql);
            ////获取参数信息即where语句的条件信息，注意上面拿到的sql中参数还是用?代替的
            ParameterHandler parameterHandler = (ParameterHandler) metaObject.getValue("delegate.parameterHandler");
            parameterHandler.setParameters(countStatement);
            ResultSet rs = countStatement.executeQuery();

            Object parameterObject = boundSql.getParameterObject();

            if (parameterObject != null) {
                PageViewBean pageView = null;
                if (parameterObject instanceof PageViewBean) {
                    pageView = (PageViewBean) parameterObject;
                } else if (parameterObject instanceof MapperMethod.ParamMap) {
                    for (Map.Entry entry : (Set<Map.Entry>) ((MapperMethod.ParamMap) parameterObject).entrySet()) {
                        if (entry.getValue() instanceof PageViewBean) {
                            pageView = (PageViewBean) entry.getValue();
                            break;
                        }
                    }
                }
                if (rs.next()) {
                    pageView.setTotal(rs.getInt(1));
                }
                // 改造后带分页查询的SQL语句
                String pageSql = sql + " limit " + pageView.getStartRow() + "," + pageView.getPageSize();
                metaObject.setValue("delegate.boundSql.sql", pageSql);
                LOG.debug("分页操作语句：" + id);
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
