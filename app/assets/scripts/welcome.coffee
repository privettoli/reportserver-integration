$(document).ready ->
  formatsSelect = $ "#formats"
  reportIdInput = $ "#report-id";
  alertDiv = $("#ajax-message")

  handleError = (errorMessage) ->
    alertDiv.text(errorMessage)
    alertDiv.removeClass "hide"
    alertDiv.show()

  $.get $("#supported-formats-url").val(), (formats) ->
    for format in formats
      formatsSelect.append $("<option>").text format
    formatsSelect.val(formats[0])
    null

  $("#export-button").click ->
    id = reportIdInput.val()
    format = formatsSelect.find(":selected").text();
    if id == ""
      handleError("Please type id")
    else if id < 0
      handleError("Incorrect report id")
    else if format == "Format…"
      handleError("Please choose format")
    else window.location = $("#export-url").val().replace("99", id).replace("format", format)
    null
  reportIdInput.on "change", ->
    $(".alert").hide()
    if reportIdInput.val() == ""
      handleError("Please type id")
    else if id < 0
      handleError("Incorrect report id")
    null
