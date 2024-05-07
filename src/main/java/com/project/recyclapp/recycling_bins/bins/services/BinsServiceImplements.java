package com.project.recyclapp.recycling_bins.bins.services;

import com.project.recyclapp.commons.Utils;
import com.project.recyclapp.commons.exceptions.CustomException;
import com.project.recyclapp.commons.messages.ErrorMessage;
import com.project.recyclapp.recycling_bins.bins.models.Bin;
import com.project.recyclapp.recycling_bins.bins.models.Color;
import com.project.recyclapp.recycling_bins.bins.repository.BinsRepository;
import com.project.recyclapp.recycling_bins.bins.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BinsServiceImplements implements BinsService {

    @Autowired
    BinsRepository binsRepository;
    @Autowired
    ColorRepository colorRepository;

    @Override
    public Bin registerBin(Bin bin) {
        if (bin == null || bin.getLongitude() == null || bin.getLatitude() == null) {
            throw Utils.noDataValid();
        }
        if (bin.getColor() == null || Utils.isNullOrEmptyWithTrim(bin.getColor().getName())) {
            throw new CustomException(ErrorMessage.COLOR_NO_VALID.getMessage());
        }
        Optional<Color> color = colorRepository.findById(bin.getColor().getId());
        if (color.isEmpty()) {
            throw new CustomException(ErrorMessage.COLOR_NO_EXISTS.getMessage());
        }
        return binsRepository.save(bin);
    }

    @Override
    public Bin updateBin(Bin bin) {
        if (bin == null || bin.getId() == null) {
            throw Utils.noDataValid();
        }
        Optional<Bin> oldBin = binsRepository.findById(bin.getId());
        if (oldBin.isEmpty()) {
            throw new CustomException(ErrorMessage.BIN_NO_EXISTS.getMessage());
        }
        if (bin.getLatitude() != null) {
            oldBin.get().setLatitude(bin.getLatitude());
        }
        if (bin.getLongitude() != null) {
            oldBin.get().setLongitude(bin.getLongitude());
        }
        return binsRepository.save(oldBin.get());
    }

    @Override
    public String deleteBin(Bin bin) {
        binsRepository.delete(bin);
        return "Bin deleted";
    }

    @Override
    public List<Bin> getAllBins() {
        return (List<Bin>) binsRepository.findAll();
    }
}
