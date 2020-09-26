package kz.iitu.pharm.basketservice.controller;

import kz.iitu.pharm.basketservice.entity.Basket;
import kz.iitu.pharm.basketservice.entity.Drug;
import kz.iitu.pharm.basketservice.entity.User;
import kz.iitu.pharm.basketservice.repository.BasketRepository;
import kz.iitu.pharm.basketservice.repository.UserRepository;
import kz.iitu.pharm.basketservice.service.UserService;
import kz.iitu.pharm.basketservice.service.impl.BasketServiceImpl;
import kz.iitu.pharm.basketservice.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping("/baskets")
@Api(value = "Basket Controller class", description = "This class is used for accessing, editing and deleting basket details")
public class BasketController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private BasketServiceImpl basketService;

    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Basket> findBasketById(@PathVariable Long id) {
        return basketRepository.findById(id);
    }

    @GetMapping("/product/products/customer/{customerId}")
    public List<Drug> requestAllProducts(@PathVariable Long customerId) {
        ResponseEntity<List<Drug>> responseEnties = null;
        List<Drug> response;
        Optional<User> user = userService.getUserbyId(customerId);
        if (user.isPresent()) {
            responseEnties = new RestTemplate().exchange(
                    "http://localhost:8080/drugs/",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Drug>>(){});
        }
        response = responseEnties.getBody();
        return response;
    }

    @GetMapping("/product/{productId}/customer/{customerId}")
    public Drug requestProductByProductId(@PathVariable Long productId,
                                                 @PathVariable Long customerId) {
        Drug response = new Drug();
        ResponseEntity<Drug> responseEntity;

        Optional<User> user = userService.getUserbyId(customerId);
        if (user.isPresent()) {
            Map<String, Long> uriVariables = new HashMap<>();
            uriVariables.put("productId", productId);

            responseEntity = new RestTemplate().getForEntity(
                    "http://localhost:8080/drugs/id/{productId}",
                    Drug.class,
                    uriVariables);
            response = responseEntity.getBody();
        }

        return new Drug(response.getId(),
                response.getName(),
                response.getName(),
                response.getPrice());
    }
    //    @ApiOperation(value = "Method for adding basket to user")
//    @PatchMapping("/add/")
//    public void addBasketToUser(@RequestParam("userId") Long userId, @RequestParam("basketId") Long basketId){
//        if(basketService.addBasketToUser(userId,basketId)){
//            System.out.println("Basket added to " + userId);
//        }
//        else{
//            System.out.println("basket is already owned");
//        }
//    }
//    @GetMapping("/shoppingCart")
//    public ModelAndView shoppingCart() {
//        ModelAndView modelAndView = new ModelAndView("/basket");
//        modelAndView.addObject("drugs", basketService.getDrugsInBasket());
//        modelAndView.addObject("total", basketService.getTotal().toString());
//        return modelAndView;
//    }
//    @GetMapping("/shoppingCart/addProduct/{productId}")
//    public ModelAndView addProductToCart(@PathVariable("productId") Long productId) {
//        drugService.findById(productId).ifPresent(basketService::addDrug);
//        return shoppingCart();
//    }
//    @GetMapping("/shoppingCart/removeProduct/{productId}")
//    public ModelAndView removeProductFromCart(@PathVariable("productId") Long productId) {
//        drugService.findById(productId).ifPresent(basketService::removeDrug);
//        return shoppingCart();
//    }
//    @GetMapping("/shoppingCart/checkout")
//    public ModelAndView checkout() {
//        basketService.getTotal();
//        return shoppingCart();
//    }
    @ApiOperation(value = "Method for deleting basket")
    @DeleteMapping("/delete")
    public void clear(){
        basketService.clear();
    }
}