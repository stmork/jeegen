package generator

class Features extends AbstractWebsite {

	override path() {
		'features.html'
	}
	
	override contents() '''
	<div id="main" role="main">
		<div id="maincontainer" class="clearfix centering">
			<h2>Features</h2>
			<div class="box introduction">
				<p>
					Die JEE-Generatoren sollen durch ein einfaches Modell die gesamte Infrastruktur für eine
					Web-Anwendung generieren. Dadurch soll der Aufwand für Build und Deploy
					möglichst minimiert werden. Das von der Web-Anwendung unabhängige Verhalten wird
					abhängig vom benutzten Container in diesem konfiguriert. Dadurch kann dieselbe
					Web-Anwendung zu unterschiedlichen Zwecken (z.B. Live-Betrieb, Staging und Testing)
					deployed werden.
				</p>

				<p>
					Der JEE6- oder JEE7-Generator generiert eine Web-Applikation, die folgende Frameworks des JEE-Standards
					benutzt:
					<ul>
						<li>Servlet API 3.x</li>
						<li>XHTML/JSF 2.x</li>
						<li>EJB 3.1 oder höher</li>
						<li>JAAS</li>
						<li>Java Mail</li>
					</ul>
				</p>

				<p>
					Die Entity Beans werden typabhängig als Maske dargestellt und können thematisch sortiert
					werden, welche wiederumg rollenbasiert abgesichert werden können. Die Daten können nach dem
					CRUD-Paradigma entsprechend passende Operationen bereitstellen.
				</p>
	
				<p>
					Darüber hinaus werden noch zahlreiche weitere Features unterstützt:
					<ul>
						<li>Lokalisierung und Internationalisierung</li>
						<li>Bereiststellung von Mail Services für die Java Mail-API</li>
						<li>Rollenbasierte Sicherheit durch Verwendung von JAAS</li>
						<li>Benutzung mehrerer Data Sources</li>
					</ul>
				</p>
	
				<p>
					Zur Unterstützung des Entwicklers hat das Eclipse-Plugin folgende auch von Xtext bekannte Features:
					<ul>
						<li>Content Assist</li>
						<li>Quick Fixes</li>
					</ul>
				</p>
				</div>
			</div>
		</div>
	'''
}