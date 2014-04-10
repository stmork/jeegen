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
				«entry('''
						icon-bug-blue.png
					''','''
						Bug gefunden?
					''','''
						mailto:jee6-generator@itemis.de
					''','''
						 Du hast einen Bug gefunden oder einen Verbesserungsvorschlag? Sende uns ein Mail an <strong>info@jee-generator.de</strong>
					''')»
			</div>
		</div>
	'''

	def entry(String img, String title, String link, String description) '''
		<div class="row">
			<div class="span1">&nbsp;</div>
			<div class="span9 team">
		    <a href="«link.trim»" class="anchor-in-div"></a>
		    <div class="row">
			  <div class="span1 ">
			    <img src="images/«img.trim»" alt="image" class="image_left">
			  </div>
			  <div class="span2 ">
			    <h3>«title.trim»</h3>
			 	</div>
				<div class="span6 ">
					«description»
				</div>
				</div>
			</div>
		  <div class="span1">&nbsp;</div>
		</div>
	'''
}