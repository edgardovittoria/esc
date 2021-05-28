package it.univaq.esc.utility;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Classe di servizio utilizzata per riprendere all'occorrenza un bean dal container.
 * @author esc
 *
 */
@Service
public class BeanUtil implements ApplicationContextAware {

    private static ApplicationContext context;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
    
    /**
     * Metodo statico per recuperare all'occorrenza un bean dal container in base al tipo.
     * @param <T>
     * @param beanClass classe del bean che vogliamo recuperare. 
     * @return un bean contenuto nel container della classe passata come parametro.
     */
    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
    
    public static Object getBeanPerNome(String nomeBean) {
        return context.getBean(nomeBean);
    }




}