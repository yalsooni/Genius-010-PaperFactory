package name.yalsooni.genius.paperfactory.definition.function;

import name.yalsooni.genius.paperfactory.template.vo.TemplateVO;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * 생성된 템플릿 출력
 * Created by hello@yalsooni.name on 2019/11/13
 */
public interface OutputData{

    void initialize() throws Exception;

    void writeData(String[] pathAndNewKeyName, ByteArrayOutputStream convertedData, TemplateVO templateSource, Map<String, String> dataRow) throws Exception;

}
