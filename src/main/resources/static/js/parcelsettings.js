/* 
 * Lorenzo D. Malafo Jr.
 */
$(function() {
    $('#btnUpdateSettings').on('click', function() {
        var data = {
            "rejectWeight" : $('#txtReject').val().trim(),
            "heavyWeight" : $('#txtHeavy').val().trim(),
            "heavyWeightPrice" : $('#txtPriceWeight').val().trim(),
            "smallParcelLimit" : $('#txtMaxSmall').val().trim(),
            "mediumParcelLimit" : $('#txtMaxMedium').val().trim(),
            "smallParcelPrice" : $('#txtPriceSmall').val().trim(),
            "mediumParcelPrice" : $('#txtPriceMedium').val().trim(),
            "largeParcelPrice" : $('#txtPriceLarge').val().trim()
        };
        
        $('#spnParcelSaving').html('Saving voucher settings...');
        $('#spnParcelSaving').removeClass('hidden');
        $('#btnUpdateSettings').prop('disabled', true);
        
        $.ajax ({
            url: "/api/settings/parcel",
            type: "POST",
            data: JSON.stringify(data),
            dataType: "json",
            contentType: "application/json; charset=utf-8"
        }).done(function(result) {
            $('#spnParcelSaving').html(result.code + ' : ' + result.message);
            $('#btnUpdateSettings').prop('disabled', false);
            
            setTimeout(function() {
                $('#spnParcelSaving').addClass('hidden');
            }, 3000);
        });
        
    });
    
    $('#btnUpdateVoucher').on('click', function() {
        var data = {
            "url" : $('#txtVoucherUrl').val().trim(),
            "key" : $('#txtAPIKey').val().trim()
        };
        
        $('#spnVoucherSaving').html('Saving voucher settings...');
        $('#spnVoucherSaving').removeClass('hidden');
        $('#btnUpdateVoucher').prop('disabled', true);
        
        $.ajax ({
            url: "/api/settings/voucher",
            type: "POST",
            data: JSON.stringify(data),
            dataType: "json",
            contentType: "application/json; charset=utf-8"
        }).done(function(result) {
            $('#spnVoucherSaving').html(result.code + ' : ' + result.message);
            $('#btnUpdateVoucher').prop('disabled', false);
            
            setTimeout(function() {
                $('#spnVoucherSaving').addClass('hidden');
            }, 3000);
        });
    });
});