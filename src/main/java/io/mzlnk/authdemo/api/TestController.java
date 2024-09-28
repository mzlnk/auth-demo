package io.mzlnk.authdemo.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
class TestController {

    @GetMapping
    @RequestMapping("/non-secure")
    ResponseEntity<TestResponse> nonSecure() {
        return ResponseEntity.ok(new TestResponse("Not secured endpoint!"));
    }

    @GetMapping
    @RequestMapping("/secure")
    ResponseEntity<TestResponse> secure() {
        return ResponseEntity.ok(new TestResponse("Secured endpoint!"));
    }

}

record TestResponse(String message) {}
