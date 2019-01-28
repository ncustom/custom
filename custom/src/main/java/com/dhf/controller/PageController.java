package com.dhf.controller;

import com.dhf.service.CityService;
import com.dhf.service.ProvinceService;
import com.dhf.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Controller
public class PageController {

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = {"/index","/"}, method = RequestMethod.GET )
    public ModelAndView goIndex(ModelAndView mv){
        mv.setViewName("index");
        return mv;
    }

    @RequestMapping(value = "/reg", method = RequestMethod.GET )
    public String goReg(){
        return "reg";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET )
    public String goLogin(){
        return "login";
    }

    @RequestMapping(value = "/updateinfo", method = RequestMethod.GET )
    public String goUpdateinfo(){
        return "updateinfo";
    }

    @RequestMapping(value = "/changecity", method = RequestMethod.GET )
    public String goChangeCity(Model model){
        List<Map<String, Object>> provinces = provinceService.selectAllProvinces();
        List<Map<String, Object>> citys = cityService.selectAllCitys();
        model.addAttribute("provinces", provinces);
        model.addAttribute("citys", citys);
        return "changecity";
    }

    @RequestMapping(value = "/index/{code}", method = RequestMethod.GET )
    public ModelAndView goIndex(@PathVariable String code, ModelAndView mv){
        Map<String, Object> city = cityService.selectCityByCode(code);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map<String, Object>> tasks = taskService.selectTasksByCode(code);
        for (Map<String, Object> map : tasks) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equals("finishTime")) {
                    String finishTime = sdf.format(entry.getValue());
                    entry.setValue(finishTime);
                }
            }
        }
        if (tasks.size() != 0) {
            mv.addObject("tasks", tasks);
        } else {
            mv.addObject("msg","抱歉，该城市目前没有待接任务！");
        }
        mv.addObject("city", city);
        mv.setViewName("index");
        return mv;
    }

    @RequestMapping(value = "/becomeexpert", method = RequestMethod.GET )
    public String goBecomeexpert(){
        return "becomeexpert";
    }

    @RequestMapping(value = "/historychat", method = RequestMethod.GET )
    public String goHistorychat(){
        return "historychat";
    }

    @RequestMapping(value = "/myinformation", method = RequestMethod.GET )
    public String goMyinformation(){
        return "myinformation";
    }

    @RequestMapping(value = "/mytask", method = RequestMethod.GET )
    public String goMyTask(){
        return "mytask";
    }
}
