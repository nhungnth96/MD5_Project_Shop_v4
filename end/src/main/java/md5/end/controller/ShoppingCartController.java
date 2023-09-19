package md5.end.controller;

//@Controller
//@SessionAttributes("cart")
//public class ProductController {
//    @Autowired
//    private IProductService productService;
//
//    @ModelAttribute("cart")
//    public Cart setupCart() {
//        return new Cart();
//    }
//
//    @GetMapping("/shop")
//    public ModelAndView showShop() {
//        ModelAndView modelAndView = new ModelAndView("shop");
//        modelAndView.addObject("products", productService.findAll());
//        return modelAndView;
//    }
//    @GetMapping("/detail/{id}")
//    public ModelAndView showDetail(@PathVariable Long id){
//        return new ModelAndView("detail","product",productService.findById(id).get());
//    }
//    @GetMapping("/delete/{id}")
//    public String deleteInCart(@PathVariable Long id,@ModelAttribute Cart cart){
//        Optional<Product> productOptional = productService.findById(id);
//        if (!productOptional.isPresent()) {
//            return "404";
//        }
//        cart.deleteProduct(productOptional.get());
//        return "redirect:/shopping-cart";
//    }
//    @GetMapping("/add/{id}")
//    public String addToCart(@PathVariable Long id, @ModelAttribute Cart cart, @RequestParam("action") String action) {
//        Optional<Product> productOptional = productService.findById(id);
//        if (!productOptional.isPresent()) {
//            return "404";
//        }
//        if (action.equals("increase")) {
//            cart.addProduct(productOptional.get());
//            return "redirect:/shopping-cart";
//        } else if (action.equals("decrease")) {
//            cart.removeProduct(productOptional.get());
//            return "redirect:/shopping-cart";
//        }
//
//        cart.addProduct(productOptional.get());
//        return "redirect:/shop";
//    }

//}
