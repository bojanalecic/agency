$( document ).ready(function() {
  $.ajaxSetup({ cache: false });
  $(".deleteEmployee").click(deleteEmployee);
  $(".deleteProject").click(deleteProject);
  $(".insertProject").click(insertProject);
  $(".deleteEngagement").click(deleteEngagement);
  $(".insertEngagement").click(insertEngagement);

$(".editable").dblclick(function (){
    var OriginalContent = $(this).text();
    $(this).addClass("cellEditing");
    $(this).html("<input type='text' value='" + OriginalContent + "' />");
    $(this).children().first().focus();
    $(this).children().first().keypress(function (e){
      if (e.which == 13){
        var newContent = $(this).val();
        $(this).parent().text(newContent);
        $(this).parent().removeClass("cellEditing"); } });

    $(this).children().first().blur(function(){
      $(this).parent().text(OriginalContent);
      $(this).parent().removeClass("cellEditing"); }); });
});

function deleteEmployee(){
  var closestTr = $(this).closest('tr');
  var emplId = $(closestTr).children()[0].innerText;
  $.ajax({
    url: '/remove-employee',
    type: 'DELETE',
    data: {id: emplId},
    success: function(result) {
         window.location.href = "/employee";
    }
});
};

$(".updateEmployee").click(function (){
  var closestTr = $(this).closest('tr');
  var employeeId = $(closestTr).children()[0].innerText;
  var newFirstName = $(closestTr).children()[1].innerText;
  var newLastName = $(closestTr).children()[2].innerText;
  var newSchool = $(closestTr).children()[3].innerText;
   $.ajax({
    url: '/update-employee',
    type: 'POST',
    data: {id: employeeId,
           first_name: newFirstName,
           last_name: newLastName,
           school: newSchool
          },
    success: function(result) {
        window.location.href = "/employee";
    }
});
});

function deleteProject (){
  var closestTr = $(this).closest('tr');
  var projId = $(closestTr).children()[0].innerText;
  $.ajax({
    url: '/remove-project',
    type: 'DELETE',
    data: {id: projId},
    success: function(result) {
         window.location.href = "/project";
    }
});
};

function insertProject(){
  var id = $('#projectId').val();
  var name = $('#projectName').val();
  var description = $('#projectDescription').val();
  var selectedTech = $('#technologies :selected').val();
  $.ajax({
    url: '/save-project',
    type: 'POST',
    data: {id: id,
           name: name,
           description: description,
           technology_id: selectedTech
          },
    success: function(result) {
       window.location.href = "/project";
    }
 });
};

function deleteEngagement (){
  var closestTr = $(this).closest('tr');
  var engId = $(closestTr).children()[0].innerText;
  $.ajax({
    url: '/remove-engagement',
    type: 'DELETE',
    data: {id: engId},
    success: function(result) {
         window.location.href = "/engagement";
    }
});
};

function insertEngagement(){
  var id = $('#engagementId').val();
  var description = $('#engagementDescription').val();
  var selectedEmp = $('#employees :selected').val();
  var selectedProj = $('#projects :selected').val();
  $.ajax({
    url: '/save-engagement',
    type: 'POST',
    data: {id: id,
           description: description,
           empl_id: selectedEmp,
           proj_id: selectedProj
          },
    success: function(result) {
       window.location.href = "/engagement";
    }
 });
};

