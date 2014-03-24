/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.synergygb.billeteravirtual.notificacion.models;

import com.synergygb.billeteravirtual.notificacion.services.models.ApplicationParamsModel;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * BancaPlus REST Web Services
 * 
 * Class defining information of the serving mobile application
 * 
 * @author Synergy-GB
 * @author Javier Fernandez
 * @version 1.0
 */
@XmlRootElement
public class Application implements Serializable{
    String platform;
    String version;
    
    public Application(){
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    
    public static Application getApplicationFromParams(ApplicationParamsModel login){
        Application app = new Application();
        app.setPlatform(login.getPlatform());
        app.setVersion(login.getVersion());
        return app;
    }

}
