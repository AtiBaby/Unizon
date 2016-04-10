/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package check;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MyBean {

    // Properties ---------------------------------------------------------------------------------

    private Boolean agree;

    // Actions ------------------------------------------------------------------------------------

    public void submit() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You have to accept the Terms and Conditions and Privacy Policy!"));
    }

    // Getters ------------------------------------------------------------------------------------

    public Boolean getAgree() {
        return agree;
    }

    // Setters ------------------------------------------------------------------------------------

    public void setAgree(Boolean agree) {
        this.agree = agree;
    }

}