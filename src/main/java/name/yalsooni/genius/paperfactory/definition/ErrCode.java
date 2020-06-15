package name.yalsooni.genius.paperfactory.definition;

/**
 * Created by ijyoon on 2017. 4. 19..
 */
public interface ErrCode {

    String PF_INIT = "[PF-I000] initialization error.";
    String PF_I001 = "[PF-I001] not found property files.";
    String PF_I002 = "[PF-I002] not found filter property files.";
    String PF_I003 = "[PF-I003] in or out class not found.";

    String PF_E001 = "[PF-E001] not found template.";
    String PF_E002 = "[PF-E002] not found template data.";
    String PF_E010 = "[PF-E010] input processor exit error.";
    String PF_E011 = "[PF-E011] output processor exit error.";



    String G_010_0001 = "[G-010-0001] 기본 속성 파일을 찾을 수 없습니다.";
    String G_010_0002 = "[G-010-0002] 기본 속성 파일을 읽는 중 예외 발생.";

    String G_010_0003 = "[G-010-0003] 필터 목록 속성 파일을 찾을 수 없습니다.";
    String G_010_0004 = "[G-010-0004] 필터 목록 속성 파일을 읽는 중 예외 발생.";

    String G_010_0005 = "[G-010-0005] 엑셀 파일을 읽을 수 없습니다.";

    String G_010_0006 = "[G-010-0006] 해당 경로는 디렉토리가 아닙니다.";

    String G_010_0007 = "[G-010-0007] 파일 이름 변경 중 필터 수행 오류 입니다.";
    String G_010_0008 = "[G-010-0008] 변환 오류 입니다.";

    String G_010_0009 = "[G-010-0009] 새 파일 생성 중 예외 발생.";
    String G_010_0010 = "[G-010-0010] 데이터 변환 중 필터 수행 오류 입니다.";

    String G_010_0011 = "[G-010-0011] temp id의 디렉토리가 존재하지 않습니다.";
}


