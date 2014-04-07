
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mauricio chirino
 */
public class User implements Serializable {

    private String pass;

    public User() {
    }

    public User(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String Pass) {
        this.pass = Pass;
    }

    @Override
    public String toString() {
        return "{\"pass\": \""+this.pass+"\"}";
    }
}
