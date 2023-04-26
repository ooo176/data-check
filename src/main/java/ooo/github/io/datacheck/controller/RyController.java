package ooo.github.io.datacheck.controller;

import ooo.github.io.datacheck.dto.CheckInputDTO;
import ooo.github.io.datacheck.dto.CheckOutput;
import ooo.github.io.datacheck.service.CustomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kaiqin
 */
@RestController
@RequestMapping("/sql")
public class RyController {

    @Autowired
    private CustomServiceImpl customService;

    @PostMapping("/check")
    public CheckOutput check(@RequestBody CheckInputDTO inputDTO) {
        return customService.list(inputDTO);
    }
}
