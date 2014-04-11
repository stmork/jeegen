package generator

class AboutUs extends AbstractWebsite {

	override path() {
		'aboutus.html'
	}
	
	override contents() '''
	<div id="main" role="main">

		<div id="maincontainer" class="clearfix centering">
			<h2>Über uns</h2>
			
			<div class="y-grid">
			<div class="box introduction">
				<div class="y-g50 y-gl">
					<div id="aboutUsText">
						<h3>Steffen A. Mork</h3>
						<div id="aboutUsImage">
							<img src="images/mork.steffen.jpg"/>
						</div>
						<p>
							Steffen hat an der Universität Dortmund Kerninformatik studiert. Nach seinem Studium hat er
							im Bereich Dokumentenmanagement und Java Enterprise erste Erfahrungen gesammelt. Seit 2005
							ist er bei der itemis AG für die Infrastruktur und Beschaffung verantwortlich. Als Software
							Entwickler hat er mehrere Frameworks entwickelt:
						</p>
						<p>
							Die modellgetriebene Server-Konfiguration ermöglicht es auf einfache Weise, den Soll-Zustand
							der itemis-Server zu verwalten. Die Konfiguration wird zentral generiert und berücksichtigt
							Abhängigkeiten zwischen den Diensten über alle Standorte hinweg.
						</p>
						<p>
							Für die modell(-bau-)basierte <a href="http://eisenbahnsteuerung.org">Eisenbahnsteuerung</a>
							hat er sowohl die auf CAN-Bus basierenden Steuerungs-Controller programmiert, als auch
							das Frontend des Eclipse basierten Frontends.
						</p>
						<p>
							Seit 2012 arbeitet Steffen an dem <a href="http://www.jee-generator.de">JEE-Generator</a>, der
							modellbasiert ein einfaches Prototyping für Web-Anwendungen ermöglicht. Einige dieser Web-Anwendungen
							werden hausintern verwendet, um die Produktivität zu erhöhen.
						</p>
					</div>
				</div>
				<div class="y-g50 y-gl">
					<div id="aboutUsText">
						<h3>Dominik Pieper</h3>
						<div id="aboutUsImage">
							<img src="images/pieper.dominik.jpg"/>
						</div>
						<p>
							Dominik hat an der Hochschule Bochum allgemeine Informatik studiert. Seit 2011 ist er bei der itemis AG
							ebenfalls in der Abteilung für Infrastruktur und Beschaffung.
						</p>
						<p>
							Seit 2012 arbeitet Dominik mit am JEE-Generator, der modellbasiert ein einfaches Prototyping für
							Web-Anwendungen ermöglicht. Der generierte Prototyp lässt sich einfach für beliebige Anwendungsfälle
							erweitern. Der JEE-Generator wird intern zur Erstellung einiger hausinterner Anwendungen genutzt.
						</p>
						<p>
							Eine der hausinternen Anwendungen ist das Flexmento Lizenzsystem.
							Flexmento bildet eine Schnittstelle zwischen einem Magento-Webshop und einem FlexNet-
							Lizenzserver. Erfolgreiche Bestellungen werden entgegengenommen, Lizenzen generiert und via E-Mail
							an den Kunden versandt.
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	'''
}