package name.yalsooni.genius.paperfactory.template.vo;

import java.io.File;

/**
 * <pre>
 * name.yalsooni.genius.paperfactory.template.vo 
 *    |_ TemplateVO.java
 * 
 * </pre>
 * @desc    : 템플릿 리스트 VO 클래스
 * @date    : 2019. 11. 11.
 * @version : 1.0
 * @author  : ChangMin
 */
public class TemplateVO {
	
	private String templateSeq;
	private String templateName;
	private File templateFile;
	private String templateFileStr;
	private String templateComment;

	public String getTemplateSeq() {
		return templateSeq;
	}

	public void setTemplateSeq(String templateSeq) {
		this.templateSeq = templateSeq;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public File getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(File templateFile) {
		this.templateFile = templateFile;
	}

	public String getTemplateFileStr() {
		return templateFileStr;
	}

	public void setTemplateFileStr(String templateFileStr) {
		this.templateFileStr = templateFileStr;
	}

	public String getTemplateComment() {
		return templateComment;
	}

	public void setTemplateComment(String templateComment) {
		this.templateComment = templateComment;
	}
}
