
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
public class Wallet implements Serializable{

    private String flag;

    public Wallet() {
        flag = "1";
    }

    public Wallet(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    public String toString(){
        return "{\"flag\": \""+this.flag+"\"}";
    }

}
