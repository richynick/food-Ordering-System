package com.richard.onlinerestuarant.service;

import com.richard.onlinerestuarant.dto.RestaurantDto;
import com.richard.onlinerestuarant.model.Address;
import com.richard.onlinerestuarant.model.Restaurant;
import com.richard.onlinerestuarant.model.User;
import com.richard.onlinerestuarant.repository.AddressRepository;
import com.richard.onlinerestuarant.repository.RestaurantRepository;
import com.richard.onlinerestuarant.repository.UserRepository;
import com.richard.onlinerestuarant.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address = addressRepository.save(req.getAddress());
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setName(req.getName());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setOpeningHours(restaurant.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        if(restaurant.getCuisineType()!=null){
            restaurant.setCuisineType(updateRestaurant.getCuisineType());
        }
        if(restaurant.getDescription()!= null){
            restaurant.setDescription(updateRestaurant.getDescription());
        }if(restaurant.getName()!= null){
            restaurant.setName(updateRestaurant.getName());
        }
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opti = restaurantRepository.findById(id);

        if(opti.isEmpty()){
            throw new Exception("restaurant not found with id"+id);
        }
        return opti.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if(restaurant==null){
            throw new Exception("restaurant not found with owner id"+userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setImages(restaurant.getImages());
        restaurantDto.setTitle(restaurant.getName());
        restaurantDto.setId(restaurantId);

        boolean isFavorited = false;
        List<RestaurantDto> favorites = user.getFavorites();
        for(RestaurantDto favorite: favorites) {
            if(favorite.getId().equals(restaurantId)){
                isFavorited = true;
                break;
            }
        }
        if(isFavorited) {
            favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
        } else {
            favorites.add(restaurantDto);
        }

//        if(user.getFavorites().contains(restaurantDto)){
//            user.getFavorites().remove(restaurantDto);
//        }
//        else user.getFavorites().add(restaurantDto);

        userRepository.save(user);
        return restaurantDto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
