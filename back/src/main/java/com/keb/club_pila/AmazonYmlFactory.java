package com.keb.club_pila;


import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class AmazonYmlFactory implements PropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource( String name, EncodedResource resource) throws IOException {
        Properties propertiesFromYml=loadYmlIntoProperties(resource);
        String sourceName=name!=null? name: resource.getResource().getFilename();
        return new PropertiesPropertySource(sourceName, propertiesFromYml);
    }

    private Properties loadYmlIntoProperties(EncodedResource resource) throws FileNotFoundException {try{
        YamlPropertiesFactoryBean factory=new YamlPropertiesFactoryBean();
        factory.setResources(resource.getResource());
        factory.afterPropertiesSet();
        return factory.getObject();
    }catch(IllegalStateException e){
     Throwable cause=e.getCause();
     if( cause instanceof FileNotFoundException)
         throw (FileNotFoundException) e.getCause();
     throw e;
    }

    }

}
