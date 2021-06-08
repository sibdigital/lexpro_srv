package ru.sibdigital.lexpro.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.sibdigital.lexpro.model.ClsUser;

import java.util.List;

@Log4j2
@Controller
public class UserRoleController extends SuperController {
    @GetMapping("/user_list")
    public @ResponseBody
    ResponseEntity<List<ClsUser>> getUserList() {
        return ResponseEntity.ok(userRoleService.getUserList());
        //return userRoleService.getUserList();

    }
}
