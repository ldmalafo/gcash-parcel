package com.exam.myntexam;

import com.exam.myntexam.storage.data.Parcel;
import com.exam.myntexam.storage.data.ParcelCostRequest;
import com.exam.myntexam.storage.data.ParcelCostResponse;
import com.exam.myntexam.storage.data.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParcelCalculatorTest {
    @Autowired
    private TestRestTemplate template;
    
    @Test
    public void testAppStatus() {
        ResponseEntity<Status> response = template.getForEntity("/api/status", Status.class);
        
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo("OK");
        assertThat(response.getBody().getMessage()).isEqualTo("Application is running");
    }

    @Test
    public void testRequestRejected() {
        Parcel p = new Parcel();
        p.setWeight(51);
        
        ParcelCostRequest pcr = new ParcelCostRequest();
        pcr.setParcel(p);
        
        ResponseEntity<ParcelCostResponse> response = template.postForEntity("/api/parcel", pcr, ParcelCostResponse.class);
        
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo("REJECT");
        
        assertThat(response.getBody().getCost()).isNotNull();
        assertThat(response.getBody().getCost().getTotal()).isEqualTo(-1.0d);
    }
    
    @Test
    public void testHeavyParcel() {
        Parcel p = new Parcel();
        p.setWeight(30);
        
        ParcelCostRequest pcr = new ParcelCostRequest();
        pcr.setParcel(p);
        
        ResponseEntity<ParcelCostResponse> response = template.postForEntity("/api/parcel", pcr, ParcelCostResponse.class);
        
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo("OK");
        
        assertThat(response.getBody().getCost()).isNotNull();
        assertThat(response.getBody().getCost().getTotal()).isEqualTo(600.0d);
        assertThat(response.getBody().getCost().getCategory()).isEqualTo("Heavy Parcel");
    }
    
    @Test
    public void testSmallParcel() {
        Parcel p = new Parcel();
        p.setWeight(9);
        p.setLength(10);
        p.setWidth(10);
        p.setHeight(10);
        
        ParcelCostRequest pcr = new ParcelCostRequest();
        pcr.setParcel(p);
        
        ResponseEntity<ParcelCostResponse> response = template.postForEntity("/api/parcel", pcr, ParcelCostResponse.class);
        
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo("OK");
        
        assertThat(response.getBody().getCost()).isNotNull();
        assertThat(response.getBody().getCost().getTotal()).isEqualTo(30.0d);
        assertThat(response.getBody().getCost().getCategory()).isEqualTo("Small Parcel");
    }
    
    @Test
    public void testMediumParcel() {
        Parcel p = new Parcel();
        p.setWeight(9);
        p.setLength(15);
        p.setWidth(10);
        p.setHeight(13);
        
        ParcelCostRequest pcr = new ParcelCostRequest();
        pcr.setParcel(p);
        
        ResponseEntity<ParcelCostResponse> response = template.postForEntity("/api/parcel", pcr, ParcelCostResponse.class);
        
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo("OK");
        
        assertThat(response.getBody().getCost()).isNotNull();
        assertThat(response.getBody().getCost().getTotal()).isEqualTo(78.0d);
        assertThat(response.getBody().getCost().getCategory()).isEqualTo("Medium Parcel");
    }
    
    @Test
    public void testLargeParcel() {
        Parcel p = new Parcel();
        p.setWeight(9);
        p.setLength(15);
        p.setWidth(30);
        p.setHeight(13);
        
        ParcelCostRequest pcr = new ParcelCostRequest();
        pcr.setParcel(p);
        
        ResponseEntity<ParcelCostResponse> response = template.postForEntity("/api/parcel", pcr, ParcelCostResponse.class);
        
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo("OK");
        
        assertThat(response.getBody().getCost()).isNotNull();
        assertThat(response.getBody().getCost().getTotal()).isEqualTo(292.5d);
        assertThat(response.getBody().getCost().getCategory()).isEqualTo("Large Parcel");
    }
}
