package name.yalsooni.genius.paperfactory.definition.function;

/**
 * Created by hello@yalsooni.name on 2019/11/19
 */
public abstract class AbstractInputData extends AbstractProcessResult implements InputData {

    private String tempIDColumnName;

    public AbstractInputData(String tempIDColumnName) {
        this.tempIDColumnName = tempIDColumnName;
    }

    @Override
    public String getTempIDColumnName() {
        return tempIDColumnName;
    }
}

