package blackbelt.lucene.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import blackbelt.lucene.IndexManager;

/**
 * TODO remove at the integration with AspectJ
 * 
 * @author forma701
 *
 */
public class SpringUtil {
    private static IndexManager indexManager;
    static{
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
        indexManager=(IndexManager)applicationContext.getBean("indexManager");
    }
    
    public static IndexManager getBean(){
        return indexManager;
    }
}
