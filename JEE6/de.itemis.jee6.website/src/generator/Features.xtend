package generator

class Features extends AbstractWebsite {

	override path() {
		'features.html'
	}
	
	override contents() '''
	<div id="header_wrapper" class="container" >
	</div>
		<div id="page">
			<div id="team" class="container clearfix"> 
				<h2>Features</h2>
				<hr/>
				<p>
					Der JEE-Generator soll durch ein einfaches Modell die gesamte Infrastruktur für eine
					Web-Anwendung generieren. Dadurch soll der Aufwand für Build und Deploy
					möglichst minimiert werden. Das von der Web-Anwendung unabhängige Verhalten wird
					abhängig vom benutzten Container in diesem konfiguriert. Dadurch kann dieselbe
					Web-Anwendung zu unterschiedlichen Zwecken (z.B. Live-Betrieb, Staging und Testing)
					deployed werden.
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