//package md5.end.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CookieValue;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.SessionAttributes;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//@Controller
//@SessionAttributes("username")
//public class SessionController {
//    // session lưu trữ phía server, dạng object
//    // cookie lưu trữ phía client, tự động gửi kèm theo requt lên server, dạng token
//    // 3 cách khởi tạo session
//    // HTTPSession session
//    // HTTPServletRequest request.getSession()
//    // @SessionAttribute
//    @ModelAttribute("username")
//    public String setUsername(){
//        return "nhung274";
//    }
//    @GetMapping("/")
//    public String home(HttpSession session, HttpServletRequest request, HttpServletResponse response){
//        Cookie cookie = new Cookie("username","nhung111");
//        cookie.setMaxAge(24*60*60); // 24h - 60p - 60s
//        response.addCookie(cookie);
//        session.setAttribute("username","nhung123");
//        return "home";
//    }
//    @GetMapping("set")
//    public String set(HttpSession session, @CookieValue("username")String username){
//        System.out.println(username);
//        session.setAttribute("username","nhung234");
//        return "home";
//    }
//    @GetMapping("/view")
//    public String views(HttpSession session){
//        System.out.println(session.getAttribute("username"));
//        return "view";
//    }
//    @GetMapping("/logout")
//    public String views(HttpServletResponse response){
//        Cookie cookie = new Cookie("username","");
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
//        return "home";
//    }
//
//}
