<jsp:include page="pageTop.html" />
<%@ page import="policy.EncryptionParameters" %>
<%@ page import="security.abemodule.utility.*" %>
<%@ page import="security.abemodule.encryption.*" %>

<script type='text/javascript'>
function notEmpty(elem){
	if(elem.value.length == 0){
		//elem.focus();
		return false;
	} 
	return true;
}

function createRequestObject() {
    var ro;
    var browser = navigator.appName;
    if(window.XMLHttpRequest){
    	ro = new XMLHttpRequest();
    } else {
    	ro = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return ro;
}

var http = createRequestObject();
var sender, owner, purpose, message, receiver, belief, consent;

function obtainSenderValues(){
	var msg = "Please enter a value for the following fields: \n";
	var check = true;
	
	if (notEmpty(document.getElementById('sender'))){
		sender = document.sendMessage.sender.value;
	} else {
		msg = msg.concat("Sender");
		check = false;
	}
	
	if (notEmpty(document.getElementById('owner'))){
		owner = document.sendMessage.owner.value;
	} else {
		msg = msg.concat(" Owner");
		check = false;
	}

	if (notEmpty(document.getElementById('purpose'))){
		purpose = document.sendMessage.purpose.value;
	} else {
		msg = msg.concat("Purpose");
		check = false;
	}

	if (notEmpty(document.getElementById('message'))){
		message = document.sendMessage.message.value;
	} else {
		msg = msg.concat("Message");
		check = false;
	}

	if (!check) alert(msg);
	
	return check;
}

function obtainReceiverValues(){
	var msg = "Please enter a value for: ";
	var check = true;
	
	if (notEmpty(document.getElementById('receiver'))){
		receiver = document.receiveMessage.receiver.value;
	} else {
		msg = msg.concat("Receiver");
		check = false;
	}

	if (notEmpty(document.getElementById('belief'))){
		belief = document.receiveMessage.belief.value;
	} else {
		msg = msg.concat(" Belief");
		check = false;
	}

	if (notEmpty(document.getElementById('consent'))){
		consent = document.receiveMessage.consent.value;
	} else {
		msg = msg.concat(" Consent");
		check = false;
	}

	if (!check) alert(msg);
	
	return check;
}

function sendMessageToServer() {
    //alert('Encrypting');
    var myURL = 'http://localhost:8080/HIPAA_ABE_Encryption/';
    http.open('get', myURL+'ProcessInput?type=' +'encrypt'+
    	    '&sender='+document.sendMessage.sender.value+
    	    '&owner='+document.sendMessage.owner.value+
    	    '&purpose='+document.sendMessage.purpose.value+
    	    '&message='+document.sendMessage.message.value);
    http.onreadystatechange = handleResponse;
    http.send(null);
    
}

function receiveMessageFromServer(){
	//alert('Decrypting');
    var myURL = 'http://localhost:8080/HIPAA_ABE_Encryption/';
    http.open('get', myURL+'ProcessInput?type=' +'decrypt'+
    	    '&receiver='+document.receiveMessage.receiver.value+
    	    '&belief='+document.receiveMessage.belief.value+
    	    '&consent='+document.receiveMessage.consent.value);
    http.onreadystatechange = handleResponse;
    http.send(null);
}

function handleResponse() {
    if(http.readyState == 4 && http.status == 200){
        var response = http.responseText;
        var update = new Array();

        if(response.indexOf('|' != -1)) {
            update = response.split('|');
            for (var i = 0; i < (update.length/2); i++){
            	document.getElementById(update[2*i]).innerHTML = update[2*i+1];
            }
        }
    }
}
</script>

<div id="dhtmltooltip"></div>

<script type="text/javascript">
	/***********************************************
	 * Cool DHTML tooltip script- © Dynamic Drive DHTML code library (www.dynamicdrive.com)
	 * This notice MUST stay intact for legal use
	 * Visit Dynamic Drive at http://www.dynamicdrive.com/ for full source code
	 ***********************************************/

	var offsetxpoint = -60
	//Customize x offset of tooltip
	var offsetypoint = 20
	//Customize y offset of tooltip
	var ie = document.all
	var ns6 = document.getElementById && !document.all
	var enabletip = false
	if (ie || ns6)
		var tipobj = document.all ? document.all["dhtmltooltip"]
				: document.getElementById ? document
						.getElementById("dhtmltooltip") : ""

	function ietruebody() {
		return (document.compatMode && document.compatMode != "BackCompat") ? document.documentElement
				: document.body
	}

	function ddrivetip(thetext, thecolor, thewidth) {
		if (ns6 || ie) {
			if (typeof thewidth != "undefined")
				tipobj.style.width = thewidth + "px"
			if (typeof thecolor != "undefined" && thecolor != "")
				tipobj.style.backgroundColor = thecolor
			tipobj.innerHTML = thetext
			enabletip = true
			return false
		}
	}

	function positiontip(e) {
		if (enabletip) {
			var curX = (ns6) ? e.pageX : event.clientX
					+ ietruebody().scrollLeft;
			var curY = (ns6) ? e.pageY : event.clientY + ietruebody().scrollTop;
			//Find out how close the mouse is to the corner of the window
			var rightedge = ie && !window.opera ? ietruebody().clientWidth
					- event.clientX - offsetxpoint : window.innerWidth
					- e.clientX - offsetxpoint - 20
			var bottomedge = ie && !window.opera ? ietruebody().clientHeight
					- event.clientY - offsetypoint : window.innerHeight
					- e.clientY - offsetypoint - 20

			var leftedge = (offsetxpoint < 0) ? offsetxpoint * (-1) : -1000

			//if the horizontal distance isn't enough to accomodate the width of the context menu
			if (rightedge < tipobj.offsetWidth)
				//move the horizontal position of the menu to the left by it's width
				tipobj.style.left = ie ? ietruebody().scrollLeft
						+ event.clientX - tipobj.offsetWidth + "px"
						: window.pageXOffset + e.clientX - tipobj.offsetWidth
								+ "px"
			else if (curX < leftedge)
				tipobj.style.left = "5px"
			else
				//position the horizontal position of the menu where the mouse is positioned
				tipobj.style.left = curX + offsetxpoint + "px"

				//same concept with the vertical position
			if (bottomedge < tipobj.offsetHeight)
				tipobj.style.top = ie ? ietruebody().scrollTop + event.clientY
						- tipobj.offsetHeight - offsetypoint + "px"
						: window.pageYOffset + e.clientY - tipobj.offsetHeight
								- offsetypoint + "px"
			else
				tipobj.style.top = curY + offsetypoint + "px"
			tipobj.style.visibility = "visible"
		}
	}

	function hideddrivetip() {
		if (ns6 || ie) {
			enabletip = false
			tipobj.style.visibility = "hidden"
			tipobj.style.left = "-1000px"
			tipobj.style.backgroundColor = ''
			tipobj.style.width = ''
		}
	}

	document.onmousemove = positiontip
</script>

<!-- start content -->
<div id="content">

	<!-- Description -->
	<div class="post">
	<!-- <h1 class="title">Demo </h1>
		<div class="entry">
			<p> Please select a particular role for the sender. You can then share any message with others within the chosen role. Enter the message in 
			the box provided below. The access control policy extracted from the HIPAA law is used to encrypt the message. Only the people with 
			attributes that satisfy this policy can successfully decrypt to obtain the message. You can verify this by choosing different roles 
			for the sender and checking which ones can retrieve the plain message and which ones cant.
			</p>    -->
		</div>
	</div>
	
	<br />
	<div><h2 class="title">Encryption</h2></div>
	
	<br />
	
	<!-- Need to add a method which recomputes the encryption on any change to the fields -->
	
	<form name="sendMessage">
		<table class="form">
			<tr>
				<td><h3 class="title">Sender:</h3></td>

				<td><select id="sender" name="sender">
					<option value=""> -- From -- </option>
			      	<option value="doctor">Primary Doctor</option>
			      	<option value="surgeon">Surgeon</option>
			      	<option value="patient">Patient</option>
			      	<option value="chief_of_medicine">Chief of Medicine</option>
			      	<option value="teen">Teenager</option>
			      	<option value="intern">Intern</option>
			      	<option value="kid">Kid</option>
				</select></td>
		
				<td><h3 class="title">Owner:</h3></td>
				<td><select id="owner" name="owner">
			      	<option value=""> -- About -- </option>
			      	<option value="patient">Patient</option>
			      	<option value="dead">Deceased Individual</option>
			      	<option value="kid">Kid</option>
			      	<option value="teen">teen</option>
			      	<option value="doctor">Primary Doctor</option>
			      	<option value="chief_of_medicine">Chief of Medicine</option>
			      	<option value="intern">Intern</option>
			      	<option value="surgeon">Surgeon</option>
			      	
				</select></td>
			</tr>
			
			<tr>
				<td><h3 class="title">Purpose:</h3></td>
				<td><select id="purpose" name="purpose">
	      			<option value=""> -- Purpose -- </option>
	      			<option value="treatment">treatment</option>
	      			<option value="compliance">compliance</option>
	      			<option value="create_deidentified_info">create_deidentified_info</option>
	      			<option value="create_protected_health_info">create_protected_health_info</option>
	      			<option value="determining_legal_options">determining_legal_options</option>
	      			<option value="healthCare_fraud_abuse_detection">healthCare_fraud_abuse_detection</option>
	      			<option value="healthCare_operations">healthCare_operations</option>
	      			<option value="incident_to_use">incident_to_use</option>
	      			<option value="investigate">investigate</option>
	      			<option value="payment">payment</option>
	      			<option value="receive_deidentified_info">receive_deidentified_info</option>
	      			<option value="requested_by_Individual">requested_by_Individual</option>
	      			<option value="standards_failure_misconduct">standards_failure_misconduct</option>
				</select></td>
			</tr>
			<tr>
				<td>
					<h3 class="title">Message:</h3>
				</td>
				<td>
					<textarea id="message" name="message" rows="2" cols="35"></textarea>
				</td>
			</tr>
			
  			<tr>
  				<td colspan="2">
  				<button type="button" value="Encrypt" onclick="if(obtainSenderValues()) sendMessageToServer();"/>  
  				Encrypt
  				</button> 
  				</td>
			</tr>      
			<!--	<input type="reset" /></td></tr> -->
		</table>
	</form>
	

	<!--  Encrypt the message -->
	<!-- Encrypted message is stored in the encryptedMessage variable. -->
	
	<!-- Show the encrypted message after base64 encoding -->
	
	<br />

	<h3 class="title" 
		onMouseOver="ddrivetip('The message has been encrypted using Symmetric AES Encryption. This encryption can be computed quickly.')"; 
		onMouseOut="hideddrivetip()">
		Message after AES Encryption
	</h3>

	<textarea id="encryptedMessage" name="encryptedMessage" rows="2" cols="70" disabled="disabled">
	</textarea>
	
	<div id = "encryptedMessageLength">
		<h4 class="title" 
		onMouseOver="ddrivetip('Number of characters in the Encrypted Message.')"; 
		onMouseOut="hideddrivetip()">
		Number of Characters = 0 
		</h4>
	</div>
	
	
	<br />
	<h3 class="title"
		onMouseOver="ddrivetip('The key used in the AES Encryption step above is encrypted using ABE. This is a computationally intensive process. In the textbox, you can see the ciphertext encrypted according to one policy.')"; 
		onMouseOut="hideddrivetip()">
		ABE Encryption of the AES Key
	</h3>
	<textarea id="encryptedAESKey" name="encryptedAESKey" rows="5" cols="70" disabled="disabled">
	</textarea>
	
	<div id = "encryptedKeyLength">
		<h4 class="title">Number of Characters = 0</h4>
	</div>
	
	<div id ="numOfABEEncryptions">
		<h4 class="title"
			onMouseOver="ddrivetip('The entire HIPAA Policy is broken up into several policies such that ORs of individual policies is equivalent to HIPAA policy. This count gives the number of such policies. We are displaying the results of only one of such encryptions.')"; 
			onMouseOut="hideddrivetip()">
			Number of ABE Encryptions = 0
		</h4>
	</div>
	
	<br />
	<div><h2 class="title">Decryption</h2></div>
	<br />

	
	<!-- Select the role of a receiver -->
	
	<form name="receiveMessage" method="post">
		<table class="form">
			<tr>
				<td><h3 class="title">Receiver:</h3></td>
				<td><select id="receiver" name="receiver">
					<option value=""> -- Receiver -- </option>
			      	<option value="chiefOfMedicine">Chief of Medicine</option>
			      	<option value="emancipated_minor">Emancipated Minor</option>
			      	<option value="intern">Intern</option>
			      	<option value="nurse">Nurse</option>
			      	<option value="patient">Patient</option>
			      	<option value="doctor">Doctor</option>
			      	<option value="surgeon">Surgeon</option>
			      	<option value="unemancipated_minor">Unemancipated Minor</option>
			      	<option value="healthCare_plan"> HealthCare Plan </option>
			      	<option value="healthCare_clearing_house">HealthCare Clearing House</option>
			      	<option value="dad">Dad</option>
			      	<option value="mom">Mom</option>
			      	<option value="trainee">Trainee</option>
			      	<option value="kid"> Kid </option>
				</select></td>

				<td><h3 class="title">Belief:</h3></td>
				<td><select id="belief" name="belief">
			      	<option value=""> -- Belief -- </option>
			      	<option value="void">void</option>
			      	<option value="minimum_necessary_to_purpose">minimum_necessary_to_purpose</option>
			      	<option value="deceased_individual">Deceased Individual</option>
			      	<option value="emancipated_minor">Emancipated Minor</option>
			      	<option value="intern">Intern</option>
			    </select></td>
			</tr>
			
			<tr>
				<td><h3 class="title">Consent:</h3></td>
				<td><select id="consent" name="consent">
	      			<option value=""> -- Consent -- </option>
	      			<option value="void"> void </option>
	      			<option value="consented">consented</option>
	      			<option value="opp_given">opp_given</option>
				</select></td>
	
  				<td colspan="2">
  				<button type="button" value="Encrypt" 
  				onclick="if(obtainReceiverValues()) receiveMessageFromServer();"/>  
  				Decrypt
  				</button> 
  				</td>
			</tr>     
		</table>
	</form>
	
	<!-- Perform the decryption -->
	
	
	
	
	
	<!-- Display the result of the decryption process -->
	
	
	<br />
	
	<h3 class="title"
		onMouseOver="ddrivetip('This is the final decrypted message.')"; 
		onMouseOut="hideddrivetip()">
		Decrypted Text:
	</h3>
	<textarea id="decryptedMessage" name="decryptedMessage" rows="2" cols="70" disabled="disabled">
	</textarea>
		
		
</div>
<!-- end content -->

<jsp:include page="pageBottom.html" />