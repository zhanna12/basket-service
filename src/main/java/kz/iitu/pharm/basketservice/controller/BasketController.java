package kz.iitu.pharm.basketservice.controller;

import io.swagger.annotations.Api;
import kz.iitu.pharm.basketservice.entity.Basket;
import kz.iitu.pharm.basketservice.entity.Drug;
import kz.iitu.pharm.basketservice.repository.BasketRepository;
import kz.iitu.pharm.basketservice.service.impl.BasketServiceImpl;
import kz.iitu.pharm.basketservice.service.impl.DrugServiceImpl;
import kz.iitu.pharm.basketservice.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    private DrugServiceImpl drugService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private BasketServiceImpl basketService;

    @Autowired
    private RestTemplate restTemplate;

    private Map<Drug, Integer> drugs = new HashMap<>();

//    @GetMapping("")
//    @ResponseBody
//    public List<Basket> findAll() {
//        return basketRepository.findAll();
//    }
//
//
//    @GetMapping("/{id}")
//    @ResponseBody
//    public Optional<Basket> findBasketById(@PathVariable Long id) {
//        return basketRepository.findById(id);
//    }

//    @GetMapping("/list")
//    public Drug[] getAllDrugs() {
//        ResponseEntity<Drug[]> response =
//                restTemplate.getForEntity(
//                        "http://drug-service/drugs/",
//                        Drug[].class);
//        Drug[] products = response.getBody();
//        return products;
//    }
//
    @GetMapping("/{id}")
    @ResponseBody
    public Basket getBasketById(@PathVariable("id") Long id){
        return basketRepository.findById(id).get();
    }

    @ApiOperation(value = "Method for adding basket to user")
    @PatchMapping("/add/")
    public void addBasketToUser(@RequestParam("userId") Long userId, @RequestParam("basketId") Long basketId){
        if(basketService.addBasketToUser(userId,basketId)){
            System.out.println("Basket added to " + userId);
        }
        else{
            System.out.println("basket is already owned");
        }
    }
    @GetMapping("/shoppingCart")
    public ModelAndView shoppingCart() {
        ModelAndView modelAndView = new ModelAndView("/basket");
        modelAndView.addObject("drugs", basketService.getDrugsInBasket());
        modelAndView.addObject("total", basketService.getTotal().toString());
        return modelAndView;
    }
    @GetMapping("/shoppingCart/addProduct/{productId}")
    public ModelAndView addProductToCart(@PathVariable("productId") Long productId) {
        drugService.findById(productId).ifPresent(basketService::addDrug);
        return shoppingCart();
    }
    @GetMapping("/shoppingCart/removeProduct/{productId}")
    public ModelAndView removeProductFromCart(@PathVariable("productId") Long productId) {
        drugService.findById(productId).ifPresent(basketService::removeDrug);
        return shoppingCart();
    }
    @GetMapping("/shoppingCart/checkout")
    public ModelAndView checkout() {
        basketService.getTotal();
        return shoppingCart();
    }
    @ApiOperation(value = "Method for deleting basket")
    @DeleteMapping("/delete")
    public void clear(){
        basketService.clear();
    }
}