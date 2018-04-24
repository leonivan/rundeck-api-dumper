package org.rundeck.api.web

import org.rundeck.api.services.RundeckService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProjectsController {

    @RequestMapping(value="/version", method=RequestMethod.GET)
    @ResponseBody
    def getVersion() {
        def url='system/info'
        def data = new RundeckService().getData_JsonObject(url)
        return data
        //return "FUNCIONA!!!!"
    }

    @RequestMapping(value="/projects", method=RequestMethod.GET)
    @ResponseBody
    def projects() {
        def url='projects'
        def data = new RundeckService().getData_JsonArray(url)
        return data
    }

    @RequestMapping(value="/projects/{project}/jobs", method=RequestMethod.GET)
    @ResponseBody
    def jobs(@PathVariable("project") String project) {
        def url="project/$project/jobs"
        def data = new RundeckService().getData_JsonArray(url)
        return data
    }

    @RequestMapping(value="/project/{project}/jobs/running", method=RequestMethod.GET)
    @ResponseBody
    def jobsrunning(@PathVariable("project") String project) {
        def url="project/$project/executions/running"
        //def data = new RundeckService().getData_JsonObject(url)//.getData(url)
        def data = new RundeckService().getData_JsonObject(url)
        //def data = new RundeckService().getData(url)
        return data
    }
}
