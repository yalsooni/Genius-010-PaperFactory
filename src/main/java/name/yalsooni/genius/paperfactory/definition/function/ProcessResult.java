package name.yalsooni.genius.paperfactory.definition.function;

import java.util.Map;

/**
 * 트랙젠션 관리를 위한 각각의 이벤트 인터페이스
 * Created by hello@yalsooni.name on 2019/11/13
 */
public interface ProcessResult {


    /**
     * 프로세스 초기화
     * @throws Exception
     */
    void initialize() throws Exception;

    /**
     * 프로세스 성공
     * @param templateSourceMap
     * @throws Exception
     */
    void success(Map<String, String> templateSourceMap) throws Exception;

    /**
     * 프로세스 실패
     * @param templateSourceMap
     * @param errMsg
     * @throws Exception
     */
    void failure(Map<String, String> templateSourceMap, String errMsg) throws Exception;

    /**
     * 프로세스 종료 처리
     * @throws Exception
     */
    void exitProcess() throws Exception;

    /**
     * 프로세스 종료시(exitProcess method) 실패 처리
     * @throws Exception
     */
    void failureProcess() throws Exception;
}
