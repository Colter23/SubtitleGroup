package top.colter.subtitlegroup.controller;

import cn.hutool.jwt.JWTUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.colter.subtitlegroup.entity.Sheet;
import top.colter.subtitlegroup.entity.User;
import top.colter.subtitlegroup.service.QQHttpClient;
import top.colter.subtitlegroup.service.UserService;
import top.colter.subtitlegroup.utils.TokenUtils;
import top.colter.subtitlegroup.vo.UserVo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class UserController {

    private final QQHttpClient qqHttpClient;
    private final UserService userService;

    public UserController(QQHttpClient qqHttpClient, UserService userService) {
        this.qqHttpClient = qqHttpClient;
        this.userService = userService;
    }

    @GetMapping("/sendVerify/{qq}")
    public Boolean sendVerify(HttpServletRequest request, HttpServletResponse response, @PathVariable("qq") String qq){

        StringBuilder verify = new StringBuilder();
        for (int i = 0; i < 6; i++){
            verify.append((int)(Math.random() * 10));
        }
        System.out.println("验证码: "+verify.toString());
        Boolean b = qqHttpClient.sendMessage("验证码: "+verify, Long.parseLong(qq));
        //Boolean b = true;
        if (b){
            System.out.println("pre: "+request.getSession().getId());
            //Cookie cookie = new Cookie("qq",qq);
            //cookie.setPath("http://test.vividbtn.top");
            //response.addCookie(cookie);
            request.getSession().setAttribute("verify", verify.toString());
        }
        return b;
    }

    @GetMapping("/verify/{qq}/{code}")
    public Boolean verify(HttpServletRequest request, HttpServletResponse response, @PathVariable("qq") String id, @PathVariable("code") String verify){
        try {
            System.out.println("next: "+request.getSession().getId());
            String tryVerify = request.getSession().getAttribute("verify").toString();
            if (tryVerify.equals(verify)){
                Cookie cookie = new Cookie("token", TokenUtils.getToken(id));
                System.out.println("====###===="+request.getRequestURI());
                cookie.setPath("/");
                response.addCookie(cookie);
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @GetMapping("/isVerify")
    public String isVerify(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie c : cookies){
                if ("token".equals(c.getName())){
                    if (TokenUtils.verify(c.getValue())){
                        return (String) JWTUtil.parseToken(c.getValue()).getPayload("id");
                    }
                }
            }
        }
        return "false";
    }

    @GetMapping("/userInfo/{qq}")
    public UserVo getUserInfo(@PathVariable("qq")Long qq){
        return userService.findUserById(qq);
    }

    @GetMapping("/userlist/{pertain}/{group}")
    public List<UserVo> getUserList(@PathVariable("pertain") String pertain, @PathVariable("group") String group){
        try {
            return userService.findUserByPertain("/"+pertain+"/"+group);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

}
