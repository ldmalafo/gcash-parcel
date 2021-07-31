# GCash Parcel Calculator
Parcel Calculator for GCash Exam. A spring-boot based application for Parcel Calculator.

## Pre-requisite
In order to run the application, you need the following installed on your laptop or PC.

- Java Development Kit 11 - You can download from [Zulu](https://www.azul.com/downloads/?package=jdk).
- Apache Maven 3.6.x or latest - You can download from [here](https://maven.apache.org/download.cgi).
- (Optional) Netbeans IDE - You can get the version 12 LTS from [here](https://netbeans.apache.org/download/nb120/nb120.html).

**IMPORTANT!** Make sure that Java Home and Maven Home are added in your Environment Path so that `java` and `mvn` commands can run on any directory.  This is needed for building and running the project without any IDE.

## Running The Application
Follow the steps below to run this application.

1. Clone the project on any folder using
```
git clone https://github.com/ldmalafo/gcash-parcel
```
2. Go to the project directory
```
cd gcash-parcel
```
3. Build the project to download all dependency
```
mvn clean install
```
4. Run the project
```
./mvnw spring-boot:run
```
5. Open another CLI and test if the application is running
```
curl --location --request GET 'http://localhost:8080/api/status'
```
6. Another way to check the status of the application is through
```
curl --location --request GET 'http://localhost:8080/actuator/health'
```
7. Open the location `http://localhost:8080` in browser to play around the API.  You can also update the calculator settings such as prices, weight limits, volume limits, and voucher config.

## API Lists
1. **Parcel Calculator** (`/api/parcel`) - Calculates the cost of a parcel based on weight, volume, and optionally the voucher code.
   - Method
   ```
   POST
   ```
   - Input Fields
     - **parcel** - The parameters of the parcel.
       - **weight** - Weight
       - **length** - Length
       - **width** - Width
       - **height** - Height
     - **voucher** - The voucher code to be considered for discount. (Example: `GFI` or `MYNT`)
     - **ignoreExpiration** - Flag to ignore expiration date or not. (`YES` means ignore expiration date, while `NO` to check if voucher is expired.)
   - Sample Input
   ```
   {
      "parcel" : {
         "weight" : 9,
         "length" : 30,
         "width" : 10,
         "height" : 20
      },
      "voucher" : "GFI",
      "ignoreExpiration" : "YES"
   }
   ```
   - Result Fields
     - **code** - Contains `OK`, `ERROR`, or `REJECT`
     - **message** - Any string message from the server
     - **cost** - Contains the details of the Parcel calculation
       - **subTotal** - The cost of the parcel calculated from the input parameters.
       - **discount** - The amount of discount based on the entered voucher.
       - **total** - The final cost of the parcel calculated by substracting discount from the sub-total.
       - **weight** - The weight considered for calculation.
       - **volume** - The calculated volume used for calculation.
       - **category** - The categorization of the parcel based on input parameters.
       - **priceUsed** - The price used for calculating cost.
       - **voucher** - The voucher code used. (Optional)
   - Sample Result
   ```
   {
      "code": "OK",
      "message": "Expired Voucher - GFI, on 2020-09-16.",
      "cost": {
          "subTotal": 300.0,
          "discount": 0.0,
          "total": 300.0,
          "weight": 9,
          "volume": 6000,
          "category": "Large Parcel",
          "priceUsed": 0.05,
          "voucher": null
      }
   }
   ```
   
   OR
   
   ```
   {
      "code": "OK",
      "cost": {
          "subTotal": 300.0,
          "discount": 12.25,
          "total": 287.75,
          "weight": 9,
          "volume": 6000,
          "category": "Large Parcel",
          "priceUsed": 0.05,
          "voucher": "MYNT"
      }
   }
   ```
2. **Parcel Calculator Settings Update** (`/api/settings/parcel`) - Updates the various parameters for calculating parcel cost.
   - Method
   ```
   POST
   ```
   - Input Fields
     - **rejectWeight** - Any weight above this value will be rejected.
     - **heavyWeight** - Anything above this weight will be calculated as Heavy Parcel.
     - **heavyWeightPrice** - The price per weight for calculating cost of Heavy Parcel.
     - **smallParcelLimit** - The volume upper limit for Small Parcel.
     - **mediumParcelLimit** - The volume upper limit for Medium Parcel.
     - **smallParcelPrice** - The price per volume for Small Parcel
     - **mediumParcelPrice** - The price per volume for Medium Parcel
     - **largeParcelPrice** - The price per volume for Large Parcel
   - Sample Input
   ```
   {
       "rejectWeight" : 50,
       "heavyWeight" : 10,
       "heavyWeightPrice" : 20,
       "smallParcelLimit" : 1500,
       "mediumParcelLimit" : 2500,
       "smallParcelPrice" : 0.03,
       "mediumParcelPrice" : 0.04,
       "largeParcelPrice" : 0.05
   }
   ```
   - Result Fields
     - **code** - Contains `OK`, `ERROR`, or `REJECT`
     - **message** - Any string message from the server
   - Sample Result
   ```
   {
       "code": "ERROR",
       "message": "Price for Small Parcel cannot be less than zero."
   }
   ```
   
   OR
   
   ```
   {
       "code": "OK",
       "message": "Successfully Updated"
   }
   ```
3. **Voucher Settings Update** (`/api/settings/voucher`) - Update the settings for checking voucher validity and discount.
   - Method
   ```
   POST
   ```
   - Input Fields
     - **url** - The URL for the Voucher Verification API.
     - **key** - The API Key for authentication
   - Sample Input
   ```
   {
      "url" : "https://mynt-exam.mocklab.io/voucher/",
      "key" : "apikey"
   }
   ```
   - Result Fields
     - **code** - Contains `OK`, `ERROR`, or `REJECT`
     - **message** - Any string message from the server
   - Sample Result
   ```
   {
       "code": "ERROR",
       "message": "Missing API URL"
   }
   ```
4. **API Status** (`/api/status`) - Used for checking API end-point status.
   - Method
   ```
   GET
   ```
   - Result Fields
     - **code** - Contains `OK`, `ERROR`, or `REJECT`
     - **message** - Any string message from the server
   - Sample Result
   ```
   {
       "code": "OK",
       "message": "Application is running"
   }
   ```
