package com.kxtx.boot.web;

import com.kxtx.boot.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

/**
 * Created by YT on 2017/10/31.
 */
@Controller
public class TestController {
    @RequestMapping(value = "/index")
    public String index(HashMap<String, Object> map) {
        System.err.println(1111);
        map.put("hello", "hello");
        return "hello";
    }

    @RequestMapping(value = "/hi")
    @ResponseBody
    public ResponseEntity<Object> hi() {
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @RequestMapping("/employee")
    @ResponseBody
    public Employee employee() {
        return new Employee() {{
            setEmail("xx@qq.com");
            setFirstName("chen");
            setLastName("alex");
        }};
    }
}
