package name.yalsooni.genius.paperfactory.util;

import name.yalsooni.boothelper.util.jdbc.vo.JDBCConnectInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by hello@yalsooni.name on 2020/01/15
 */
public class PropertyHelper {

    // 프로퍼티 설정
    private final static String DEFAULTATTR_PREFIX = "ATTR.DEFAULT.";
    private final static String JDBCINFO_PREFIX = "DS.";
    private final static String META_DS_NAME = "METAPF";
    private final static String JDBC_USER = "USER";
    private final static String JDBC_PASSWD = "PASSWD";
    private final static String JDBC_URL = "URL";
    private final static String JDBC_DRIVER = "DRIVER";

    private final static int IDX_DS_NAME = 1;
    private final static int IDX_ATTR_NAME = 2;

    /**
     * 프로퍼티 설정을 DS 맵으로 변환한다.
     * @param properties
     */
    public static Map<String, JDBCConnectInfo> propertyToJDBCMap(Properties properties) throws Exception {

        Map<String, JDBCConnectInfo> jdbcConnectInfoMap = new HashMap<>();

        Set<Object> keys = properties.keySet();

        for( Object key : keys){
            String keyStr = (String) key;
            if(keyStr.startsWith(JDBCINFO_PREFIX)){
                String[] keysSplit = keyStr.split("\\.");

                String dsName = keysSplit[IDX_DS_NAME];
                String dsAttr = keysSplit[IDX_ATTR_NAME];

                JDBCConnectInfo dsInfo = jdbcConnectInfoMap.get(dsName);
                if(dsInfo == null){
                    dsInfo = new JDBCConnectInfo();
                    jdbcConnectInfoMap.put(dsName, dsInfo);
                }

                switch (dsAttr){
                    case JDBC_DRIVER:
                        try {
                            dsInfo.setDriverName(properties.getProperty(keyStr));
                        } catch (ClassNotFoundException e) {
                            throw new Exception("드라이버 클래스를 찾을 수 없습니다.", e);
                        }
                        break;
                    case JDBC_URL:
                        dsInfo.setJdbcURL(properties.getProperty(keyStr));
                        break;
                    case JDBC_USER:
                        dsInfo.setUserName(properties.getProperty(keyStr));
                        break;
                    case JDBC_PASSWD:
                        dsInfo.setPassword(properties.getProperty(keyStr));
                        break;
                }
            }
        }
        return jdbcConnectInfoMap;
    }

    public static Map<String,String> propertyToDefaultAttr(Properties properties){

        Map<String,String> defaultAttr = new HashMap<>();
        Set<Object> keys = properties.keySet();

        for( Object key : keys){
            String keyStr = (String) key;
            if(keyStr.startsWith(DEFAULTATTR_PREFIX)){
                defaultAttr.put( (keyStr.split("\\."))[2], properties.getProperty(keyStr));
            }
        }
        return defaultAttr;
    }
}
