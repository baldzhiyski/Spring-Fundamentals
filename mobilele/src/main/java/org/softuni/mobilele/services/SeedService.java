package org.softuni.mobilele.services;

import java.io.FileNotFoundException;

public interface SeedService {
    void seedBrands() throws FileNotFoundException;

    void seedModels() throws FileNotFoundException;

    void seedOffers() throws FileNotFoundException;

    void seedUsers() throws FileNotFoundException;

    void seedUserRoles() throws FileNotFoundException;

    default void seedAll() throws FileNotFoundException {
        seedUserRoles();
        seedBrands();
        seedUsers();
        seedModels();
        seedOffers();
    }
}
