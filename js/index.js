function sumbit() {
  $.ajax({
      url: '../cgi-bin/submit.php',
      data: {"queryArg": queryString},
      method: 'GET',
      cache: false,
      dataType: 'html',
      success: function(result) {
        $('#dataTable').html(result);
        hideCols();
        $('#dataTable').append("<br>");
        $("#physicalTable").tablesorter();
      },
      error: function(jqXHR, textStatus, errorThrown) {
        if(jqXHR.status == '500') {
          alert('Internal server error: 500');
        } else {
          alert('Unexpected error');
        }
      }
  });
}

//Check if the second part of a class name is actually a number
function isSecondPartNumber(className) {
  var firstDigitIndex = -1;
  for(var i = 0; i < className.length; i++) {
    if($.isNumeric(className[i])) {
        firstDigitIndex = i;
        break;
    }
  }

  //No digits
  if(firstDigitIndex == -1) {
    return false;
  }

  //Check if the second part of the string only consists of digits/or ends with only letters
  var letterFlag = false;
  for(var i = firstDigitIndex; i < className.length; i++) {
    if(!$.isNumeric(className[i])) {
      letterFlag = true;
    }

    if($.isNumeric(className[i]) && letterFlag) {
      return false;
    }
  }
  
  return true;
}

function validClass(className) {

  //Make sure the first character is a letter
  if($.isNumeric(className[0])) {
    return false;
  }

  if(!isSecondPartNumber(className)) {
    return false;
  }

  return true;
}

//Check if the inputs are valid
function validInput() {
  var email = $('#email').val();
  var className = $('#class').val();

  //make sure fields are filled out
  if(email == "" || className == "") {
    $('#error').html('Must fill out both the e-mail and class fields');
    return false;
  }

  //make sure stanford.edu address
  if(email.indexOf('@stanford.edu') == -1) {
    $('#error').html('Must have a stanford.edu e-mail');
    return false;
  }

  if(!validClass(className)) {
    $('#error').html(className + ' does not seem like a valid class name');
    return false;
  }

  return true;
}

$('#submit').click(function() {
  $('#error').hide();
  if(validInput()) {
    //submit();
  } else {
    $('#error').show();
  }
  return false;
});
