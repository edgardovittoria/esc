package it.univaq.esc.mappingObjectsViewController;

import java.util.HashMap;

import org.springframework.stereotype.Component;


@Component
public interface IFormPrenotabile {
    
    public HashMap<String, Object> getValoriForm();
}