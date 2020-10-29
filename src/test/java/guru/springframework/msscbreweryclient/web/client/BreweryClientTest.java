package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Abid Hussain
 * @created on 28/10/2020 4:38 PM
 */
@SpringBootTest
class BreweryClientTest {

    @Autowired
    BreweryClient breweryClient;

    @Test
    void getBeerByIdTest() {
        BeerDto beerDto = breweryClient.getBeerById(UUID.randomUUID());
        assertNotNull(beerDto);
    }

    @Test
    void saveNewBeerTest() {
        BeerDto beerDto = BeerDto.builder().beerName("New Beer").build();
        URI uri = breweryClient.saveNewBeer(beerDto);

        assertNotNull(uri);
    }

    @Test
    void updateBeerTest() {
        BeerDto beerDto = BeerDto.builder().beerName("New Beer").build();
        breweryClient.updateBeer(UUID.randomUUID(), beerDto);
    }

    @Test
    void deleteBeerTest() {
        breweryClient.deleteBeer(UUID.randomUUID());
    }

    @Test
    void getCustomerTest() {
        CustomerDto customerDto = breweryClient.getCustomer(UUID.randomUUID());
        assertNotNull(customerDto);
    }

    @Test
    void saveCustomerTest() {
        CustomerDto customerDto = CustomerDto.builder().name("Customer 1").build();
        URI uri = breweryClient.saveCustomer(customerDto);
        assertNotNull(uri);
    }

    @Test
    void updateCustomerTest() {
        CustomerDto customerDto = CustomerDto.builder().name("Customer 1").build();
        breweryClient.updateCustomer(UUID.randomUUID(), customerDto);
    }

    @Test
    void deleteCustomerTest() {
        breweryClient.deleteCustomer(UUID.randomUUID());
    }
}