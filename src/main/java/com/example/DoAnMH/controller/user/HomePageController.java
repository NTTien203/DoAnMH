package com.example.DoAnMH.controller.user;

import com.example.DoAnMH.model.Category;
import com.example.DoAnMH.model.Product;
import com.example.DoAnMH.service.CartService;
import com.example.DoAnMH.service.CategoryService;
import com.example.DoAnMH.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.geom.QuadCurve2D;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomePageController {
    @Autowired
    private  final ProductService productService;
    @Autowired
    private final CategoryService categoryService;
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
    public String Productpage(Model model) {
        List<Product> list = productService.getAllProduct();
        List<Category> categories = categoryService.getAllCategory();
        double maxPrice = 0;
        for (Product p : list){
            p.setImages(p.convertJsonToImages());
            double disc = p.getDiscountId().getDiscount();
            double sale = p.getPrice() * ((double) 1 -disc/(double) 100);
            maxPrice = Math.max(maxPrice,sale);
            p.setPriceDiscount(sale);
        }
        while (!list.isEmpty()&&list.size()>6){
            list.removeLast();
        }
        Page<Product>Page = new PageImpl<>(list.subList(0,Math.min(list.size(),6)),PageRequest.of(0, 6),list.size());
        model.addAttribute("categories",categories);
        model.addAttribute("maxPrice",maxPrice);
        model.addAttribute("price1",0.0);
        model.addAttribute("cate1",null);
        model.addAttribute("sort1","Most Popular");
        model.addAttribute("productsPage", Page);

        return "user/ProductPage";// This should match the name of your HTML file without the .html extension
    }
    @PostMapping("/Product")
    public String ProductPage(Model model, @RequestParam(value = "price",required = false) Double price,@RequestParam(name = "catename",required = false) String cate,@RequestParam(name = "sort",required = false) String sort
    ,@RequestParam(defaultValue = "0") int page,@RequestParam(name = "search",required = false) String search,@RequestParam(name="view",required = false) String view
    ){
//        System.out.println(cate);
        List<Product>list = productService.getAllProduct();
        if(view!=null){
            if(view.equals("1")){
                list = productService.getProductsSortedByDiscount();
                for (int i = list.size() - 1; i >= 0; i--) {
                    if (list.get(i).getDiscountId().getDiscount()==0.0) {
                        list.remove(i);
                    }
                }
            }else if(view.equals("2")){
                list = productService.getProductsByCategoryName("Macbook");
            }
        }
        List<Category> categories = categoryService.getAllCategory();
        double maxPrice = 0;
        for (Product p : list){
            p.setImages(p.convertJsonToImages());
            double disc = p.getDiscountId().getDiscount();
            double sale = p.getPrice() * ((double) 1 -disc/(double) 100);
            maxPrice = Math.max(maxPrice,sale);
            p.setPriceDiscount(sale);
        }
        if(search!=null){
            Iterator<Product> iterator = list.iterator();
            while (iterator.hasNext()){
                Product product = iterator.next();
                if(!product.getName().toLowerCase().contains(search.toLowerCase())){
                    iterator.remove();
                }
            }
        }
        if(cate!=null){
            Iterator<Product> iterator = list.iterator();
            while (iterator.hasNext()) {
                Product product = iterator.next();
                if (!cate.toLowerCase().equals(product.getCategoryId().getName().toLowerCase())) {
                    iterator.remove();
                }
            }
        }
        if(price!=null && price.intValue() > 0){
            for(int i = list.size()-1;i>=0;i--){
                double sale = list.get(i).getPriceDiscount();
                if((int)sale < price.intValue()){
                    list.remove(i);
                }
            }
        }
//        for (Product p : list){
//            System.out.println(p.getPriceDiscount());
//        }
        if(sort!=null&&sort.equals("Low to High")) {
                 list.stream()
                    .sorted(new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return Double.compare(p2.priceDiscount, p1.priceDiscount);
                        }
                    });
        }
        if(sort!=null&&sort.equals("High to Low")){
            Collections.reverse(list);
        }
        Page<Product>Products = Page.empty();
        if(!list.isEmpty()){
            Products = new PageImpl<>(list.subList(page*6,Math.min(page*6+6,list.size())),PageRequest.of(page,6),list.size());
        }
        model.addAttribute("categories",categories);
        model.addAttribute("price1",price);
        model.addAttribute("cate1",cate);
        model.addAttribute("sort1",sort);
        model.addAttribute("maxPrice",maxPrice);
        model.addAttribute("productsPage",Products);
        return "user/ProductPage";
    }
}
