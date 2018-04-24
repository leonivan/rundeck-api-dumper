package org.rundeck.api.web

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class HalloController {

    @RequestMapping(method = RequestMethod.GET, value = "/api/hallo")
    def sayHello() {
        return "Hallo Richard!"
    }
}
