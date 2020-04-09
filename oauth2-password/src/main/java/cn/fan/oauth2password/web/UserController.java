package cn.fan.oauth2password.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
public class UserController {

    @Autowired
    private ObjectMapper objectMapper;

//    @GetMapping(value = "/user/me", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public String me() throws JsonProcessingException {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = auth.getPrincipal();
//        return objectMapper.writeValueAsString(principal);
//    }

    @GetMapping(value = "/user/me", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String me(Authentication auth, HttpServletRequest request) throws JsonProcessingException, UnsupportedEncodingException {
        String header = request.getHeader("Authorization");
//        String token = header.split("Bearer ")[1];
        String token =header.substring(6).trim();
        Claims claims = Jwts.parser().setSigningKey("myJwtKey".getBytes("UTF-8")).parseClaimsJws(token).getBody();
        String foo = (String) claims.get("foo");

        System.out.println(foo);

        return objectMapper.writeValueAsString(auth);
    }

}
