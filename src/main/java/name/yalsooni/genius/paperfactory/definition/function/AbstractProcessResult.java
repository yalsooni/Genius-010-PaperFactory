package name.yalsooni.genius.paperfactory.definition.function;

import name.yalsooni.boothelper.util.reader.PropertyReader;

import java.util.Properties;

/**
 * ProcessResult 추상화 클래스
 * Created by hello@yalsooni.name on 2019/12/03
 */
public abstract class AbstractProcessResult implements ProcessResult {

    private final String DEFAULT_PREFIX_CONFIG_PATH = "../property/";

    private String propertyPath;
    private Properties properties;

    public void setProperties(String propertyPath) {
        this.propertyPath = propertyPath;
    }

    public String getPropertyPath() {
        return this.propertyPath != null ? this.propertyPath : DEFAULT_PREFIX_CONFIG_PATH.concat(this.getClass().getSimpleName()).concat(".properties");
    }

    @Override
    public void initialize() throws Exception {
        PropertyReader pr = new PropertyReader();
        this.properties = pr.read(this.getClass().getSimpleName(), getPropertyPath());
        initialize(this.properties);
    }

    public abstract void initialize(Properties properties) throws Exception;
}
