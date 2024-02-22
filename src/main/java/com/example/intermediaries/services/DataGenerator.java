package com.example.intermediaries.services;

import com.example.intermediaries.entities.comissions.CommissionRateL2andL3;
import com.example.intermediaries.entities.comissions.CommissionRateL3andL4;
import com.example.intermediaries.entities.intermediariesinfo.IntermediaryTypes;
import com.example.intermediaries.entities.intermediariesinfo.LevelOfIntermediary;
import com.example.intermediaries.entities.intermediariesinfo.TypeOfIntermediary;
import com.example.intermediaries.entities.products.ProductCodeAndTitle;
import com.example.intermediaries.entities.products.ProductType;
import com.example.intermediaries.entities.registrations.ChildrenIntermediaries;
import com.example.intermediaries.entities.registrations.ParentIntermediaries;
import com.example.intermediaries.repositories.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataGenerator {


    @Autowired
    private IntermediaryTypesRepository intermediaryTypesRepo;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private ParentIntermediariesRepository parentIntermediariesRepository;

    @Autowired
    private ChildrenIntermediariesRepository childrenIntermediariesRepository;

    @Autowired
    private CommissionRateL2andL3Repository commissionRateL2andL3Repository;

    @Autowired
    private CommissionRateL3andL4Repository commissionRateL3andL4Repository;


    @PostConstruct
    public void generateData(){
        generateIntermediaryTypes();
        generateProductTypes();
        generateParentAndChildrenIntermediaries();
    }

    private void generateIntermediaryTypes(){
        for (int i = 0; i<4; i++){
            IntermediaryTypes intermediaryTypes = new IntermediaryTypes();
            intermediaryTypes.setIntermidiaryLevel(LevelOfIntermediary.values()[i]);
            intermediaryTypes.setIntermediaryType(TypeOfIntermediary.values()[i]);
            intermediaryTypesRepo.save(intermediaryTypes);
        }
    }

    private void generateProductTypes(){
        for (int i =0; i<6; i++){
            ProductType productType = new ProductType();
            productType.setCode(ProductCodeAndTitle.values()[i]);
            productType.setTitle();
            productTypeRepository.save(productType);
        }
    }

    private void generateParentAndChildrenIntermediaries(){

        for (int i = 0; i < 100; i++) {

            ParentIntermediaries parentIntermediaries = new ParentIntermediaries();
            ChildrenIntermediaries childrenIntermediaries = new ChildrenIntermediaries();

            int rand = generateRandomValue(1,6);
            int rand2 = generateRandomValue(2,3);
            TypeOfIntermediary randType = intermediaryTypesRepo.findById(rand2).get().getIntermidiaryType();
            IntermediaryTypes randIntermediaryTypes = intermediaryTypesRepo.findById(rand2).get();

            if(randType==TypeOfIntermediary.A || randType==TypeOfIntermediary.B ){

                parentIntermediariesRepository.save(parentIntermediaries);
                childrenIntermediariesRepository.save(childrenIntermediaries);

                parentIntermediaries.setIntermediaryType(randIntermediaryTypes);
                parentIntermediaries.setTitle();

                childrenIntermediaries.setParentIntermediaries(parentIntermediaries);
                childrenIntermediaries.setIntermediaryType(intermediaryTypesRepo.findById(4).get());
                childrenIntermediaries.setTitle();

                parentIntermediariesRepository.save(parentIntermediaries);
                childrenIntermediariesRepository.save(childrenIntermediaries);
            }

            generateCommissionRateL2andL3(productTypeRepository.findById(rand).get(), parentIntermediaries);
            generateCommissionRateL3andL4(commissionRateL2andL3Repository.findAll().stream().parallel().findAny().get());
        }
    }

    private void generateCommissionRateL2andL3(ProductType productType, ParentIntermediaries parentIntermediaries){

           CommissionRateL2andL3 commissionRateL2andL3 = new CommissionRateL2andL3();
           int rand = generateRandomValue(7,30);
           commissionRateL2andL3.setCommissionRateTotal(rand);
           commissionRateL2andL3.setProductType(productType);
           commissionRateL2andL3.setParentIntermediaries(parentIntermediaries);

           commissionRateL2andL3Repository.save(commissionRateL2andL3);
    }

    private void generateCommissionRateL3andL4(CommissionRateL2andL3 commissionRateL2andL3){

        int rand = generateRandomValue(1, commissionRateL2andL3.getCommissionRateTotal());
        CommissionRateL3andL4 commissionRateL3andL4 = new CommissionRateL3andL4();
        commissionRateL3andL4.setCommissionRateL2andL3(commissionRateL2andL3);
        commissionRateL3andL4.setCommissionRateTotal();
        commissionRateL3andL4Repository.save(commissionRateL3andL4);

        assignParentOrChild(commissionRateL3andL4);
        calculateRandomPersonalCommissions( commissionRateL2andL3, commissionRateL3andL4, rand);
    }

    private void calculateRandomPersonalCommissions(CommissionRateL2andL3 commissionRateL2andL3, CommissionRateL3andL4 commissionRateL3andL4, int rand ){
        List<CommissionRateL3andL4> listCommissionsGroups =
                commissionRateL3andL4Repository.
                        findAll().stream().
                        filter(c -> (c.getCommissionRateL2andL3().getId())==(commissionRateL2andL3.getId()))
                        .toList();

        if (listCommissionsGroups.isEmpty()){
            commissionRateL3andL4.setCommissionRatePersonal(rand);
            commissionRateL3andL4Repository.save(commissionRateL3andL4);
        }else {
            int commission = commissionRateL3andL4.getCommissionRateTotal() -
                    listCommissionsGroups.stream().mapToInt(CommissionRateL3andL4::getCommissionRatePersonal).sum();
            if (commission == 0 || commission <0){
                commissionRateL3andL4Repository.delete(commissionRateL3andL4);
            }
            else if(commission - rand >0){
                commissionRateL3andL4.setCommissionRatePersonal(commission - rand);
                commissionRateL3andL4Repository.save(commissionRateL3andL4);
            }else {
                commissionRateL3andL4.setCommissionRatePersonal(commission);
                commissionRateL3andL4Repository.save(commissionRateL3andL4);
            }
        }
        // make shore key combinations are UNIQUE

        if (listCommissionsGroups.size()>1){
            var l2 = listCommissionsGroups.stream().filter(a -> a.getParentIntermediaries()!=null);
            var l3 = listCommissionsGroups.stream().filter(a -> a.getChildrenIntermediaries()!=null);
            int size1  = l2.toList().size();
            int size2  = l3.toList().size();
            if (size1 >1){
                long count = listCommissionsGroups.stream().filter(a -> a.getParentIntermediaries()!=null)
                        .mapToInt(a ->  a.getParentIntermediaries().getId()).distinct().count();
                if (count == 1){
                    commissionRateL3andL4Repository.delete(commissionRateL3andL4);
                }
            }
            if (size2 >1){
                long count = listCommissionsGroups.stream().filter(a -> a.getChildrenIntermediaries()!=null)
                        .mapToInt(a ->  a.getChildrenIntermediaries().getId()).distinct().count();
                if (count == 1){
                    commissionRateL3andL4Repository.delete(commissionRateL3andL4);
                }
            }
        }
    }

    private void assignParentOrChild(CommissionRateL3andL4 commissionRateL3andL4){
        int randSwitch = generateRandomValue(1,2);

        if (randSwitch == 1) {
            commissionRateL3andL4.setChildrenIntermediaries(null);
            var parentIntermediaries = parentIntermediariesRepository.findAll().stream().parallel().findAny().get();
            commissionRateL3andL4.setParentIntermediaries(parentIntermediaries);
        } else {
            commissionRateL3andL4.setParentIntermediaries(null);
            var childrenIntermediaries = childrenIntermediariesRepository.findAll().stream().parallel().findAny().get();
            commissionRateL3andL4.setChildrenIntermediaries(childrenIntermediaries);
        }
        commissionRateL3andL4Repository.save(commissionRateL3andL4);
    }

    /*
    * generate a random integer value in a range
    * */
    public int generateRandomValue(int min, int max ){
        int range = max - min + 1;
        int rand = (int)(Math.random() * range) + min;
        return rand;
    }

}

