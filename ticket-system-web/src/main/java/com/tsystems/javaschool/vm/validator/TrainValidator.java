package com.tsystems.javaschool.vm.validator;

import com.tsystems.javaschool.vm.domain.Train;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrainValidator extends SBBValidator<Train> {

    @Override
    public List<String> validate(Train train) {
        List<String> errorList = new ArrayList<String>();
        if (train.getNumber() == null) {
            errorList.add("Error: number field is null");
        }
        if (train.getPlacesQty() == null) {
            errorList.add("Error: placesQty field is null");
        }
        if (errorList.isEmpty()) {
            validator.validate(train, errorList);
        }
        return errorList;
    }


}
