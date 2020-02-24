package name.yalsooni.genius.paperfactory.execute.help;


import name.yalsooni.boothelper.util.ClassUtil;
import name.yalsooni.boothelper.util.reader.PropertyReader;
import name.yalsooni.genius.paperfactory.definition.ErrCode;
import name.yalsooni.genius.paperfactory.definition.PropertyName;
import name.yalsooni.genius.paperfactory.definition.function.AbstractInputData;
import name.yalsooni.genius.paperfactory.definition.function.AbstractOutputData;
import name.yalsooni.genius.paperfactory.execute.PaperFactoryService;

import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * 페이퍼팩토리 초기화 도우미
 *
 * Created by ijyoon on 2017. 4. 19..
 * Refacted by ijyoon on 2019. 11. 19..
 */
public class FactoryInitHelper {

    private final static ClassUtil CLASSUTIL = new ClassUtil();

    /**
     * 프로퍼티 파일을 읽어 해당 속성값을 바인딩한다.
     *
     * @param service 페이퍼팩토리 객체
     * @throws Exception
     */
    public static void propertiesRead(PaperFactoryService service) throws Exception{

        // 프로퍼티 파일 읽기
        PropertyReader pr = new PropertyReader();
        try{
            Properties rootProperties = pr.read(PropertyName.PROPERTYNAME, service.getPropertyFilePath());
            service.setRootProperties(rootProperties);
            service.setFilterListFilePath(rootProperties.getProperty(PropertyName.FILTERLIST));
        } catch (FileNotFoundException fe) {
            throw new Exception(ErrCode.PF_I001, fe);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 필터 속성 파일을 읽어 필터객체 맵을 바인딩한다.
     *
     * @param service 페이퍼팩토리 객체
     * @throws Exception
     */
    public static void createFilterMap(PaperFactoryService service) throws Exception {

        String filterListFilePath = service.getFilterListFilePath();
        if( filterListFilePath != null){
            PropertyReader pr = new PropertyReader();
            try{
                Properties filterList = pr.read(PropertyName.PROPERTYNAME_FILTER, filterListFilePath);
                service.setFilterInstanceMap(CLASSUTIL.getInstanceMap(filterList));
            } catch (FileNotFoundException fe) {
                throw new Exception(ErrCode.PF_I002, fe);
            } catch (Exception e) {
                throw e;
            }
        }
    }

    /**
     * input process, output process obejct create.
     *
     * @param service
     * @throws Exception
     */
    public static void createInOutObject(PaperFactoryService service) throws Exception {

        Properties rootProperties = service.getRootProperties();
        String inputClassName = rootProperties.getProperty(PropertyName.INPUT_CLASS_NAME);
        String outputClassName = rootProperties.getProperty(PropertyName.OUPUT_CLASS_NAME);

        try {
            AbstractInputData inputData = CLASSUTIL.newInstance(inputClassName);
            inputData.setProperties(rootProperties.getProperty(PropertyName.INPUT_PROPERTY_PATH));
            service.setInputDataProcessor(inputData);

            AbstractOutputData outputData = CLASSUTIL.newInstance(outputClassName);
            outputData.setProperties(rootProperties.getProperty(PropertyName.OUTPUT_PROPERTY_PATH));
            service.setOutputDataProcessor(outputData);
        } catch (ClassNotFoundException e) {
            throw new Exception(ErrCode.PF_I003, e);
        } catch (InstantiationException e) {
            throw e;
        } catch (IllegalAccessException e) {
            throw e;
        }
    }

    /**
     * input, output obejct init.
     *
     * @param service
     * @throws Exception
     */
    public static void initInOutObject(PaperFactoryService service) throws Exception {
        service.getInputDataProcessor().initialize();
        service.getOutputDataProcessor().initialize();
    }
}
