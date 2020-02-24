package name.yalsooni.genius.paperfactory.definition;

/**
 * 페이퍼팩토리 속성 이름 정의
 * Created by ijyoon on 2017. 4. 19..
 */
public interface PropertyName {

    String PROPERTYNAME = "PAPERFACTORY";

    String INPUT_CLASS_NAME = "PAPERFACTORY.INPUT_CLASS";
    String OUPUT_CLASS_NAME = "PAPERFACTORY.OUTPUT_CLASS";
    String FILTERLIST = "PAPERFACTORY.FILTERLIST";
    String INPUT_PROPERTY_PATH = "PAPERFACTORY.INPUT.PROPERTY_PATH";
    String OUTPUT_PROPERTY_PATH = "PAPERFACTORY.OUTPUT.PROPERTY_PATH";

    String PROPERTYNAME_FILTER = "FilterList";

    String DEFAULT_PROPERTIES_FILE_PATH = "../property/pf.properties";
    String DEFAULT_TEMPLATEREGISTER_PROPERTY_PATH = "../property/TemplateRegister.properties";
    String DEFAULT_DATATODB_PROPERTY_PATH = "../property/DataToDB.properties";
}
