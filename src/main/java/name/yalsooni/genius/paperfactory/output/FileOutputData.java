package name.yalsooni.genius.paperfactory.output;

import name.yalsooni.boothelper.util.file.FileSupport;
import name.yalsooni.genius.paperfactory.definition.function.AbstractOutputData;
import name.yalsooni.genius.paperfactory.template.vo.TemplateVO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Created by hello@yalsooni.name on 2019/11/22
 */
public class FileOutputData extends AbstractOutputData {

    private final String OUTPUT_TEMPLATE_FILE_PATH = "OUTPUT.TEMPLATE.FILEPATH";

    private final FileSupport FILE_SUPPORT = new FileSupport();
    private String outputFilePath;

    @Override
    public void initialize(Properties properties) throws Exception {
        this.outputFilePath = properties.getProperty(OUTPUT_TEMPLATE_FILE_PATH);
    }

    private String splitPath(String path){
        String[] splitPath = path.split("#");
        StringBuilder newPath = new StringBuilder();
        for(String unit : splitPath){
            newPath.append("/").append(unit);
        }

        return newPath.toString();
    }

    @Override
    public void writeData(String[] pathAndNewKeyName, ByteArrayOutputStream convertedData, TemplateVO templateSource, Map<String, String> dataRow) throws Exception {
        String outputPath;
        String outputName;

        if(pathAndNewKeyName.length > 1){
            outputPath = this.outputFilePath + "/" + splitPath(pathAndNewKeyName[0]);
            outputName = pathAndNewKeyName[1];
        }else{
            outputPath = this.outputFilePath;
            outputName = pathAndNewKeyName[0];
        }

        FILE_SUPPORT.mkdir(outputPath);

        byte[] output = convertedData.toByteArray();

        File targetFile = new File(outputPath + "/" + outputName);
        FileOutputStream fos = new FileOutputStream(targetFile);

        fos.write(output);
        fos.flush();
        fos.close();

//        FILE_SUPPORT.dataToFile(new File(outputPath + "/" + outputName), convertedData);
    }

    @Override
    public void success(Map<String, String> templateSourceMap) throws Exception {

    }

    @Override
    public void failure(Map<String, String> templateSourceMap, String errMsg) throws Exception {

    }

    @Override
    public void exitProcess() throws Exception {

    }

    @Override
    public void failureProcess() throws Exception {

    }
}
