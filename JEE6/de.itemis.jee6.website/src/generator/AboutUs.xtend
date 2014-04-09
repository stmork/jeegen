package generator

class AboutUs extends AbstractWebsite {

	override path() {
		'aboutus.html'
	}
	
	override contents() '''
	<div id="main" role="main">
		<div id="maincontainer" class="clearfix centering">
			<h2>Über uns</h2>
			<div class="box introduction">
	
				<div id="Leute">
					<div id="links">
						<img src="images/mork.steffen.jpg"/>
						<h3>Steffen A. Mork</h3>
						<p>
							Steffen hat an der Universität Dortmund Kerninformatik studiert. Nach seinem Studium hat er
							im Bereich Dokumentenmanagement und Java Enterprise erste Erfahrungen gesammelt. Seit 2005
							ist er bei der itemis AG für die Infrastruktur und Beschaffung verantwortlich. Als Software
							Entwickler hat er mehrere Frameworks entwickelt:
							<ul>
								<li>
									Die modellgetriebene Server-Konfiguration ermöglicht es auf einfache Weise, den Soll-Zustand
									der itemis-Server zu verwalten. Die Konfiguration wird zentral generiert und berücksichtigt
									Abhängigkeiten zwischen den Diensten über alle Standorte hinweg.
								</li>
								<li>
									Für die modell(-bau-)basierte <a href="http://eisenbahnsteuerung.org">Eisenbahnsteuerung</a>
									hat er sowohl die auf CAN-Bus basierenden Steuerungs-Controller programmiert, als auch
									das Frontend des Eclipse basierten Frontends.
								</li>
								<li>
									Seit 2012 arbeitet Steffen an dem <a href="http://www.jee-generator.de">JEE-Generator</a>, der
									modellbasiert ein einfaches Prototyping für Web-Anwendungen ermöglicht. Einige dieser Web-Anwendungen
									werden hausintern verwendet, um die Produktivität zu erhöhen.
								</li>
							</ul>
						</p>
					</div>
					<div id="rechts">
						<img src="images/pieper.dominik.jpg"/>
						<h3>Dominik Pieper</h3>
						<p>
						</p>
					</div>
				</div>
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