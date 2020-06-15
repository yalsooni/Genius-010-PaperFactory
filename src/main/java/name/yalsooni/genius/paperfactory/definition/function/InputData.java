package name.yalsooni.genius.paperfactory.definition.function;

import name.yalsooni.genius.paperfactory.template.vo.TemplateVO;

import java.util.LinkedList;
import java.util.Map;

/**
 * 템플릿 대상 정보, 템플릿 정보 인터페이스
 * Created by hello@yalsooni.name on 2019/11/13
 */
public interface InputData{

    /**
     * 탬플릿명을 확인할 수 있는 컬럼명
     * @return
     */
    String getTempIDColumnName();

    /**
     * target list
     * 타입별 그룹, 대상 목록
     *
     * @return
     * @throws Exception
     */
    LinkedList<Map<String, String>> getTargetList() throws Exception;

    /**
     * template data
     * 변환 대상 데이터
     *
     * @return
     * @throws Exception
     */
    LinkedList<Map<String, String>> getTemplateData(String tempID) throws Exception;

    /**
     * template file - 다수 파일
     *
     * @return
     * @throws Exception
     */
    LinkedList<TemplateVO> getTemplate(String tempID) throws Exception;

}
