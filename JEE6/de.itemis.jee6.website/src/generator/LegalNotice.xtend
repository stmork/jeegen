package generator

class LegalNotice extends AbstractWebsite {

	override path() {
		"legal-notice.html"
	}
	
	override contents() '''
	<div id="main" role="main">
		<div id="maincontainer" class="clearfix centering">
			<h2>Company</h2>
			<div class="box introduction">
				itemis AG
				Am Brambusch 15–24
				44536 Lünen
				Germany

				Responsibility in accordance with §55(2) of the German Interstate Broadcasting Treaty (Rundfunkstaatsvertrag):
				Jens Wagener (address as above)
				
				<h3>Management Board</h3>
				Jens Wagener (chairman), Wolfgang Neuhaus, Sebastian Neus, Dr. Georg Pietrek, Jens Trompeter
				
				<h3>Supervisory Board</h3>
				Prof. Dr. Burkhard Igel (chairman), Stephan Grollmann (vice chairman), Michael Neuhaus
				
				<h3>Registered Office</h3>
				Lünen
				
				<h3>Register Court</h3>
				Amtsgericht Dortmund
				
				<h3>Register no.</h3>
				
				HRB 20621
			</div>
		</div>
	</div>
	'''
	
}