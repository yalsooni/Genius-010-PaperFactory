package name.yalsooni.genius.paperfactory.definition.filter;

/**
 * 사용자 정의 필터 인터페이스
 * Created by ijyoon on 2017. 4. 22..
 */
public interface PaperFactoryFilter {

    /**
     * 사용자 정의 필터.
     * @param tdSeq
     * @param data
     * @return
     * @throws Exception
     */
    String replace(String tdSeq, String data) throws Exception;
}
