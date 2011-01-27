package ctfTesting;


public class Main {

    public static void main(String[] args){
        
//        BlackBeltTagHandler blackBeltTagHandler = null;
        BlackBeltTagHandlerCourseOnBrowser test = new BlackBeltTagHandlerCourseOnBrowser();
        BlackBeltTagParser blackBeltParse = new BlackBeltTagParser(test, new Course());
    
        System.out.println(blackBeltParse.parse());
    }
}