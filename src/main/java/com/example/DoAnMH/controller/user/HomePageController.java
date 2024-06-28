package com.example.DoAnMH.controller.user;

import com.example.DoAnMH.model.Product;
import com.example.DoAnMH.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomePageController {
    @Autowired
    private  final ProductService productService;
    @GetMapping("/")
    public String index(Model model) {
        List<Product>Sales = productService.getProductsSortedByDiscount();
        List<Product>All = productService.getAllProduct();
        List<Product>Macbook = productService.getProductsByCategoryName("Macbook");
        for (int i = Sales.size() - 1; i >= 0; i--) {
            if (Sales.get(i).getDiscountId().getDiscount()==0.0) {
                Sales.remove(i);
            }
        }
        for (Product p : All){
            p.setImages(p.convertJsonToImages());
            if(p.getDiscountId().getDiscount()==0.0){
                p.setPriceDiscount((double)0);
                continue;
            }
            double disc = p.getDiscountId().getDiscount();
            double sale = p.getPrice() * ((double) 1 -disc/(double) 100);
            p.setPriceDiscount(sale);
        }
        for (Product p : Macbook){
            p.setImages(p.convertJsonToImages());
        }
        model.addAttribute("sales",Sales);
        model.addAttribute("products",All);
        model.addAttribute("macbooks",Macbook);
        return "User/HomePage"; // This should match the name of your HTML file without the .html extension
    }
    @GetMapping("/Detail/{id}")
    public String Detailpage(@PathVariable Long id,Model model) {
        Product product = productService.getProductById(id).orElseThrow();
//        List<Product>list = new ArrayList<>();
        List<Product> list= productService.getProductsByCategoryName(product.getCategoryId().getName());
        product.setImages(product.convertJsonToImages());
        if(product.getDiscountId().getDiscount()==0.0){
            product.setPriceDiscount((double)0);
        }else{
            double disc = product.getDiscountId().getDiscount();
            double sale = product.getPrice() * ((double) 1 -disc/(double) 100);
            product.setPriceDiscount(sale);
        }
        while(list.size()>4){
                list.removeLast();
        }
        for (Product p : list){
            p.setImages(p.convertJsonToImages());
            if(p.getDiscountId().getDiscount()==0.0){
                p.setPriceDiscount((double)0);
                continue;
            }
            double disc = p.getDiscountId().getDiscount();
            double sale = p.getPrice() * ((double) 1 -disc/(double) 100);
            p.setPriceDiscount(sale);
        }
        model.addAttribute("product",product);
        model.addAttribute("list",list);
        return "User/DetailPage";
    }
        @GetMapping("/Product")
    public String Productpage() {
        return "User/ProductPage"; // This should match the name of your HTML file without the .html extension
    }
}
