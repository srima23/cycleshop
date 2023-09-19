package com.talentsprint.cycleshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talentsprint.cycleshop.entity.Cycle;
import com.talentsprint.cycleshop.exception.CycleShopBusinessException;
import com.talentsprint.cycleshop.repository.CycleRepository;

@Service
public class CycleService {
    @Autowired
    private CycleRepository cycleRepository;

    public List<Cycle> listCycles() {
        var listFromDB = cycleRepository.findAll();
        var cycleList = new ArrayList<Cycle>();
        listFromDB.forEach(cycleList::add);
        return cycleList;
    }

    public List<Cycle> listAvailableCycles() {
        return listCycles()
        .stream()
        .filter(cycle -> cycle.getNumAvailable() > 0)
        .collect(Collectors.toList());
    }

    public Cycle findByIdOrThrow404(long id) {
        var optCycle = cycleRepository.findById(id);
        if (optCycle.isEmpty()) {
            throw new CycleShopBusinessException(
                String.format("Can't find the cycle with id %d in the DB",
                id));
        }
        return optCycle.get();
    }
    public Cycle add(String brand,int stock){
        var cycle = new Cycle();
        cycle.setBrand(brand);
        cycle.setStock(stock);
        cycle.setNumBorrowed(0);
        cycleRepository.save(cycle);
        return cycle;
    }

    public Cycle borrowCycle(long id, int count) {
        var cycle = findByIdOrThrow404(id);
        cycle.setNumBorrowed(cycle.getNumBorrowed() + count);
        cycleRepository.save(cycle);
        return cycle;
    }

    public Cycle returnCycle(long id, int count) {
        var cycle = findByIdOrThrow404(id);
        cycle.setNumBorrowed(cycle.getNumBorrowed() - count);
        cycleRepository.save(cycle);
        return cycle;
    }
    public Cycle addToCartCycle(long id, int count) {
        var cycle = findByIdOrThrow404(id);
        cycle.setNumBorrowed(cycle.getNumBorrowed() + count);
        cycleRepository.save(cycle);
        return cycle;
    }
    public Cycle addToCartCycle(long id) {
        return addToCartCycle(id, 1);
    }

    public Cycle borrowCycle(long id) {
        return borrowCycle(id, 1);
    }

    public Cycle returnCycle(long id) {
        return returnCycle(id, 1);
    }

    public Cycle restockBy(long id, int count) {
        var cycle = findByIdOrThrow404(id);
        cycle.setStock(cycle.getStock() + count);
        cycleRepository.save(cycle);
        return cycle;
    }

}
