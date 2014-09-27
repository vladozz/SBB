package com.tsystems.javaschool.vm.converter;

import com.tsystems.javaschool.vm.domain.Path;
import com.tsystems.javaschool.vm.domain.Station;
import com.tsystems.javaschool.vm.dto.PathDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PathConverter {
    public PathDTO convertToPathDTO(Path path) {
        return convertToPathDTO(path, true);
    }

    public PathDTO convertToPathDTO(Path path, boolean forward) {
        PathDTO pathDTO = new PathDTO(path.getId(), path.getTitle(), path.getReturnTitle()," ", " ", path.getLastChange());
        List<Station> stations = path.getStations();
        if (!stations.isEmpty()) {
            String beginStation = stations.get(0).getTitle();
            String endStation = stations.get(stations.size() - 1).getTitle();
            if (forward) {
                pathDTO.setBeginStation(beginStation);
                pathDTO.setEndStation(endStation);
            } else {
                pathDTO.setBeginStation(endStation);
                pathDTO.setEndStation(beginStation);
            }
        }
        return pathDTO;
    }

}
