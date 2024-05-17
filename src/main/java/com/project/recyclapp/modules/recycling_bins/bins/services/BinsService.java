package com.project.recyclapp.modules.recycling_bins.bins.services;

import com.project.recyclapp.modules.recycling_bins.bins.models.Bin;

import java.util.List;

public interface BinsService {

    Bin registerBin(Bin bin);

    Bin updateBin(Bin bin);

    String deleteBin(Bin bin);

    List<Bin> getAllBins();

}
