package com.zrar.easyweb.bpmjob.typehandle;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 模仿 ObjectTypeHandle 来处理 timestamp 报错问题
 * @author yule
 * @date 2018/9/26 22:43
 */
@MappedTypes({Object.class})
@MappedJdbcTypes(value = {JdbcType.TIMESTAMP})
public class MyObjectTypeHandle extends BaseTypeHandler<Object> {

    public MyObjectTypeHandle() {
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
    	if (parameter instanceof LocalDateTime) {
    		parameter = Timestamp.valueOf((LocalDateTime)parameter);
		}
        ps.setObject(i, parameter);
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Object result = rs.getObject(columnName);
        return rs.wasNull() ? null : dealResult(result);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Object result = rs.getObject(columnIndex);
        return rs.wasNull() ? null : dealResult(result);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object result = cs.getObject(columnIndex);
        return cs.wasNull() ? null : dealResult(result);
    }

    /**
     * 为了解决错误：
     * 26-Sep-2018 14:21:06.634 WARNING [http-apr-8080-exec-6] org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver.handleHttpMessageNotWritable Failed to write HTTP message: org.springframework.http.converter.HttpMessageNotWritableException:
     * Could not write JSON: No serializer found for class java.io.ByteArrayInputStream and no properties discovered to create BeanSerializer
     * (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS);
     * nested exception is com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class java.io.ByteArrayInputStream and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS)
     * (through reference chain: java.util.HashMap["pageData"]->java.util.ArrayList[0]->java.util.HashMap["UPDATE_TIME"]->oracle.sql.TIMESTAMP["stream"])
     * @param result
     * @return
     * @throws SQLException
     */
    private Object dealResult(Object result) throws SQLException {
//
        return result;
    }
}