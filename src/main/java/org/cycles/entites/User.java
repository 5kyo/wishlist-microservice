package org.cycles.entites;

import javax.persistence.*;

@Entity
@Cacheable
public class User{

    @Id
    private Long userId;
    
    private String userName;
    private String userSurname;
    private String userNickname;
    private String userEmail;
    private String userPassword;
    private String userPhoneNumber;
    private String userRole;
    private int userActive;
    public User(){

    }

    public User(Long userId, String userName, String userSurname, String userNickname, String userEmail, String userPassword, String userPhoneNumber, String userRole, int userActive) {
        this.userId = userId;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userNickname = userNickname;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userPhoneNumber = userPhoneNumber;
        this.userRole = userRole;
        this.userActive = userActive;
    }
    
    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return this.userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserNickname() {
        return this.userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return this.userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhoneNumber() {
        return this.userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserRole() {
        return this.userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public int getUserActive() {
        return this.userActive;
    }

    public void setUserActive(int userActive) {
        this.userActive = userActive;
    }

    // @ManyToMany(fetch = FetchType.EAGER)
    // @JoinTable(
    //         name = "wishlist",
    //         joinColumns = { @JoinColumn(name = "user_id") },
    //         inverseJoinColumns = { @JoinColumn(name = "product_id") }
    // )

    // private Set<Product> products;

    // public Set<Product> getProducts() {
    //     return this.products;
    // }

    // public void setProducts(Product product) {
    //     if(this.products == null){
    //         this.products = new HashSet<>();
    //     }

    //     this.products.add(product);
    // }
}
