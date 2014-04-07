
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mauriciochirino
 */
@XmlRootElement
public class Instrument implements Serializable{
    
    private String id,ExternalId;

    public Instrument() {
    }

    public Instrument(String id, String ExternalId) {
        this.id = id;
        this.ExternalId = ExternalId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExternalId() {
        return ExternalId;
    }

    public void setExternalId(String ExternalId) {
        this.ExternalId = ExternalId;
    }
    
    public String toString(){
        return "{\"id\": \""+this.id+"\", ref\": \""+this.ExternalId+"\"}";
    }
    
}
