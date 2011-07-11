<jsp:include page="pageTop.html" />

<!-- start content -->

<div id="content">
	<div class="post">
			<h1 class="title">Future Work </h1>
			<div class="entry">
			  <p>There are many important issues that our present implementation does not yet address. These include the following:</p>
			  <ul>
			    <li><strong>Key Management: </strong>The system needs a key management system in place that verifies an individual's attributes and thereafter assigns appropriate keys to them providing access. </li>
			    <li><strong>Conflight Resolution: </strong>An individual might have multiple roles and correspondingly different attributes. There could be scenarios where an individual has access within one role and does not have access while acting within another role. We are leaving it up to the individual accessing the data to decide the role within which they are accessing the data. There needs to be an auditing mechanism to verify if the individual could have legally accessed that data or not. </li>
			    <li><strong>Revocation of access:</strong> Our current implementation cannot revoke an individual's access to some data, to which they had access based out on their attributes. One way to address this problem is to have revocation lists which identify the individuals whose access to the data has been revoked. Another option is to incorporate that within the access control policy by modifying the policy. The latter option would require us to re-encrypt the DES key with the policy. As the number of revocations increase, the size of the corresponding ciphertext will become increasingly larger. Hence, this does not seem to be a good solution.</li>
			    <li><strong>Health Information Exchange (HIE): </strong>The HIE enables different healthcare entities to share medical information with each other. The current implementation can be easily extended to the scenario of sharing PHI data across different hospitals which could be in different states. If the hospitals are in different states, they might have slightly different rules and regulations governing access to the data. Besides, the hospitals themselves might decide to be more restrictive in terms of access. We need to come up with an efficient technique for identifying an  access control policy and for resolving conflicts.</li>
		      </ul>
			  <p>&nbsp;</p>
          </div>
	</div>
</div>

<jsp:include page="pageBottom.html" />