package name.yalsooni.genius.paperfactory.util;

import name.yalsooni.boothelper.util.file.FileSupport;
import name.yalsooni.genius.paperfactory.definition.ErrCode;
import name.yalsooni.genius.paperfactory.template.vo.TemplateVO;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by hello@yalsooni.name on 2019/11/22
 */
public class FileTemplateUtil {

    private static final FileSupport FILE_SUPPORT = new FileSupport();

    /**
     * temp 디렉토리의 파일 목록을 반환한다.
     *
     * @param tempDirectoryPath temp 디렉토리 경로
     * @return temp 디렉토리의 파일 목록
     * @throws Exception
     */
    public static LinkedList<TemplateVO> templateData(String tempDirectoryPath, String tempID) throws Exception {

        File tempDirectoryRoot = new File(tempDirectoryPath);
        if (!tempDirectoryRoot.isDirectory()) throw new Exception("this is not directory : " + tempDirectoryPath);

        LinkedList<TemplateVO> templateList = new LinkedList<>();

        File[] templates = tempDirectoryRoot.listFiles();
        for(File targetTemplate : templates){
            if(targetTemplate.isDirectory() && targetTemplate.getName().equals(tempID)){

                File[] templateFiles = targetTemplate.listFiles();
                for(int idx = 0 ; idx < templateFiles.length; idx++){
                    TemplateVO vo = new TemplateVO();
                    vo.setTemplateSeq(String.valueOf(idx+1));
                    vo.setTemplateName(templateFiles[idx].getName());
                    vo.setTemplateFile(templateFiles[idx]);
                    vo.setTemplateFileStr(fileToData(templateFiles[idx]));
                    templateList.add(vo);
                }
                break;
            }
        }
        return templateList;
    }

    private static String fileToData(File file) throws IOException {
        FileInputStream fis = null;
        ByteArrayOutputStream bos;
        try{
            byte[] buffer = new byte[2048];
            int readSize = 0;

            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();

            while ( (readSize = fis.read(buffer)) != -1){
                bos.write(buffer, 0, readSize);
            }
            
        }catch (IOException io){
            throw io;
        }finally {
            if(fis != null){
                fis.close();    
            }
        }

        return bos.toString();
    }
}