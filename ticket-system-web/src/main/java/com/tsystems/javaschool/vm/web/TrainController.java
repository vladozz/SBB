package com.tsystems.javaschool.vm.web;

import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.domain.Train;
import com.tsystems.javaschool.vm.dto.TrainDTO;
import com.tsystems.javaschool.vm.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;
import java.util.regex.Pattern;

@Controller
public class TrainController {
    private final String entity = "train";
    private final String entityWithSlash = "/" + entity;
    private final String index = entityWithSlash + "/index";
    private final String redirect = "redirect:" + index;

    @Autowired
    TrainService trainService;

    @RequestMapping(index)
    public String listTrains(Map<String, Object> map) {
        map.put("train", new TrainDTO());
        map.put("trainList", trainService.getAllTrains());
        map.put("station", new Station());

        return entity;
    }

    @RequestMapping(entityWithSlash)
    public String home() {
        return redirect;
    }

    @RequestMapping(value = entityWithSlash + "/add", method = RequestMethod.POST)
    public String addTrain(@ModelAttribute("train") TrainDTO trainDTO, BindingResult result) {

        String number = trainDTO.getNumber();
        if (number.isEmpty()) {
            return redirect;
        }

        String placesQtyString = trainDTO.getPlacesQty();
        Pattern pattern = Pattern.compile("[0-9]+");
        if (placesQtyString.isEmpty() || !pattern.matcher(placesQtyString).matches()) {
            return redirect;
        }
        Integer placesQtyInt = Integer.parseInt(placesQtyString);
        if (placesQtyInt > Short.MAX_VALUE || placesQtyInt <= 0) {
            return redirect;
        }
        Short placesQty = Short.parseShort(placesQtyString);

        trainService.addTrain(new Train(number, placesQty));

        return redirect;
    }

    @RequestMapping(value = entityWithSlash + "/delete/{id}")
    public String removeTrain(@PathVariable("id") Long trainId) {

        trainService.removeTrain(trainId);

        return redirect;
    }
}
