package name.yalsooni.genius.paperfactory.input;

import name.yalsooni.genius.paperfactory.definition.EXTargetState;
import name.yalsooni.genius.paperfactory.definition.function.AbstractInputData;
import name.yalsooni.genius.paperfactory.template.vo.TemplateVO;
import name.yalsooni.genius.paperfactory.util.ExcelReader;
import name.yalsooni.genius.paperfactory.util.FileTemplateUtil;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

/**
 * Created by hello@yalsooni.name on 2019/11/19
 */
public class ExcelFileReader extends AbstractInputData {

    private final String DATAEXCEL_FILE_PATH = "INPUT.EXCEL.DATAPATH";
    private final String TEMPLATE_FILE_PATH = "INPUT.TEMPLATE.FILEPATH";

    private final String LIST_SHEETNAME = "TemplateList";

    private ExcelReader eReader;
    private String templateRootDirectoryPath;

    private static final String TEMPLATE_LIST = "TemplateID";
    private static final String IS_TARGET_COULMN_NM = "TARGET";

    public ExcelFileReader() {
        super(TEMPLATE_LIST);
    }

    @Override
    public void initialize(Properties properties) throws IOException {
        String excelFilePath = properties.getProperty(DATAEXCEL_FILE_PATH);
        this.eReader = new ExcelReader(excelFilePath, "");
        this.templateRootDirectoryPath = properties.getProperty(TEMPLATE_FILE_PATH);
    }

    /**
     * TARGET 컬럼이 T인 것 만 추출.
     * @param list
     */
    private void filterTarget(LinkedList<Map<String, String>> list){
        int listIdx = list.size();
        for ( int idx = 0; idx < listIdx; idx++){
            Map<String, String> row = list.get(idx);
            if(!row.get(IS_TARGET_COULMN_NM).equals(EXTargetState.TARGET)){
                list.remove(idx);
                idx -= 1;
                listIdx -= 1;
            }
        }
    }

    @Override
    public LinkedList<Map<String, String>> getTargetList() throws Exception {
        LinkedList<Map<String, String>>  targetMap = eReader.getDataMap(LIST_SHEETNAME, 0, 1, 0);
        filterTarget(targetMap);
        return targetMap;
    }

    @Override
    public LinkedList<Map<String, String>> getTemplateData(String tempID) throws Exception {
        LinkedList<Map<String, String>> templateData = eReader.getDataMap( tempID,0,1,0);
        filterTarget(templateData);
        return templateData;
    }

    @Override
    public LinkedList<TemplateVO> getTemplate(String tempID) throws Exception {
        return FileTemplateUtil.templateData(this.templateRootDirectoryPath, tempID);
    }

    @Override
    public void success(Map<String, String> templateSourceMap) throws Exception {
    }

    @Override
    public void failure(Map<String, String> templateSourceMap, String errMsg) throws Exception {
    }

    @Override
    public void exitProcess() throws Exception {
        this.eReader.close();
    }

    @Override
    public void failureProcess() throws Exception {
    }
}
