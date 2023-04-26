package ooo.github.io.datacheck.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author kaiqin
 */
@RestController
@RequestMapping("/chat")
public class CheckController {

    @DeleteMapping("/delete_bob")
    public String deleteBob() {
        return "delete bob successfully";
    }

    @GetMapping("/get_bob")
    public String getBob() {
        return "get bob successfully";
    }

    @PostMapping("/post_bob")
    public String postBob() {
        return "post bob successfully";
    }

}
