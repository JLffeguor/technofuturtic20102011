package blackbelt.bbtag;
/** 
 * @author Timmermans Gaetan, Alia Faton
 */
public class BbTagConvertor {
	
	public static final char TAG_BEGIN_CHAR='[';
	public static final char TAG_END_CHAR=']';
	public static final String[] SUPPORTED_LANGUAGES={
		"JAVA","XML","JSP","MARKUP","RUBY","JAVA NUM","XML NUM","JSP NUM","MARKUP NUM"
	};
	
	/** Replace old blackbelt_TAG with new blackbelt_TAG
	 * Example old: [CODE:JAVA NUM] ... [/CODE:JAVA NUM]
	 * Example new: [CODE lang="Java" num="true"] ... [/CODE]
	 * 
	 * This tries to fix malformed oldText (i.e. double open bracket [[CODE])
	 * 
	 * */
	public static String newFormat(String oldText){
		StringBuffer result=new StringBuffer();
		
		int beginIndex=0,currantTagOpener;
		
		//begin loop
		while(beginIndex< oldText.length()){
			
			//currantTagOpener = index of next begin bracket
			currantTagOpener=oldText.indexOf(TAG_BEGIN_CHAR, beginIndex);
			if(currantTagOpener==-1){//cannot find bracket
				result.append(oldText.substring(beginIndex, oldText.length()));
				break;
			}
			
			//currantTagCloser = index of next end bracket
			int currentTagCloser=oldText.indexOf(TAG_END_CHAR, currantTagOpener);
			if(currentTagCloser==-1){//cannot find bracket
				result.append(oldText.substring(beginIndex, oldText.length()));
				break;
			}
			//we found bracket
			result.append(oldText.substring(beginIndex, currantTagOpener));
			String currentTag=oldText.substring(currantTagOpener,currentTagCloser+1);
			//we have a complete tag
			boolean found=false;
			
			//we check if there is bracket in bracket(ex: [ [Code ... ])
			int index=currentTag.lastIndexOf(TAG_BEGIN_CHAR,currentTag.length());
			if (index > 0) {
				result.append(currentTag.substring(0, index));
				currentTag=currentTag.substring(index,currentTag.length());
			}
			
			String currentTagNoSpace = currentTag.replaceAll(" ", "");
			
			//replace old tag with new tag
			for (String language : SUPPORTED_LANGUAGES) {
				String languageNoSpace = language.replaceAll(" ", "");
				
				if (currentTagNoSpace.equalsIgnoreCase("[CODE:"+ languageNoSpace + "]")) {
					String[] tagType = language.split(" ");
					if (tagType.length == 1){
						result.append("[CODE lang=\"" + language + "\"]");
					}else{
						result.append("[CODE lang=\"" + tagType[0]+ "\" num=\"true\"]");
					}
					found = true;
					break;
				} else if (currentTagNoSpace.equalsIgnoreCase("[/CODE:"+ languageNoSpace + "]")) {
					result.append("[/CODE]");
					found = true;
					break;
				}
			}
			//we cannot match tag to SUPPORTED_LANGUAGES tags 
			if(!found){
				result.append(currentTag);
			}
			//beginIndex is incremented at the end+1 end_tag 
			beginIndex=currentTagCloser+1;
		}//end loop
		
		return result.toString();
	}
}
