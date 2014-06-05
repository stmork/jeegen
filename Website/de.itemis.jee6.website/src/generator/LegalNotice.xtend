package generator

class LegalNotice extends AbstractWebsite {

	override path() {
		"legal-notice.html"
	}
	
	override contents() '''
	<div id="main" role="main">
		<div id="maincontainer" class="clearfix centering">
			<h2>Impressum</h2>
			<div class="box introduction">
				<h3>Unternehmen</h3>
				itemis AG<br/>
				Am Brambusch 15<br/>
				<br/>
				44536 Lünen

				<h3>Vorstand</h3>
				Jens Wagener (Vorsitzender), Wolfgang Neuhaus, Sebastian Neus, Dr. Georg Pietrek, Jens Trompeter

				<h3>Aufsichtsrat</h3>
				Prof. Dr. Burkhard Igel (Vorsitzender), Stephan Grollmann (stellv. Vorsitzender), Michael Neuhaus

				<h3>Sitz der Gesellschaft</h3>
				Lünen

				<h3>Registergericht</h3>
				Amtsgericht Dortmund
				Registernummer 
				HRB 20621

				<h3>Umsatzsteueridentifikationsnummer</h3>
				DE 23 11 77 498
			</div>
		</div>
	</div>
	'''
}