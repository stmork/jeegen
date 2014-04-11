package generator

class Kontakt extends AbstractWebsite {

	override path() {
		'kontakt.html'
	}
	
	override contents() '''
	<div id="main" role="main">
		<div id="maincontainer" class="clearfix centering">
			<h2>Kontakt</h2>
			<div class="box introduction">
				<h3>Bug gefunden?</h3>
				Du hast einen Bug gefunden oder einen Verbesserungsvorschlag? Sende uns ein Mail an:
				<a href="mailto:info@jee-generator.de"><strong>info@jee-generator.de</strong></a>
			</div>
		</div>
	'''
}