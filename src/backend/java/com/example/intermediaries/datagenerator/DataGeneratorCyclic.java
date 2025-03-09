package com.example.intermediaries.datagenerator;

import com.example.intermediaries.comissions.CommissionRateL2andL3;
import com.example.intermediaries.comissions.CommissionRateL2andL3Repository;
import com.example.intermediaries.comissions.CommissionRateL3andL4;
import com.example.intermediaries.comissions.CommissionRateL3andL4Repository;
import com.example.intermediaries.intermediaries.IntermediaryTypes;
import com.example.intermediaries.intermediaries.additionalInfo.TypeOfIntermediary;
import com.example.intermediaries.intermediaries.childrenIntermediaries.ChildrenIntermediaries;
import com.example.intermediaries.intermediaries.childrenIntermediaries.ChildrenIntermediariesRepository;
import com.example.intermediaries.intermediaries.parentIntermediaries.ParentIntermediaries;
import com.example.intermediaries.intermediaries.parentIntermediaries.ParentIntermediariesRepository;
import com.example.intermediaries.sales.l2andl3.SalesL2AndL3;
import com.example.intermediaries.sales.l2andl3.SalesL2AndL3Repository;
import com.example.intermediaries.sales.SalesL3AndL4;
import com.example.intermediaries.sales.SalesL3AndL4Repository;
import com.example.intermediaries.sales.l2andl3.SalesL2L3DTO;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class DataGeneratorCyclic {
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    @Autowired
    private ParentIntermediariesRepository parentIntermediariesRepository;
    @Autowired
    private ChildrenIntermediariesRepository childrenIntermediariesRepository;
    @Autowired
    private CommissionRateL2andL3Repository commissionRateL2andL3Repository;
    @Autowired
    private CommissionRateL3andL4Repository commissionRateL3andL4Repository;
    @Autowired
    private SalesL2AndL3Repository salesL2AndL3Repository;
    @Autowired
    private SalesL3AndL4Repository salesL3AndL4Repository;
    public static ConcurrentHashMap<Integer, ParentIntermediaries> parentIntermediariesCollection = new ConcurrentHashMap();
    public static ConcurrentHashMap<Integer, ChildrenIntermediaries> childrenIntermediariesCollection = new ConcurrentHashMap();
    public static ConcurrentHashMap<Integer, CommissionRateL2andL3> commissionRateL2andL3Collection = new ConcurrentHashMap();
    public static ConcurrentHashMap<Integer, CommissionRateL3andL4> commissionRateL3andL4CollectionAgents = new ConcurrentHashMap();
    public static ConcurrentHashMap<Integer, CommissionRateL3andL4> commissionRateL3andL4CollectionBrokers = new ConcurrentHashMap();
    public static ConcurrentHashMap<Integer, SalesL2AndL3> salesL2AndL3Collection = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer, SalesL3AndL4> salesL3AndL4Collection = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer, SalesL2L3DTO> salesL2AndL3DTOCollection = new ConcurrentHashMap<>();

    /*  public DataGeneratorCyclic(){
        scheduledExecutorService.scheduleAtFixedRate(this::generateData, 0, 5, TimeUnit.SECONDS);
    }*/
    @Scheduled(fixedRate = 5000)
    public void generateData(){
        generateParentIntermediaries();
        generateChildIntermediaries();
        generateCommissionRate();
    }


    // Flux data streams generated
    public Flux<SalesL2AndL3> getDataStreamSalesL2AndL3(){
        return Flux.interval(Duration.ofSeconds(5))
                .flatMapIterable(ignore -> salesL2AndL3Collection.values());
    }

    private void generateParentIntermediaries(){
        ParentIntermediaries parentIntermediaries = new ParentIntermediaries();
        int random = generateRandomValue(1,2);
        int indent = generateRandomValue(1000, 100000);
        IntermediaryTypes intermediaryTypes = DataGenerator.intermediaryTypesList.get(random);
        parentIntermediaries.setTitle(intermediaryTypes.getIntermediaryType() + "" + indent);
        parentIntermediaries.setIntermediaryType(intermediaryTypes);
        parentIntermediariesRepository.save(parentIntermediaries);
        parentIntermediariesCollection.put(parentIntermediaries.getId(), parentIntermediaries);
    }

    private void generateChildIntermediaries(){
        if (!parentIntermediariesCollection.values().stream().filter(a->a.getTitle().startsWith("B")).findAny().isEmpty()){
        ChildrenIntermediaries childrenIntermediaries = new ChildrenIntermediaries();
        int indent = generateRandomValue(1000, 100000);
        childrenIntermediaries.setTitle("AB" + indent);
        int randomParentID = generateRandomValue(1, parentIntermediariesCollection.size() );
        childrenIntermediaries.setParentIntermediaries( parentIntermediariesCollection.get(randomParentID));
        childrenIntermediaries.setIntermediaryType(DataGenerator.intermediaryTypesList.get(3));
        childrenIntermediariesRepository.save(childrenIntermediaries);
        childrenIntermediariesCollection.put(childrenIntermediaries.getId(), childrenIntermediaries);}
    }

    private void generateCommissionRate(){
        //L2 and L3
        if (!parentIntermediariesCollection.values().isEmpty()){
            CommissionRateL2andL3 commissionRateL2andL3 = new CommissionRateL2andL3();
            int randomNum = generateRandomValue(0, 5);
            int randomTotalCommission = generateRandomValue(10,25);
            int randomParentID = generateRandomValue(1, parentIntermediariesCollection.size() );
            commissionRateL2andL3.setProductType(DataGenerator.productTypeList.get(randomNum));
            ParentIntermediaries parentInt = parentIntermediariesCollection.get(randomParentID);
            commissionRateL2andL3.setParentIntermediaries(parentInt);
            commissionRateL2andL3.setCommissionRateTotal(randomTotalCommission);
            commissionRateL2andL3.setIntermediaryTypes(parentInt.getIntermediaryType());

            commissionRateL2andL3Repository.save(commissionRateL2andL3);
            commissionRateL2andL3Collection.put(commissionRateL2andL3.getId(), commissionRateL2andL3);
            //L3 and L4
            if (parentInt.getTitle().startsWith("B")){
                generateCommissionRateL3andL4(randomTotalCommission, parentInt, commissionRateL2andL3);
            }
        }
    }

    private void generateCommissionRateL3andL4(int randomTotalCommission, ParentIntermediaries parentInt, CommissionRateL2andL3 commissionRateL2andL3 ){
        CommissionRateL3andL4 commissionRateL3andL4Broker = new CommissionRateL3andL4();
        commissionRateL3andL4Broker.setCommissionRateTotal(randomTotalCommission);
        commissionRateL3andL4Broker.setParentIntermediaries(parentInt);
        commissionRateL3andL4Broker.setCommissionRateL2andL3(commissionRateL2andL3);
        int personalCommission = generateRandomValue(3,randomTotalCommission );

        if (personalCommission!=randomTotalCommission && !childrenIntermediariesCollection.isEmpty()){
           generateCommissionRateL3L4Agent(randomTotalCommission, parentInt, commissionRateL2andL3, personalCommission, commissionRateL3andL4Broker);
        }else {
            commissionRateL3andL4Broker.setChildrenIntermediaries(null);
            commissionRateL3andL4Broker.setCommissionRatePersonal(randomTotalCommission);
        }
        commissionRateL3andL4Repository.save(commissionRateL3andL4Broker);
        commissionRateL3andL4CollectionBrokers.put(commissionRateL3andL4Broker.getId(), commissionRateL3andL4Broker);

    }

    private void generateCommissionRateL3L4Agent(int randomTotalCommission,
                                                 ParentIntermediaries parentInt,
                                                 CommissionRateL2andL3 commissionRateL2andL3,
                                                 int personalCommission,
                                                 CommissionRateL3andL4 commissionRateL3andL4Broker){
        CommissionRateL3andL4 commissionRateL3andL4Agent = new CommissionRateL3andL4();
        int randomChildID = generateRandomValue(1, childrenIntermediariesCollection.size());
        ChildrenIntermediaries randomChild = childrenIntermediariesCollection.get(randomChildID);
        commissionRateL3andL4Agent.setCommissionRateTotal(randomTotalCommission);
        commissionRateL3andL4Agent.setParentIntermediaries(parentInt);
        commissionRateL3andL4Agent.setChildrenIntermediaries(randomChild);
        commissionRateL3andL4Agent.setCommissionRateL2andL3(commissionRateL2andL3);

        commissionRateL3andL4Agent.setCommissionRatePersonal(personalCommission);
        commissionRateL3andL4Broker.setCommissionRatePersonal(randomTotalCommission - personalCommission);

        commissionRateL3andL4Repository.save(commissionRateL3andL4Agent);
        commissionRateL3andL4CollectionAgents.put(commissionRateL3andL4Agent.getId(), commissionRateL3andL4Agent);
        generateSalesL2L3(commissionRateL2andL3, commissionRateL3andL4Agent);
    }

    private void generateSalesL2L3(CommissionRateL2andL3 commissionRateObj, CommissionRateL3andL4 commissionRateL3andL4Agent){
        if(!commissionRateL2andL3Collection.isEmpty()){
            SalesL2AndL3 salesL2AndL3 = new SalesL2AndL3();
            double randomIBM = generateRandomValue(100000, 1000000);
            int randomPremiumPercentage = generateRandomValue(5, 35);
            double premiumCalculated = (double) (randomPremiumPercentage * randomIBM) /100.00;
            Double commissionCalculated = (double) (commissionRateObj.getCommissionRateTotal()*premiumCalculated)/100.00;
            double companyProfit = premiumCalculated - commissionCalculated;

            salesL2AndL3.setIbm(randomIBM);
            salesL2AndL3.setPremium(randomPremiumPercentage);
            salesL2AndL3.setPremiumCalculated(premiumCalculated);
            salesL2AndL3.setFK_commissionRateL3L2(commissionRateObj);
            salesL2AndL3.setCommissionCalculated(commissionCalculated);
            salesL2AndL3.setCompanyProfit(companyProfit);

            salesL2AndL3Repository.save(salesL2AndL3);
            salesL2AndL3Collection.put(salesL2AndL3.getId(), salesL2AndL3);
            generateSalesL3L4(commissionRateObj, salesL2AndL3, commissionRateL3andL4Agent);

            generateSalesL2L3DTO(salesL2AndL3);
        }
    }

    private void generateSalesL2L3DTO(SalesL2AndL3 salesL2AndL3){
        SalesL2L3DTO salesL2L3DTO = new SalesL2L3DTO(
                salesL2AndL3.getId(),
                salesL2AndL3.getFK_commissionRateL3L2().getParentIntermediaries().getTitle(),
                salesL2AndL3.getFK_commissionRateL3L2().getProductType().getTitle(),
                salesL2AndL3.getPremiumCalculated(),
                salesL2AndL3.getCommissionCalculated(),
                salesL2AndL3.getSalesDate(),
                salesL2AndL3.getCompanyProfit()
        );
        salesL2AndL3DTOCollection.put(salesL2L3DTO.id(), salesL2L3DTO);
    }

    private void generateSalesL3L4(@NotNull CommissionRateL2andL3 commissionRateL2andL3, SalesL2AndL3 salesL2AndL3, CommissionRateL3andL4 commissionRateL3andL4){

        if (commissionRateL2andL3.getParentIntermediaries()
                .getIntermediaryType()
                .getIntermediaryType()
                .equals(TypeOfIntermediary.B)){
            ParentIntermediaries parentIntermediaries = commissionRateL2andL3.getParentIntermediaries();
            if (commissionRateL3andL4!=null && commissionRateL3andL4.getChildrenIntermediaries()!=null){
                SalesL3AndL4 salesL3AndL4 = getSalesL3AndL4(commissionRateL2andL3, salesL2AndL3, commissionRateL3andL4);

                salesL3AndL4Repository.save(salesL3AndL4);
                salesL3AndL4Collection.put(salesL3AndL4.getId(), salesL3AndL4);
            }
        }
    }

    private static SalesL3AndL4 getSalesL3AndL4(CommissionRateL2andL3 commissionRateL2andL3, SalesL2AndL3 salesL2AndL3, CommissionRateL3andL4 commissionRateL3andL4) {
        SalesL3AndL4 salesL3AndL4 = new SalesL3AndL4();
        int commissionRateTotal = commissionRateL2andL3.getCommissionRateTotal();
        ParentIntermediaries brokerInt = commissionRateL3andL4.getParentIntermediaries();
        ChildrenIntermediaries agentInt = commissionRateL3andL4.getChildrenIntermediaries();

        Double agentCommissionCalculated = (commissionRateL3andL4.getCommissionRatePersonal()* salesL2AndL3.getCommissionCalculated())/100.00;
        Double brokerCommissionCalculated = salesL2AndL3.getCommissionCalculated() - agentCommissionCalculated;

        salesL3AndL4.setSalesL2AndL3Id(salesL2AndL3);
        salesL3AndL4.setCommissionRateL3AndL4Id(commissionRateL3andL4);
        salesL3AndL4.setParentIntermediariesFK(brokerInt);
        salesL3AndL4.setChildrenIntermediariesFK(agentInt);
        salesL3AndL4.setParentCommissionCalculated(brokerCommissionCalculated);
        salesL3AndL4.setChildCommissionCalculated(agentCommissionCalculated);
        return salesL3AndL4;
    }

    public static int generateRandomValue(int min, int max ){
        int range = max - min + 1;
        int rand = (int)(Math.random() * range) + min;
        return rand;
    }
}
