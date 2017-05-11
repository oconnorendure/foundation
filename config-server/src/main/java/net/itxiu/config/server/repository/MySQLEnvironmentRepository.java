package net.itxiu.config.server.repository;


import lombok.Getter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQLEnvironmentRepository implements EnvironmentRepository,InitializingBean,DisposableBean {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MySQLEnvironmentRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public Environment findOne(String application, String profile, String label) {
        String[] profilesArr = StringUtils.commaDelimitedListToStringArray(profile);
        String sql = "select `key` AS pro_key,raw_value,profile_name as profileName from properties where application_name=:applicationName and profile_name=:profileName";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("applicationName",application);
        param.addValue("profileName",profile);
        List<Tuple<String,String,String>> result = namedParameterJdbcTemplate.query(sql, param, new RowMapper<Tuple<String,String,String>>() {
            @Override
            public Tuple<String,String,String> mapRow(ResultSet resultSet, int i) throws SQLException {
                String key = resultSet.getString("pro_key");
                String value = resultSet.getString("raw_value");
                String profile = resultSet.getString("profileName");
                return new Tuple<>(key,value,profile);
            }
        });
        Environment environment = new Environment(application, profilesArr, label, null,null);
        String sourceName = String.format("%s-%s", application, profile);
        Map<String,String> map = new HashMap<>();
        for(Tuple<String,String,String> tuple: result){
            map.put(tuple.v1,tuple.v2);
        }
        environment.add(new PropertySource(sourceName,map));
        return environment;
    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Getter
    static class Tuple<V1,V2,V3>{
        private V1 v1;
        private V2 v2;
        private V3 v3;
        public Tuple(V1 v1,V2 v2,V3 v3){
            this.v1 = v1;
            this.v2 = v2;
            this.v3 = v3;
        }
    }
}