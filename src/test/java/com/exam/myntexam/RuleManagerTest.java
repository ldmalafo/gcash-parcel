package com.exam.myntexam;

import com.exam.myntexam.config.RuleManager;
import com.exam.myntexam.storage.data.Parcel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author lorenzomalafo
 */
@SpringBootTest
public class RuleManagerTest {
    @Test
    public void testParcelVolume() {
        Parcel p = new Parcel();
        p.setLength(5);
        p.setWidth(10);
        p.setHeight(20);
        
        assertThat(p.getVolume()).isEqualTo(1000);
        
        p.setWidth(30);
        
        assertThat(p.getVolume()).isEqualTo(3000);
        
        p.setLength(10);
        
        assertThat(p.getVolume()).isEqualTo(6000);
        
        p.setHeight(1);
        
        assertThat(p.getVolume()).isEqualTo(300);
    }
    
    @Test
    public void testParcelCategory() {
        Parcel p = new Parcel();
        p.setWeight(51);
        p.setLength(5);
        p.setWidth(10);
        p.setHeight(20);
        
        RuleManager rm = RuleManager.getInstance();
        
        assertThat(rm.parcelCategory(p)).isEqualTo("Reject");
        
        p.setWeight(30);
        
        assertThat(rm.parcelCategory(p)).isEqualTo("Heavy Parcel");
        
        p.setWeight(9);
        
        assertThat(rm.parcelCategory(p)).isEqualTo("Small Parcel");
        
        p.setLength(11);
        
        assertThat(rm.parcelCategory(p)).isEqualTo("Medium Parcel");
        
        p.setLength(30);
        
        assertThat(rm.parcelCategory(p)).isEqualTo("Large Parcel");
    }
    
    @Test
    public void testParcelCost() {
        Parcel p = new Parcel();
        p.setWeight(51);
        p.setLength(5);
        p.setWidth(10);
        p.setHeight(20);
        
        RuleManager rm = RuleManager.getInstance();
        
        assertThat(rm.calculateCost(p).get().getTotal()).isEqualTo(-1.0d);
        
        p.setWeight(30);
        
        assertThat(rm.calculateCost(p).get().getTotal()).isEqualTo(600.0d);
        
        p.setWeight(9);
        
        assertThat(rm.calculateCost(p).get().getTotal()).isEqualTo(30.0d);
        
        p.setLength(11);
        
        assertThat(rm.calculateCost(p).get().getTotal()).isEqualTo(88.0d);
        
        p.setLength(30);
        
        assertThat(rm.calculateCost(p).get().getTotal()).isEqualTo(300.0d);
    }
}
