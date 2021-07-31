/* 
 * Lorenzo D. Malafo Jr.
 */
$(function() {
    var constructData = function() {
        var data = {
            "parcel" : {
                "weight" : $('#txtWeight').val().trim(),
                "length" : $('#txtLength').val().trim(),
                "width" : $('#txtWidth').val().trim(),
                "height" : $('#txtHeight').val().trim()
            },
            "voucher" : $('#txtVoucher').val().trim(),
            "ignoreExpiration" : ($('#chkIgnoreExpire').is(":checked") ? "YES" : "NO")
        };
        
        return data;
    };
    
    var submitData = function(jsonData) {
        var def = new $.Deferred();

        $.ajax ({
            url: "/api/parcel",
            type: "POST",
            data: jsonData,
            dataType: "json",
            contentType: "application/json; charset=utf-8"
        }).done(function(resultData) {
            def.resolve(resultData);
        });
        
        return def.promise();
    };
    
    $('#btnSubmitData').on('click', function() {
        $('.input-button').prop('disabled', true);
        
        $('#textRequestData').val('');
        $('#textResponseData').val('Calculating');
        
        var data = constructData();
        var jsonData = JSON.stringify(data);
        var jdataPretty = JSON.stringify(data, null, '\t');
        
        $('#textRequestData').val(jdataPretty);
        
        submitData(jsonData).done(function(resObj) {
            $('#textResponseData').val(JSON.stringify(resObj, null, '\t'));
            $('.input-button').prop('disabled', false);
        });
    });
    
    $('#btnReset').on('click', function() {
        location.reload(true);
    });
});