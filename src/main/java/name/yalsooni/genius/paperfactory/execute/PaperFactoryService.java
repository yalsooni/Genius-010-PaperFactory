package name.yalsooni.genius.paperfactory.execute;

import name.yalsooni.boothelper.util.Log;
import name.yalsooni.genius.paperfactory.definition.ErrCode;
import name.yalsooni.genius.paperfactory.definition.function.AbstractInputData;
import name.yalsooni.genius.paperfactory.definition.function.AbstractOutputData;
import name.yalsooni.genius.paperfactory.execute.help.FactoryExeHelper;
import name.yalsooni.genius.paperfactory.execute.help.FactoryInitHelper;
import name.yalsooni.genius.paperfactory.template.vo.TemplateVO;

import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

/**
 * 페이퍼팩토리
 *
 * Created by ijyoon on 2017. 4. 19..
 * Refacted by ijyoon on 2019. 11. 19..
 */
public class PaperFactoryService {

    // PF 프로퍼티 패스
    private String propertyFilePath;
    // PF 프로퍼티
    private Properties rootProperties;

    // 입풋 작업용 클래스
    private AbstractInputData inputDataProcessor;
    // 아웃풋 작업용 클래스
    private AbstractOutputData outputDataProcessor;

    // 필터 리스트 패스
    private String filterListFilePath;
    // 필터 인스턴스 맵
    private Map<String, Object> filterInstanceMap = null;

    public PaperFactoryService(String propertyFilePath) {
        this.propertyFilePath = propertyFilePath;
    }

    /**
     * 페이퍼팩토리 초기화
     * @throws Exception
     */
    public void initialize() throws Exception{

        try{
            Log.console("initialize start..");

            // 프로퍼티 속성 값 바인딩
            FactoryInitHelper.propertiesRead(this);

            // 필터목록 객체화
            FactoryInitHelper.createFilterMap(this);

            // Input, Output class -> new instance
            FactoryInitHelper.createInOutObject(this);

            // Input Output object init.
            FactoryInitHelper.initInOutObject(this);

            Log.console("initialize done..");
        }catch (Exception e){
            throw new Exception(ErrCode.PF_INIT, e);
        }

    }

    /**
     * 페이퍼 팩토리 실행
     * @throws Exception
     */
    public void execute() throws Exception{

        Log.console("Execute start..");

        // 변환 대상 탬플릿 목록 획득.
        LinkedList<Map<String, String>> targetList =  inputDataProcessor.getTargetList();
        // 탬플릿 이름을 참조할 수 있는 컬럼명 획득.
        String templateColumnName = inputDataProcessor.getTempIDColumnName();

        for(Map<String, String> row : targetList){

            String tempKey = row.get(templateColumnName);
            // 탬플릿 셋
            LinkedList<TemplateVO> templateSourceList = inputDataProcessor.getTemplate(tempKey);
            // 탬플릿 데이터 목록
            LinkedList<Map<String, String>> templateData = inputDataProcessor.getTemplateData(tempKey);

            try{
                // 탬플릿 셋 + 탬플릿 데이터 목록 = 신규 생성 및 변환
                FactoryExeHelper.templateConversion(this, templateSourceList, templateData);
            }catch (Exception e){
                Log.console(e);
                continue;
            }
        }

        // 프로세스 종료 전파
        try{
            exitProcess();
        }catch (Exception e){
            Log.console(e);
            failureProcess();
        }

        Log.console("Execute done..");
    }

    /**
     * 변환 성공 시 실행
     * @param templateSourceMap
     * @throws Exception
     */
    public void success(Map<String, String> templateSourceMap) {
        try{
            if(this.inputDataProcessor != null)
                this.inputDataProcessor.success(templateSourceMap);
        }catch(Exception e){
            Log.console(e);
        }
        try{
            if(this.outputDataProcessor != null)
                this.outputDataProcessor.success(templateSourceMap);
        }catch(Exception e){
            Log.console(e);
        }
    }

    /**
     * 변환 실패 시 실행
     * @param templateSourceMap
     * @param errMsg
     * @throws Exception
     */
    public void failure(Map<String, String> templateSourceMap, String errMsg){
        try{
            if(this.inputDataProcessor != null)
                this.inputDataProcessor.failure(templateSourceMap, errMsg);
        }catch(Exception e){
            Log.console(e);
        }
        try{
            if(this.outputDataProcessor != null)
                this.outputDataProcessor.failure(templateSourceMap, errMsg);
        }catch(Exception e){
            Log.console(e);
        }
    }

    /**
     * 프로세스 종료 시 실행
     * @throws Exception
     */
    public void exitProcess(){
        try{
            if(this.inputDataProcessor != null)
                this.inputDataProcessor.exitProcess();
        }catch(Exception e){
            Log.console(ErrCode.PF_E010, e);
        }
        try{
            if(this.outputDataProcessor != null)
                this.outputDataProcessor.exitProcess();
        }catch(Exception e){
            Log.console(ErrCode.PF_E011, e);
        }
    }

    /**
     * 프로세스 종료 시 실패 처리
     * @throws Exception
     */
    public void failureProcess() {
        try{
            if(this.inputDataProcessor != null)
                this.inputDataProcessor.failureProcess();
        }catch(Exception e){
            Log.console(e);
        }
        try{
            if(this.outputDataProcessor != null)
                this.outputDataProcessor.failureProcess();
        }catch(Exception e){
            Log.console(e);
        }
    }

    public String getPropertyFilePath() {
        return propertyFilePath;
    }

    public Properties getRootProperties() {
        return rootProperties;
    }

    public void setRootProperties(Properties rootProperties) {
        this.rootProperties = rootProperties;
    }

    public AbstractInputData getInputDataProcessor() {
        return inputDataProcessor;
    }

    public void setInputDataProcessor(AbstractInputData inputDataProcessor) {
        this.inputDataProcessor = inputDataProcessor;
    }

    public AbstractOutputData getOutputDataProcessor() {
        return outputDataProcessor;
    }

    public void setOutputDataProcessor(AbstractOutputData outputDataProcessor) {
        this.outputDataProcessor = outputDataProcessor;
    }

    public String getFilterListFilePath() {
        return filterListFilePath;
    }

    public void setFilterListFilePath(String filterListFilePath) {
        this.filterListFilePath = filterListFilePath;
    }

    public Map<String, Object> getFilterInstanceMap() {
        return filterInstanceMap;
    }

    public void setFilterInstanceMap(Map<String, Object> filterInstanceMap) {
        this.filterInstanceMap = filterInstanceMap;
    }
}
