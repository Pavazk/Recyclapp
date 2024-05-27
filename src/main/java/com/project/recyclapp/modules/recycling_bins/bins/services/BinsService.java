package com.project.recyclapp.modules.recycling_bins.bins.services;

import com.project.recyclapp.modules.recycling_bins.bins.models.Bin;
import com.project.recyclapp.modules.recycling_bins.bins.models.Color;

import java.util.List;

public interface BinsService {

    Bin registerBin(Bin bin);

    Bin updateBin(Bin bin, Integer id);

    String deleteBin(Bin bin);

    List<Bin> getAllBins();
    List<Color> getAllColors();

}
