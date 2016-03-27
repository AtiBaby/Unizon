/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf.Unizon.controller;

import hu.unideb.inf.Unizon.facade.AddressFacade;
import hu.unideb.inf.Unizon.facade.AddressesOfUserFacade;
import hu.unideb.inf.Unizon.facade.PhoneNumberFacade;
import hu.unideb.inf.Unizon.facade.UserFacade;
import hu.unideb.inf.Unizon.model.Address;
import hu.unideb.inf.Unizon.model.AddressesOfUser;
import hu.unideb.inf.Unizon.model.AddressesOfUserPK;
import hu.unideb.inf.Unizon.model.PhoneNumber;
import hu.unideb.inf.Unizon.model.User;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import password.Password;

/**
 *
 * @author Czuczi
 */
@Named(value = "registrationController")
@ViewScoped
public class RegistrationController implements Serializable {

    @EJB
    private UserFacade userFacade;
    @EJB
    private AddressFacade addressFacade;
    @EJB
    private AddressesOfUserFacade addressesOfUserFacade;
    @EJB
    private PhoneNumberFacade phoneNumberFacade;

    private String userName;
    private String password;
    private String email;
    private String name;
    private String phoneNumber;

    private String zip;
    private String country;
    private String city;
    private String street;
    private Integer streetNumber;
    private Integer floor;
    private Integer door;

    private void nullProps() {
        userName = null;
        password = null;
        email = null;
        name = null;
        phoneNumber = null;
        
        zip = null;
        country = null;
        city = null;
        street = null;
        streetNumber = null;
        floor = null;
        door = null;
    }
    
    public void register() {
        if(userFacade.findByUserName(userName) != null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Username already exists!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        
        if (phoneNumberFacade.findByPhoneNumber(phoneNumber) != null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING", "Phone number already exists!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        
        PhoneNumber pn = new PhoneNumber();
        pn.setPhoneNumber(phoneNumber);
        
        phoneNumberFacade.create(pn);

        List<Address> addresses = addressFacade.findAll();
        Address address = new Address();
        address.setCity(city);
        address.setZip(zip);
        address.setCountry(country);
        address.setStreet(street);
        address.setStrNumber(streetNumber);
        address.setFloor(floor);
        address.setDoor(door);

        boolean existingAddress = false;
        for (Address a : addresses) {
            if (a.equals(address)) {
                address = a;
                existingAddress = true;
                break;
            }
        }
        if (!existingAddress) {
            addressFacade.create(address);
        }
        
        User user = new User();
        user.setAddress(address);
        user.setEMail(email);
        try {
            user.setEncryptedPassword(Password.getSaltedHash(password));
        } catch (Exception ex) {
            Logger.getLogger(RegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        user.setName(name);
        user.setPhoneNumber(pn);
        user.setRegistrationDate(new Date());
        user.setUsername(userName);
        userFacade.create(user);
        
        AddressesOfUser addressesOfUser = new AddressesOfUser();
        addressesOfUser.setAddress(address);
        addressesOfUser.setUser(user);
        
        AddressesOfUserPK addressesOfUserPK = new AddressesOfUserPK();
        addressesOfUserPK.setAddressId(address.getAddressId());
        addressesOfUserPK.setUserId(user.getUserId());
        
        addressesOfUser.setId(addressesOfUserPK);
        addressesOfUserFacade.create(addressesOfUser);
        nullProps();
    }

    /**
     * Creates a new instance of RegistrationController
     */
    public RegistrationController() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getDoor() {
        return door;
    }

    public void setDoor(Integer door) {
        this.door = door;
    }

}
