function render() {
  var contactList = retrieveContactListFromServer();
	renderContactList(contactList, $('#contactTable'), false);

}

$(document).ready(function() {
	render();
	$("#save_button").click(function() {
		submitEditContact();
	});
});



function retrieveContactListFromServer()
{
	var contactList = [];	
	
	$.ajax({
	    type: 'GET',
	    url: '/contacts',
	    dataType: 'json',
	    success: function(data) {contactList = data },
	    async: false
	});
	
	return contactList;
}

function Contact(attributes) {
    this.firstName = attributes.firstname===undefined?'':attributes.firstname;
    this.lastName = attributes.lastname===undefined?'':attributes.lastname;
    this.company = attributes.company===undefined?'':attributes.company;
    this.location = attributes.location===undefined?'':attributes.location;
    this.email = attributes.email===undefined?'':attributes.email;
}

function createTableData(cssClasses, value) {
    var tableRow = $('<tr>');
    return '<td class="' + cssClasses + '" style="text-align: left; line-height: 200%;">' + value + '</td>';
}

function createButtonElement(buttonId, buttonName, clickEvent) {
    return '<button class="btn btn-default" id="' + buttonId + '" name="' + buttonName + '" onclick=\'' + clickEvent + '\'>' + buttonName +'</button>';
}

function renderContactList(json, selector)
{

    selector.html('');
	$.each(json, function(idx, item) {
        var contact = new Contact(item.attributes);
        var tableContent = [
          ['email-address', contact.email],
          ['', contact.firstName],
          ['', contact.lastName],
          ['company', contact.company],
          ['location', contact.location],
          ['', createButtonElement('edit_button', 'edit', 'showEditContactDetail(' + JSON.stringify(item) + ')')]
        ];
        generateContactTableRow(selector, tableContent);
    })
}

function renderContactSelectionList(json, selector)
{
	selector.html('');
	$.each(json, function(idx, item) {
		var contact = new Contact(item.attributes);
        var tableContent = [
            ['email-checkbox', "<input type=\"checkbox\" onclick=\"whenContactIsSelected(" + idx + ")\" id=\"" + idx + "\" value=\"" + item.attributes.email + "\" />"],
            ['email-address', contact.email],
            ['contact-name', contact.firstName],
            ['contact-lname', contact.lastName],
            ['contact-cname', contact.company],
            ['contact-location', contact.location]
        ];
        generateContactTableRow(selector, tableContent);
	})
}

function generateContactTableRow(selector, content) {
    var tr = $('<tr>' );
    selector.append(tr);
    content.forEach(function(element) {
        tr.append(createTableData(element[0], element[1]));
    });
}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}


function showEditContactDetail(item)
{
	openEditContactModal();
	insertDataIntoContactModal(item);
}

function insertDataIntoContactModal(item){

    var location = item.attributes.location;

    var positionOfslash = location.split("/");
    var country = positionOfslash[0];
    var city = positionOfslash[1];

	$('#name').val(item.attributes.firstname);
	$('#lastname').val(item.attributes.lastname);
	$('#company').val(item.attributes.company);
	$('#countrydrp').val(country);
	$('#city').val(city);
	$('#email').val(item.attributes.email);
	$('#email_label').text(item.attributes.email);
}

function openEditContactModal()
{
	$('#editContactModal').modal();
}

function submitEditContact() {
	$("#editContact").submit();
}
