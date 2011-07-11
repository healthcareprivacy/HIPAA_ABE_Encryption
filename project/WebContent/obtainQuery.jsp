<%! String sender, owner, purpose, message; %>

<jsp:useBean id="userQuery" class="policy.EncryptionParameters" scope="page"/>

<jsp:setProperty name="userQuery" property="sender"/> 
<jsp:setProperty name="userQuery" property="owner"/>
<jsp:setProperty name="userQuery" property="purpose"/> 
<jsp:setProperty name="userQuery" property="message"/>

<% 
	sender	= userQuery.getSender();
	owner 	= userQuery.getOwner();
	purpose = userQuery.getPurpose();
	message = userQuery.getMessage();
%>


