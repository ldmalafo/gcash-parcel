package com.exam.myntexam.config;

import com.exam.myntexam.storage.data.Parcel;
import com.exam.myntexam.storage.data.ParcelCost;
import com.exam.myntexam.storage.data.RuleBank;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lorenzomalafo
 */
public class RuleManager {
    private final RuleBank ruleBank;
    private static final RuleManager RULE_MANAGER;
    
    static {
        try(InputStream inputStream = RuleManager.class.getClassLoader().getResourceAsStream("parcelrules.properties")) {
            Properties prop = new Properties();
            prop.load(inputStream);
            
            RuleBank r = new RuleBank();
            r.setRejectWeight(Long.valueOf(prop.getProperty("reject.weight", "50")));
            r.setHeavyWeight(Long.valueOf(prop.getProperty("heavy.weight", "10")));
            r.setHeavyWeightPrice(Double.valueOf(prop.getProperty("heavy.weight.price", "20")));
            r.setSmallParcelLimit(Long.valueOf(prop.getProperty("small.parcel.limit", "1500")));
            r.setMediumParcelLimit(Long.valueOf(prop.getProperty("medium.parcel.limit", "2500")));
            r.setSmallParcelPrice(Double.valueOf(prop.getProperty("small.parcel.price", "0.03")));
            r.setMediumParcelPrice(Double.valueOf(prop.getProperty("medium.parcel.price", "0.04")));
            r.setLargeParcelPrice(Double.valueOf(prop.getProperty("large.parcel.price", "0.05")));
            
            RULE_MANAGER = new RuleManager(r);
        } catch(IOException ioe) {
            Logger.getLogger(RuleManager.class.getName())
                    .log(Level.SEVERE, "Failed to Read parcelrules.properties", ioe);
            
            throw new ExceptionInInitializerError("Failed to Read parcelrules.properties");
        }
    }
    
    private RuleManager(RuleBank ruleBank) {
        this.ruleBank = ruleBank;
    }
    
    /**
     * Gets the initialized instance of RuleManager.
     * 
     * @return an instance of <code>RuleManager</code>
     */
    public static RuleManager getInstance() {
        return RULE_MANAGER;
    }
    
    /**
     * Gets a copy of the current rules stored in the memory bank.
     * 
     * @return an instance of <code>RuleBank</code>
     */
    public RuleBank getRuleBank() {
        /*
            Create a copy instead of returning the instance.
            Since Java is by reference. We don't like to 
            accidentally modify the current rule since it acts as our
            rules database.
        */
        RuleBank r = new RuleBank();
        r.setRejectWeight(ruleBank.getRejectWeight());
        r.setHeavyWeight(ruleBank.getHeavyWeight());
        r.setHeavyWeightPrice(ruleBank.getHeavyWeightPrice());
        r.setSmallParcelLimit(ruleBank.getSmallParcelLimit());
        r.setMediumParcelLimit(ruleBank.getMediumParcelLimit());
        r.setSmallParcelPrice(ruleBank.getSmallParcelPrice());
        r.setMediumParcelPrice(ruleBank.getMediumParcelPrice());
        r.setLargeParcelPrice(ruleBank.getLargeParcelPrice());
        
        return r;
    }
    
    /**
     * Calculates the cost of the given parcel.
     * 
     * @param parcel an instance of <code>Parcel</code> containing weight,
     *      length, width, and height.
     * @return an optional calculation result of type <code>ParcelCost</code>
     */
    public Optional<ParcelCost> calculateCost(final Parcel parcel) {
        if(parcel == null) return Optional.empty();
        
        ParcelCost cost = new ParcelCost();
        cost.setWeight(parcel.getWeight());
        cost.setVolume(parcel.getVolume());
        
        double[] ccalc;
        
        if(isRejected(parcel.getWeight())) {
            ccalc = new double[] {-1.0d, -1.0d};
        } else if(isHeavy(parcel.getWeight())) {
            ccalc = parcelCostByWeight(parcel.getWeight());
        } else {
            ccalc = parcelCostByVolume(parcel.getVolume());
        }
        
        cost.setPriceUsed(ccalc[0]);
        cost.setSubTotal(ccalc[1]);
        cost.setTotal(ccalc[1]);
        cost.setCategory(parcelCategory(parcel));
        
        return Optional.of(cost);
    }
    
    private boolean isRejected(long weight) {
        return weight > ruleBank.getRejectWeight();
    }
    
    private boolean isHeavy(long weight) {
        return weight > ruleBank.getHeavyWeight();
    }
    
    private double[] parcelCostByWeight(long weight) {
        return new double[]{ruleBank.getHeavyWeightPrice(), 
            (double)(weight * ruleBank.getHeavyWeightPrice())};
    }
    
    public String parcelCategory(final Parcel parcel) {
        if(isRejected(parcel.getWeight())) {
            return "Reject";
        } else if(isHeavy(parcel.getWeight())) {
            return "Heavy Parcel";
        } else {
            double vol = parcel.getVolume();
            
            if(vol < ruleBank.getSmallParcelLimit()) {
                return "Small Parcel";
            } else if(vol < ruleBank.getMediumParcelLimit()) {
                return "Medium Parcel";
            } else {
                return "Large Parcel";
            }
        }
    }
    
    private double[] parcelCostByVolume(long vol) {
        double[] cost;

        if (vol < ruleBank.getSmallParcelLimit()) {
            cost = new double[]{ruleBank.getSmallParcelPrice(), vol * ruleBank.getSmallParcelPrice()};
        } else if (vol < ruleBank.getMediumParcelLimit()) {
            cost = new double[]{ruleBank.getMediumParcelPrice(), vol * ruleBank.getMediumParcelPrice()};
        } else {
            cost = new double[]{ruleBank.getLargeParcelPrice(), vol * ruleBank.getLargeParcelPrice()};
        }

        return cost;
    }
    
    /**
     * Updates the current rules stored in memory. (NOTE: This is thread safe
     * operation.)
     * 
     * @param updRule an object containing the new rule.
     */
    public void updateRuleBank(final RuleBank updRule) {
        /*
            Need to make sure this is thread safe, since we wanted to make sure
            that when mulitple attempts are made to update, we get always
            the last update.
        */
        synchronized(this) {
            ruleBank.setRejectWeight(updRule.getRejectWeight());
            ruleBank.setHeavyWeight(updRule.getHeavyWeight());
            ruleBank.setHeavyWeightPrice(updRule.getHeavyWeightPrice());
            ruleBank.setSmallParcelLimit(updRule.getSmallParcelLimit());
            ruleBank.setMediumParcelLimit(updRule.getMediumParcelLimit());
            ruleBank.setSmallParcelPrice(updRule.getSmallParcelPrice());
            ruleBank.setMediumParcelPrice(updRule.getMediumParcelPrice());
            ruleBank.setLargeParcelPrice(updRule.getLargeParcelPrice());
        }
    }
}
