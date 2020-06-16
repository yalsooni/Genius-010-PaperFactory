package name.yalsooni.genius.paperfactory.execute.help;

import name.yalsooni.boothelper.util.Log;
import name.yalsooni.genius.paperfactory.definition.ErrCode;
import name.yalsooni.genius.paperfactory.definition.Process;
import name.yalsooni.genius.paperfactory.definition.filter.PaperFactoryFilter;
import name.yalsooni.genius.paperfactory.definition.function.OutputData;
import name.yalsooni.genius.paperfactory.execute.PaperFactoryService;
import name.yalsooni.genius.paperfactory.template.vo.TemplateVO;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 페이퍼팩토리 실행 도우미
 *
 * Created by ijyoon on 2017. 4. 19..
 */
public class FactoryExeHelper {

    /**
     * 템플릿 기반의 변환 작업
     * @param service
     * @param templateSourceList  탬플릿 맵
     * @param templateData  변환될 데이터 셋
     */
    public static void templateConversion(PaperFactoryService service, LinkedList<TemplateVO> templateSourceList, LinkedList<Map<String, String>> templateData) throws Exception{

        if(templateSourceList == null){
            throw new NullPointerException(ErrCode.PF_E001);
        }

        if(templateData == null){
            throw new NullPointerException(ErrCode.PF_E002);
        }

        OutputData outputData = service.getOutputDataProcessor();

        for(Map<String, String> dataRow: templateData){
            try{
                for(TemplateVO templateSource : templateSourceList){
                    String[] newKeyName = replaceKeyName(service, templateSource.getTemplateSeq(), templateSource.getTemplateName(), dataRow);

                    ByteArrayInputStream inputStream = new ByteArrayInputStream(templateSource.getTemplateFileStr().getBytes());
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                    String newKeyNameForLog = Arrays.toString(newKeyName);

                    Log.console("["+ newKeyNameForLog+"] -- Template data replace on stream, start.");

                    if(isZipFile(templateSource.getTemplateName())){
                        replaceOnZipStream(service, templateSource.getTemplateSeq(), templateSource.getTemplateFile(), outputStream, dataRow);
                    }else{
                        replaceOnStream(service, templateSource.getTemplateSeq(), inputStream, outputStream, dataRow);
                    }

                    Log.console("["+ newKeyNameForLog+"] -- Template data replace on stream, done.");

                    Log.console("["+ newKeyNameForLog+"] -- new data output, start.");
                    outputData.writeData(newKeyName, outputStream, templateSource, dataRow);
                    Log.console("["+ newKeyNameForLog+"] -- new data output, end.");
                }

                service.success(dataRow);
            }catch (Exception e){
                Log.console(e);
                service.failure(dataRow, e.getMessage());
                continue;
            }
        }
    }

    private static boolean isZipFile(String fileName){
        if(fileName.endsWith(".zip") || fileName.endsWith(".xlsx") || fileName.endsWith(".docx") || fileName.endsWith(".pptx")){
            return true;
        }
        return false;
    }

    /**
     * template subject의 데이터 변환
     * @param key
     * @param dataRow
     * @return
     */
    private static String[] replaceKeyName(PaperFactoryService service, String tdSeq, String key, Map<String, String> dataRow) throws Exception {

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(key.getBytes());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        replaceOnStream(service, tdSeq, byteArrayInputStream, byteArrayOutputStream, dataRow);

        key = byteArrayOutputStream.toString();
        // @ 기준으로 왼쪽은 아웃풋 패스 + 하위 디렉토리, 오른쪽은 파일 이름이 됨.
        return key.split("@");
    }


    /**
     * 압축 파일 스트림 변환
     * @param templateFile
     * @param os
     * @param dataMap
     * @throws IOException
     */
    private static void replaceOnZipStream(PaperFactoryService service, String tdSeq, File templateFile, OutputStream  os, Map<String,String> dataMap) throws IOException {

        ZipInputStream zipInputStream = null;
        ZipOutputStream zipOutputStream = null;

        try{
            zipInputStream = new ZipInputStream(new FileInputStream(templateFile));
            zipOutputStream = new ZipOutputStream(os);

            ZipEntry zentry;
            ZipEntry newZentry;

            while ((zentry = zipInputStream.getNextEntry()) != null) {
                newZentry = new ZipEntry(zentry.getName());
                zipOutputStream.putNextEntry(newZentry);

                if (!zentry.isDirectory()) {
                    replaceOnStream(service, tdSeq, zipInputStream, zipOutputStream, dataMap);
                }
            }

        }catch (IOException ioe){
            throw ioe;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{ zipOutputStream.close(); } catch (Exception e){}
            try{ zipInputStream.close(); } catch (Exception e){}
        }
    }


    /**
     * 스트림 기반의 변환 작업
     * @param service
     * @param is
     * @param os
     * @param dataMap
     * @throws IOException
     */
    private static void replaceOnStream(PaperFactoryService service, String tdSeq, InputStream is, OutputStream  os, Map<String,String> dataMap) throws Exception {

        byte[] data = new byte[Process.BYTE_PACKET_SIZE];
        byte[] tempSubject = new byte[Process.LIMIT_SUBJECT_BYTE_SIZE + 2];

        int readSize;
        int dataStartIdx = 0;
        int tempSubjectIdx = 0;

        boolean scanTempSubject = false;

        while ((readSize = is.read(data)) != -1) {

            for(int idx=0; idx < readSize; idx++){

                if (scanTempSubject == false && idx == readSize -1){
                    os.write(data, dataStartIdx, readSize - dataStartIdx);
                    dataStartIdx = 0;
                }else if(scanTempSubject && data[idx] == Process.POSTFIX_MARK) {
                    tempSubject[tempSubjectIdx] = data[idx];

                    String key = new String(tempSubject, 1, tempSubjectIdx - 1);
                    String value = dataMap.get(key);

                    if(value != null){

                        PaperFactoryFilter filter = (PaperFactoryFilter) service.getFilterInstanceMap().get(key);

                        if(filter != null){
                            try {
                                value = filter.replace(tdSeq, value);
                            } catch (Exception e) {
                                throw new Exception(ErrCode.G_010_0010, e);
                            }
                        }

                        os.write(value.getBytes());
                    }else{
                        os.write(tempSubject, 0, tempSubjectIdx + 1);
                    }

                    dataStartIdx = (readSize == idx + 1) ? 0 : idx + 1;
                    tempSubjectIdx = 0;
                    scanTempSubject = false;

                }else if( scanTempSubject && tempSubjectIdx == Process.LIMIT_SUBJECT_BYTE_SIZE + 1 ){
                    tempSubject[tempSubjectIdx] = data[idx];
                    os.write(tempSubject, 0, tempSubject.length);

                    dataStartIdx = (readSize == idx + 1) ? 0 : idx + 1;

                    tempSubjectIdx = 0;
                    scanTempSubject = false;

                }else if (scanTempSubject && data[idx] == Process.PREFIX_MARK){
                    os.write(tempSubject, 0, tempSubjectIdx);

                    dataStartIdx = (readSize == idx + 1) ? 0 : idx + 1;
                    tempSubjectIdx = 0;
                    tempSubject[tempSubjectIdx++] = data[idx];

                }else if (data[idx] == Process.PREFIX_MARK){
                    os.write(data, dataStartIdx, idx-dataStartIdx);

                    scanTempSubject = true;
                    tempSubject[tempSubjectIdx++] = data[idx];

                    dataStartIdx = idx;

                }else if (scanTempSubject) {
                    tempSubject[tempSubjectIdx++] = data[idx];
                }
            }
        }

        os.flush();
    }
}
