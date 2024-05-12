package org.softuni.mobilele.services;

public interface SeedService {
    void seedBrands();

    void seedModels();

    void seedOffers();

    void seedUsers();

    void seedUserRoles();

    default void seedAll(){
        seedUserRoles();
        seedBrands();
        seedUsers();
        seedModels();
        seedOffers();
    }
}
